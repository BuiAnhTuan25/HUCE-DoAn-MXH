package com.huce.doan.mxh.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huce.doan.mxh.constains.ActiveStatusEnum;
import com.huce.doan.mxh.constains.ProviderEnum;
import com.huce.doan.mxh.constains.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsersDto {
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @JsonProperty("is_profile")
    private Boolean isProfile;

    private String email;

    private ProviderEnum provider;

}
