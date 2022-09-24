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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.Map;

@Service
@AllArgsConstructor
public class CommentsServiceImpl implements CommentsService {
    private final CommentsRepository commentsRepository;
    private final ModelMapper mapper;
    private final Cloudinary cloudinary;
    private final Response response;

    @Override
    public Data getComment(Long id) {
        CommentsEntity commentsEntity = commentsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return response.responseData(mapper.map(commentsEntity, CommentsDto.class));
    }

    @Override
    public ListData getByPostId(Long id, int page, int pageSize) {
        Page<CommentsEntity> comments = commentsRepository.findByPostId(id, PageRequest.of(page, pageSize));

        return response.responseListData(comments.getContent(), new Pagination(comments.getNumber(), comments.getSize(), comments.getTotalPages(),
                (int) comments.getTotalElements()));
    }

    @Override
    public Data createComment(CommentsDto comment, MultipartFile picture) {
        CommentsEntity commentsEntity = new CommentsEntity().mapperCommentsDto(comment);

        if (picture != null) {
            try {
                Map x = this.cloudinary.uploader().upload(picture.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                commentsEntity.setPictureUrl(x.get("url").toString());

            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return response.responseData(mapper.map(commentsRepository.save(commentsEntity), CommentsDto.class));
    }

    @Override
    public Data updateComment(CommentsDto comment, MultipartFile picture, Long id) {
        comment.setId(id);
        CommentsEntity commentsEntity = commentsRepository.findById(id).orElseThrow(EntityNotFoundException::new).mapperCommentsDto(comment);

        if (picture != null) {
            try {
                Map x = this.cloudinary.uploader().upload(picture.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                commentsEntity.setPictureUrl(x.get("url").toString());

            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return response.responseData(mapper.map(commentsRepository.save(commentsEntity), CommentsDto.class));
    }

    @Override
    public Data deleteComment(Long id) {
        CommentsEntity commentsEntity = commentsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        commentsRepository.deleteById(id);

        return response.responseData(mapper.map(commentsEntity, CommentsDto.class));
    }
}
