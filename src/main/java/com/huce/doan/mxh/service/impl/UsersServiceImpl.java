package com.huce.doan.mxh.service.impl;

import com.huce.doan.mxh.constains.StatusEnum;
import com.huce.doan.mxh.model.dto.UsersDto;
import com.huce.doan.mxh.model.entity.UsersEntity;
import com.huce.doan.mxh.model.response.Data;
import com.huce.doan.mxh.model.response.Response;
import com.huce.doan.mxh.repository.UsersRepository;
import com.huce.doan.mxh.service.UsersService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;
    private ModelMapper mapper;
    private Response response;

    @Override
    public Data getUser(Long id) {
        UsersEntity user = usersRepository.findByIdAndStatus(id, StatusEnum.ACTIVE).orElseThrow(EntityNotFoundException::new);

        return response.responseData(mapper.map(user, UsersDto.class));
    }

    @Override
    public Data createUser(UsersDto user) {
        UsersEntity userEntity = new UsersEntity().mapperUsersDto(user);
        userEntity.setStatus(StatusEnum.ACTIVE);

        return response.responseData(mapper.map(usersRepository.save(userEntity), UsersDto.class));
    }

    @Override
    public Data updateUser(UsersDto user, Long id) {
        user.setId(id);
        UsersEntity userEntity = usersRepository.findByIdAndStatus(id, StatusEnum.ACTIVE).orElseThrow(EntityNotFoundException::new);

        return response.responseData(mapper.map(usersRepository.save(userEntity.mapperUsersDto(user)), UsersDto.class));
    }

    @Override
    public Data deleteUser(Long id) {
        UsersEntity userEntity = usersRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userEntity.setStatus(StatusEnum.INACTIVE);
        usersRepository.save(userEntity);

        return response.responseData(mapper.map(userEntity, UsersDto.class));
    }

}
