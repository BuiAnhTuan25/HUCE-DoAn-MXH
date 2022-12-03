package com.huce.doan.mxh.model.dto;

import com.huce.doan.mxh.constains.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfileSearchRequest {
    private Long idMe;
    private String name;
    private String phoneNumber;
    private String address;
    private GenderEnum gender;
    private String sortBy;
}
