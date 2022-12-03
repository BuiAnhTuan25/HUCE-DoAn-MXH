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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostsServiceImpl implements PostsService {

    private final PostsRepository postsRepository;
    private final ModelMapper mapper;
    private final Cloudinary cloudinary;
    private final Response response;

    @Override
    public Data getPost(Long id,Long idMe) {
        Optional<PostsDto> postsDto = postsRepository.getPost(id, idMe);

        return postsDto.map(data -> response.responseData("Get post successfully", data)).orElseGet(() -> response.responseError("Entity not found"));
    }

    @Override
    public ListData search(Long id, String content, int page, int pageSize) {
        Page<PostsDto> posts = postsRepository.search(id, content, PageRequest.of(page, pageSize));
        return response.responseListData(posts.getContent(), new Pagination(posts.getNumber(), posts.getSize(), posts.getTotalPages(),
                (int) posts.getTotalElements()));
    }

    @Override
    public ListData getPosts(Long authorId, Long id, int page, int pageSize) {
        Page<PostsDto> posts = postsRepository.getMyPosts(authorId, id, PageRequest.of(page, pageSize));

        return response.responseListData(posts.getContent(), new Pagination(posts.getNumber(), posts.getSize(), posts.getTotalPages(),
                (int) posts.getTotalElements()));
    }

    @Override
    public ListData getNewsFeed(Long id, int page, int pageSize) {
        Page<PostsDto> posts = postsRepository.getNewsFeed(id, PageRequest.of(page, pageSize));

        return response.responseListData(posts.getContent(), new Pagination(posts.getNumber(), posts.getSize(), posts.getTotalPages(),
                (int) posts.getTotalElements()));
    }

    @Override
    public Data createPost(PostsDto post, MultipartFile picture) {
        PostsEntity postsEntity = new PostsEntity().mapperPostsDto(post);
        postsEntity.setPostingTime(LocalDateTime.now());

        if (picture != null) {
            try {
                Map x = this.cloudinary.uploader().upload(picture.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                postsEntity.setPictureUrl(x.get("url").toString());

            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return response.responseData("Create post successfully", mapper.map(postsRepository.save(postsEntity), PostsDto.class));
    }

    @Override
    public Data updatePost(PostsDto post, MultipartFile picture, Long id) {
        post.setId(id);
        Optional<PostsEntity> postsEntity = postsRepository.findById(id);

        return postsEntity.map(data -> {
            data = data.mapperPostsDto(post);
            if (picture != null) {
                try {
                    Map x = this.cloudinary.uploader().upload(picture.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                    data.setPictureUrl(x.get("url").toString());

                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            return response.responseData("Update post successfully", mapper.map(postsRepository.save(data), PostsDto.class));
        }).orElseGet(() -> response.responseError("Entity not found"));


    }

    @Override
    public Data deletePost(Long id) {
        Optional<PostsEntity> postsEntity = postsRepository.findById(id);

        return postsEntity.map(data -> {
            postsRepository.deleteById(id);

            return response.responseData("Delete post successfully", mapper.map(data, PostsDto.class));
        }).orElseGet(() -> response.responseError("Entity not found"));
    }
}
