package com.huce.doan.mxh.service;

import com.huce.doan.mxh.constains.ProviderEnum;
import com.huce.doan.mxh.model.dto.UpdatePasswordDto;
import com.huce.doan.mxh.model.dto.UserRegister;
import com.huce.doan.mxh.model.dto.UsersDto;
import com.huce.doan.mxh.model.entity.UsersEntity;
import com.huce.doan.mxh.model.response.Data;
import org.springframework.security.core.userdetails.UserDetails;

import javax.mail.MessagingException;

public interface UsersService {
    Data getUser(Long id);

    Data createUser(UserRegister user, StringBuffer siteUrl) throws MessagingException;

    Data updateUser(UsersDto user, Long id);

    Data deleteUser(Long id);

    UserDetails loadUserByUsername(String username);

    UserDetails loadUserById(Long id);

    UsersEntity processOAuthPostLogin(UsersEntity userEntity, ProviderEnum provider);

    Data verify(String verificationCode);

    Data changePassword(Long id, UpdatePasswordDto passwordDto);

    Data forgotPassword(String mail) throws MessagingException;
}
