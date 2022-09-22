package com.huce.doan.mxh.service.impl;

import com.huce.doan.mxh.model.dto.LikesDto;
import com.huce.doan.mxh.model.entity.FriendsEntity;
import com.huce.doan.mxh.model.entity.LikesEntity;
import com.huce.doan.mxh.model.response.Data;
import com.huce.doan.mxh.model.response.ListData;
import com.huce.doan.mxh.model.response.Pagination;
import com.huce.doan.mxh.model.response.Response;
import com.huce.doan.mxh.repository.LikesRepository;
import com.huce.doan.mxh.service.LikesService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class LikesServiceImpl implements LikesService {
    private final LikesRepository likesRepository;
    private final ModelMapper mapper;
    private final Response response;

    @Override
    public ListData getByPostId(Long id, int page, int pageSize){
        Page<LikesEntity> likes = likesRepository.findByPostId(id, PageRequest.of(page, pageSize));

        return response.responseListData(likes.getContent(), new Pagination(likes.getNumber(), likes.getSize(), likes.getTotalPages(),
                (int) likes.getTotalElements()));
    }

    @Override
    public Data createLike(LikesDto like){
        LikesEntity likesEntity = new LikesEntity().mapperLikesDto(like);

        return response.responseData(mapper.map(likesRepository.save(likesEntity),LikesDto.class));
    }

    @Override
    public Data deleteLike(Long postId, Long userId){
        LikesEntity likesEntity = likesRepository.findByPostIdAndUserId(postId, userId).orElseThrow(EntityNotFoundException::new);
        likesRepository.deleteById(likesEntity.getId());

        return response.responseData(mapper.map(likesEntity,LikesDto.class));
    }
}
