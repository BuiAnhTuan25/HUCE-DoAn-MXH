package com.huce.doan.mxh.service.impl;

import com.huce.doan.mxh.model.dto.LikesDto;
import com.huce.doan.mxh.model.entity.LikesEntity;
import com.huce.doan.mxh.model.entity.PostsEntity;
import com.huce.doan.mxh.model.response.Data;
import com.huce.doan.mxh.model.response.ListData;
import com.huce.doan.mxh.model.response.Pagination;
import com.huce.doan.mxh.model.response.Response;
import com.huce.doan.mxh.repository.LikesRepository;
import com.huce.doan.mxh.repository.PostsRepository;
import com.huce.doan.mxh.service.LikesService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class LikesServiceImpl implements LikesService {
    private final LikesRepository likesRepository;
    private final PostsRepository postsRepository;
    private final ModelMapper mapper;
    private final Response response;

    @Override
    public ListData getByPostId(Long id, int page, int pageSize) {
        Page<LikesEntity> likes = likesRepository.findByPostId(id, PageRequest.of(page, pageSize));

        return response.responseListData(likes.getContent(), new Pagination(likes.getNumber(), likes.getSize(), likes.getTotalPages(),
                (int) likes.getTotalElements()));
    }

    @Override
    public Data createLike(LikesDto like) {
        LikesEntity likesEntity = new LikesEntity().mapperLikesDto(like);
        likesEntity=likesRepository.save(likesEntity);
        PostsEntity post = postsRepository.getById(likesEntity.getPostId());
        post.setCountLikes(post.getCountLikes()+1);
        postsRepository.save(post);

        return response.responseData("Create like successfully", mapper.map(likesEntity, LikesDto.class));
    }

    @Override
    public Data deleteLike(Long postId, Long userId) {
        Optional<LikesEntity> likesEntity = likesRepository.findByPostIdAndUserId(postId, userId);
        if(likesEntity.isPresent()){
            PostsEntity post = postsRepository.getById(likesEntity.get().getPostId());
            post.setCountLikes(post.getCountLikes()-1);
            postsRepository.save(post);
            likesRepository.deleteById(likesEntity.get().getId());
            return response.responseData("Dislike successfully", mapper.map(likesEntity.get(), LikesDto.class));
        } else return response.responseError("Entity not found");
    }
}
