package com.huce.doan.mxh.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.huce.doan.mxh.constains.StatusEnum;
import com.huce.doan.mxh.model.dto.ProfilesDto;
import com.huce.doan.mxh.model.entity.ProfilesEntity;
import com.huce.doan.mxh.model.response.Data;
import com.huce.doan.mxh.model.response.Response;
import com.huce.doan.mxh.repository.ProfilesRepository;
import com.huce.doan.mxh.service.ProfilesService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfilesServiceImpl implements ProfilesService {

    private final ProfilesRepository profilesRepository;
    private final ModelMapper mapper;
    private final Response response;
    private final Cloudinary cloudinary;

    @Override
    public Data getProfile(Long id) {
        Optional<ProfilesEntity> profilesEntity = profilesRepository.findByIdAndStatus(id, StatusEnum.ACTIVE);

        return profilesEntity.map(data -> response.responseData("Get profile successfully", mapper.map(profilesEntity, ProfilesDto.class))).orElseGet(() -> response.responseError("Entity not found"));
    }

    @Override
    public Data createProfile(ProfilesDto profile, MultipartFile avatar) {
        ProfilesEntity profilesEntity = new ProfilesEntity().mapperProfilesDto(profile);
        profilesEntity.setStatus(StatusEnum.ACTIVE);
        if (avatar != null) {
            try {
                Map x = this.cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                profilesEntity.setAvatarUrl(x.get("url").toString());

            } catch (Exception e) {
                System.out.println(e);
            }
        } else
            profilesEntity.setAvatarUrl(
                    "https://res.cloudinary.com/anhtuanbui/image/upload/v1657248868/knybg0tx6rj48d62nv4a.png");

        return response.responseData("Create profile successfully", mapper.map(profilesRepository.save(profilesEntity), ProfilesDto.class));
    }

    @Override
    public Data updateProfile(ProfilesDto profile, MultipartFile avatar, Long id) {
        profile.setId(id);
        Optional<ProfilesEntity> profilesEntity = profilesRepository.findByIdAndStatus(id, StatusEnum.ACTIVE);
        return profilesEntity.map(data -> {
            data = data.mapperProfilesDto(profile);
            if (avatar != null) {
                try {
                    Map x = this.cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                    data.setAvatarUrl(x.get("url").toString());

                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            return response.responseData("Update profile successfully", mapper.map(profilesRepository.save(data), ProfilesDto.class));
        }).orElseGet(() -> response.responseError("Entity not found"));

    }

    @Override
    public Data deleteProfile(Long id) {
        Optional<ProfilesEntity> profilesEntity = profilesRepository.findByIdAndStatus(id, StatusEnum.ACTIVE);

        return profilesEntity.map(data -> {
            data.setStatus(StatusEnum.INACTIVE);

            return response.responseData("Delete profile success", mapper.map(data, ProfilesDto.class));
        }).orElseGet(() -> response.responseError("Entity not found"));

    }
}
