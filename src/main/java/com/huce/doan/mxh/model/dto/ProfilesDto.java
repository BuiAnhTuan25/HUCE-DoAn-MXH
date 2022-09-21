package com.huce.doan.mxh.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfilesDto {
    private Long id;

    private String name;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private LocalDate birthday;

    private String address;

    @JsonProperty("is_public")
    private Boolean is_public;
}
