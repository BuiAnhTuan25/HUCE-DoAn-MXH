package com.huce.doan.mxh.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.huce.doan.mxh.constains.ActiveStatusEnum;
import com.huce.doan.mxh.constains.MessageTypeEnum;
import com.huce.doan.mxh.constains.StatusEnum;
import com.huce.doan.mxh.model.dto.MessagesDto;
import com.huce.doan.mxh.model.dto.ProfileSearchRequest;
import com.huce.doan.mxh.model.dto.ProfilesDto;
import com.huce.doan.mxh.model.entity.MessagesEntity;
import com.huce.doan.mxh.model.entity.ProfilesEntity;
import com.huce.doan.mxh.model.response.Data;
import com.huce.doan.mxh.model.response.ListData;
import com.huce.doan.mxh.model.response.Pagination;
import com.huce.doan.mxh.model.response.Response;
import com.huce.doan.mxh.repository.ProfilesRepository;
import com.huce.doan.mxh.repository.custom.ProfilesRepositoryCustom;
import com.huce.doan.mxh.service.ProfilesService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfilesServiceImpl implements ProfilesService {

    private final ProfilesRepository profilesRepository;
    private final ProfilesRepositoryCustom profilesRepositoryCustom;
    private final ModelMapper mapper;
    private final Response response;
    private final Cloudinary cloudinary;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public Data getProfile(Long id) {
        Optional<ProfilesEntity> profilesEntity = profilesRepository.findByIdAndStatus(id, StatusEnum.ACTIVE);

        return profilesEntity.map(data -> response.responseData("Get profile successfully", mapper.map(data, ProfilesDto.class)))
                .orElseGet(() -> response.responseError("Entity not found"));
    }

    @Override
    public ListData search(ProfileSearchRequest request, int page, int pageSize) {
        List<ProfilesEntity> profiles = profilesRepositoryCustom.search(request, page, pageSize);
        List<ProfilesDto> profilesDto = profiles.stream().map(p -> this.mapper.map(p, ProfilesDto.class)).collect(Collectors.toList());
        int total = profilesRepositoryCustom.count(request).intValue();
        int totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        return new ListData(true, "Search profile successfully",200, profilesDto, new Pagination(page, pageSize, totalPage, total));
    }

    @Override
    public Data findByPhoneNumber(String phoneNumber){
        Optional<ProfilesEntity> profilesEntity = profilesRepository.findByPhoneNumber(phoneNumber);

        return profilesEntity.map(data->response.responseData("Get profile successfully", mapper.map(data, ProfilesDto.class))).orElseGet(() -> response.responseError("Entity not found"));
    }

    @Override
    public ListData findProfilesFriendsByNameOrPhoneNumber(Long idMe, String fullTextSearch, int page, int pageSize){
        Page<ProfilesDto> profilesDtoPage = profilesRepository.findProfilesFriendsByNameOrPhoneNumber(idMe,fullTextSearch, PageRequest.of(page, pageSize));

        return response.responseListData(profilesDtoPage.getContent(), new Pagination(profilesDtoPage.getNumber(), profilesDtoPage.getSize(), profilesDtoPage.getTotalPages(),
                (int) profilesDtoPage.getTotalElements()));
    }

    @Override
    public Data createProfile(ProfilesDto profile, MultipartFile avatar) {
        ProfilesEntity profilesEntity = new ProfilesEntity().mapperProfilesDto(profile);
        profilesEntity.setStatus(StatusEnum.ACTIVE);
        profilesEntity.setActiveStatus(ActiveStatusEnum.OFFLINE);
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
    public Data updateActiveStatus(Long id, ActiveStatusEnum activeStatus){
        Optional<ProfilesEntity> profilesEntity = profilesRepository.findById(id);

        if(profilesEntity.isPresent()){
            profilesEntity.get().setActiveStatus(activeStatus);
            MessagesDto message = new MessagesDto();
            message.setSenderId(id);
            message.setContent(activeStatus.toString());
            message.setMessageType(MessageTypeEnum.ACTIVE_STATUS);
            simpMessagingTemplate.convertAndSend("/topic/message",message);

            return response.responseData("Update active status successfully", mapper.map(profilesRepository.save(profilesEntity.get()), ProfilesDto.class));
        } else return response.responseError("Entity not found");
    }

    @Override
    public Data deleteProfile(Long id) {
        Optional<ProfilesEntity> profilesEntity = profilesRepository.findByIdAndStatus(id, StatusEnum.ACTIVE);

        return profilesEntity.map(data -> {
            data.setStatus(StatusEnum.INACTIVE);

            return response.responseData("Delete profile successfully", mapper.map(data, ProfilesDto.class));
        }).orElseGet(() -> response.responseError("Entity not found"));

    }
}
