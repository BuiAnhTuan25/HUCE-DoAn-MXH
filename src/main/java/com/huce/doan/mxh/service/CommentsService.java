package com.huce.doan.mxh.service;

import com.huce.doan.mxh.model.dto.CommentsDto;
import com.huce.doan.mxh.model.response.Data;
import com.huce.doan.mxh.model.response.ListData;
import org.springframework.web.multipart.MultipartFile;

public interface CommentsService {
    Data getComment(Long id);

    ListData getByPostId(Long postId, int page, int pageSize);

    Data createComment(CommentsDto post, MultipartFile picture);

    Data updateComment(CommentsDto post, MultipartFile picture, Long id);

    Data deleteComment(Long id);
}
