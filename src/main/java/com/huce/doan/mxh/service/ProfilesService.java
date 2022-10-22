package com.huce.doan.mxh.service;

import com.huce.doan.mxh.constains.ActiveStatusEnum;
import com.huce.doan.mxh.constains.StatusEnum;
import com.huce.doan.mxh.model.dto.ProfilesDto;
import com.huce.doan.mxh.model.response.Data;
import com.huce.doan.mxh.model.response.ListData;
import org.springframework.web.multipart.MultipartFile;

public interface ProfilesService {
    Data getProfile(Long id);

    Data findByPhoneNumber(String phoneNumber);

    ListData findProfilesFriendsByNameOrPhoneNumber(Long idMe, String fullTextSearch, int page, int pageSize);

    Data createProfile(ProfilesDto user, MultipartFile avatar);

    Data updateProfile(ProfilesDto user, MultipartFile avatar, Long id);

    Data updateActiveStatus(Long id, ActiveStatusEnum activeStatus);

    Data deleteProfile(Long id);
}
