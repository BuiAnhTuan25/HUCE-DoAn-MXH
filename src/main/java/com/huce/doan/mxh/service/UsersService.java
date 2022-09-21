package com.huce.doan.mxh.service;

import com.huce.doan.mxh.model.dto.UsersDto;
import com.huce.doan.mxh.model.response.Data;

public interface UsersService {
    Data getUser(Long id);

    Data createUser(UsersDto user);

    Data updateUser(UsersDto user, Long id);

    Data deleteUser(Long id);
}
