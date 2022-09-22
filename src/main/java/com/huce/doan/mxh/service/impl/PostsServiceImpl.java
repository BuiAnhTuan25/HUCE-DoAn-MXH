package com.huce.doan.mxh.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.huce.doan.mxh.model.dto.PostsDto;
import com.huce.doan.mxh.model.entity.PostsEntity;
import com.huce.doan.mxh.model.response.Data;
import com.huce.doan.mxh.model.response.ListData;
import com.huce.doan.mxh.model.response.Pagination;
import com.huce.doan.mxh.model.response.Response;
import com.huce.doan.mxh.repository.PostsRepository;
import com.huce.doan.mxh.service.PostsService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PostsServiceImpl implements PostsService {

    private final PostsRepository postsRepository;
    private final ModelMapper mapper;
    private final Cloudinary cloudinary;
    private final Response response;

    @Override
    public Data getPost(Long id) {
        PostsEntity postsEntity = postsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return response.responseData(mapper.map(postsEntity, PostsDto.class));
    }

    @Override
    public ListData getByAuthorId(Long id, int page, int pageSize) {
        Page<PostsEntity> posts = postsRepository.findByAuthorId(id, PageRequest.of(page, pageSize));

        return response.responseListData(posts.getContent(), new Pagination(posts.getNumber(), posts.getSize(), posts.getTotalPages(),
                (int) posts.getTotalElements()));
    }

    @Override
    public Data createPost(PostsDto post, MultipartFile picture) {
        PostsEntity postsEntity = new PostsEntity().mapperPostsDto(post);

        if (picture != null) {
            try {
                Map x = this.cloudinary.uploader().upload(picture.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                postsEntity.setPictureUrl(x.get("url").toString());

            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return response.responseData(mapper.map(postsRepository.save(postsEntity), PostsDto.class));
    }

    @Override
    public Data updatePost(PostsDto post, MultipartFile picture, Long id) {
        post.setId(id);
        PostsEntity postsEntity = postsRepository.findById(id).orElseThrow(EntityNotFoundException::new).mapperPostsDto(post);

        if (picture != null) {
            try {
                Map x = this.cloudinary.uploader().upload(picture.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                postsEntity.setPictureUrl(x.get("url").toString());

            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return response.responseData(mapper.map(postsRepository.save(postsEntity), PostsDto.class));
    }

    @Override
    public Data deletePost(Long id) {
        PostsEntity postsEntity = postsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        postsRepository.deleteById(id);

        return response.responseData(mapper.map(postsEntity, PostsDto.class));
    }
}
