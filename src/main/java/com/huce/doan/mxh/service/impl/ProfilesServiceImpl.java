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

import javax.persistence.EntityExistsException;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProfilesServiceImpl implements ProfilesService {

    private ProfilesRepository profilesRepository;
    private ModelMapper mapper;
    private Response response;
    private Cloudinary cloudinary;

    @Override
    public Data getProfile(Long id) {
        ProfilesEntity profilesEntity = profilesRepository.findByIdAndStatus(id, StatusEnum.ACTIVE).orElseThrow(EntityExistsException::new);
        return response.responseData(mapper.map(profilesEntity, ProfilesDto.class));
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

        return response.responseData(mapper.map(profilesRepository.save(profilesEntity), ProfilesDto.class));
    }

    @Override
    public Data updateProfile(ProfilesDto profile, MultipartFile avatar, Long id) {
        profile.setId(id);
        ProfilesEntity profilesEntity = profilesRepository.findByIdAndStatus(id, StatusEnum.ACTIVE).orElseThrow(EntityExistsException::new).mapperProfilesDto(profile);
        if (avatar != null) {
            try {
                Map x = this.cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                profilesEntity.setAvatarUrl(x.get("url").toString());

            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return response.responseData(mapper.map(profilesRepository.save(profilesEntity), ProfilesDto.class));
    }

    @Override
    public Data deleteProfile(Long id) {
        ProfilesEntity profilesEntity = profilesRepository.findByIdAndStatus(id, StatusEnum.ACTIVE).orElseThrow(EntityExistsException::new);
        profilesEntity.setStatus(StatusEnum.INACTIVE);

        return response.responseData(mapper.map(profilesEntity, ProfilesDto.class));
    }
}
