package com.huce.doan.mxh.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NotBlank
public class LoginResponse {
    private String jwt;
    private UsersDto user;
}
