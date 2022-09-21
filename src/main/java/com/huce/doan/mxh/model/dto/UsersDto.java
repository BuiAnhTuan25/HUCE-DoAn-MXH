package com.huce.doan.mxh.model.dto;

import com.huce.doan.mxh.constains.ProviderEnum;
import com.huce.doan.mxh.constains.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsersDto {
    private Long id;

    private String username;

    private String password;

    private Boolean isProfile;

    private String mail;

    private ProviderEnum provider;
}
