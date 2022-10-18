package com.huce.doan.mxh.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.huce.doan.mxh.model.dto.CommentsDto;
import com.huce.doan.mxh.model.entity.CommentsEntity;
import com.huce.doan.mxh.model.response.Data;
import com.huce.doan.mxh.model.response.ListData;
import com.huce.doan.mxh.model.response.Pagination;
import com.huce.doan.mxh.model.response.Response;
import com.huce.doan.mxh.repository.CommentsRepository;
import com.huce.doan.mxh.service.CommentsService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentsServiceImpl implements CommentsService {
    private final CommentsRepository commentsRepository;
    private final ModelMapper mapper;
    private final Cloudinary cloudinary;
    private final Response response;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public Data getComment(Long id) {
        Optional<CommentsEntity> commentsEntity = commentsRepository.findById(id);

        return commentsEntity.map(data -> response.responseData("Get comment successfully", mapper.map(data, CommentsDto.class))).orElseGet(() -> response.responseError("Entity not found"));
    }

    @Override
    public ListData getByPostId(Long id, int page, int pageSize) {
        Page<CommentsDto> comments = commentsRepository.getListCommentByPostId(id, PageRequest.of(page, pageSize));

        return response.responseListData(comments.getContent(), new Pagination(comments.getNumber(), comments.getSize(), comments.getTotalPages(),
                (int) comments.getTotalElements()));
    }

    @Override
    public Data createComment(CommentsDto comment, MultipartFile picture) {
        CommentsEntity commentsEntity = new CommentsEntity().mapperCommentsDto(comment);
        commentsEntity.setCommentTime(LocalDateTime.now());

        if (picture != null) {
            try {
                Map x = this.cloudinary.uploader().upload(picture.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                commentsEntity.setPictureUrl(x.get("url").toString());

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        commentsEntity = commentsRepository.save(commentsEntity);
        comment.setId(commentsEntity.getId());
        comment.setCommentTime(commentsEntity.getCommentTime());
        comment.setPictureUrl(commentsEntity.getPictureUrl());

        simpMessagingTemplate.convertAndSend("/topic/message",comment);

        return response.responseData("Create comment successfully", comment);
    }

    @Override
    public Data updateComment(CommentsDto comment, MultipartFile picture, Long id) {
        comment.setId(id);
        Optional<CommentsEntity> commentsEntity = commentsRepository.findById(id);

        return commentsEntity.map(data -> {
            data = data.mapperCommentsDto(comment);
            if (picture != null) {
                try {
                    Map x = this.cloudinary.uploader().upload(picture.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                    data.setPictureUrl(x.get("url").toString());

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            return response.responseData("Update comment successfully", mapper.map(commentsRepository.save(data), CommentsDto.class));
        }).orElseGet(() -> response.responseError("Entity not found"));
    }

    @Override
    public Data deleteComment(Long id) {
        Optional<CommentsEntity> commentsEntity = commentsRepository.findById(id);

        return commentsEntity.map(data -> {
            commentsRepository.deleteById(id);
            return response.responseData("Delete comment successfully", mapper.map(data, CommentsDto.class));
        }).orElseGet(() -> response.responseError("Entity not found"));
    }
}
