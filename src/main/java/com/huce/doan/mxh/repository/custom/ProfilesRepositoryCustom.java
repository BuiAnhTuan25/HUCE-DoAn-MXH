package com.huce.doan.mxh.repository.custom;

import com.huce.doan.mxh.model.dto.ProfileSearchRequest;
import com.huce.doan.mxh.model.entity.ProfilesEntity;

import java.util.List;

public interface ProfilesRepositoryCustom {
    List<ProfilesEntity> search(ProfileSearchRequest request, int page, int pageSize);
    Long count(ProfileSearchRequest request);
}
