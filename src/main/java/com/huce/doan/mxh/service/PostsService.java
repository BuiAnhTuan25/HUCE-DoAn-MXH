package com.huce.doan.mxh.service;

import com.huce.doan.mxh.model.dto.PostsDto;
import com.huce.doan.mxh.model.response.Data;
import com.huce.doan.mxh.model.response.ListData;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PostsService {
    Data getPost(Long id, Long idMe);

    ListData search(Long id, String content, int page, int pageSize);

    ListData getPosts(Long authorId, Long userId, int page, int pageSize);

    ListData getNewsFeed(Long userId, int page, int pageSize);

    Data createPost(PostsDto post, MultipartFile picture);

    Data updatePost(PostsDto post, MultipartFile picture, Long id);

    Data deletePost(Long id);
}
