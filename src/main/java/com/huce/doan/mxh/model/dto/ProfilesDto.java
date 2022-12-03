package com.huce.doan.mxh.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.huce.doan.mxh.constains.ActiveStatusEnum;
import com.huce.doan.mxh.constains.GenderEnum;
import com.huce.doan.mxh.model.entity.FriendsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private GenderEnum gender;

    private String address;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("is_public")
    private Boolean isPublic;

    @JsonProperty("active_status")
    private ActiveStatusEnum activeStatus;

    private FriendsDto friend;

    public ProfilesDto(Long id, String name, String phoneNumber, LocalDate birthday, GenderEnum gender, String address, String avatarUrl, Boolean isPublic, ActiveStatusEnum activeStatus) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.avatarUrl = avatarUrl;
        this.isPublic = isPublic;
        this.activeStatus = activeStatus;
    }

    public void enrichFriend(FriendsDto friend) {
        this.friend = friend;
    }
}
