package com.huce.doan.mxh.service;

import com.huce.doan.mxh.model.dto.LikesDto;
import com.huce.doan.mxh.model.response.Data;
import com.huce.doan.mxh.model.response.ListData;

public interface LikesService {
    ListData getByPostId(Long postId, int page, int pageSize);

    Data createLike(LikesDto post);

    Data deleteLike(Long postId, Long userId);
}
