package com.huce.doan.mxh.service;

import com.huce.doan.mxh.constains.StatusEnum;
import com.huce.doan.mxh.model.dto.ProfilesDto;
import com.huce.doan.mxh.model.response.Data;
import org.springframework.web.multipart.MultipartFile;

public interface ProfilesService {
    Data getProfile(Long id);

    Data findByPhoneNumber(String phoneNumber);

    Data createProfile(ProfilesDto user, MultipartFile avatar);

    Data updateProfile(ProfilesDto user, MultipartFile avatar, Long id);

    Data deleteProfile(Long id);
}
