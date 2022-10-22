package com.huce.doan.mxh.model.entity;

import com.huce.doan.mxh.constains.ActiveStatusEnum;
import com.huce.doan.mxh.constains.GenderEnum;
import com.huce.doan.mxh.constains.StatusEnum;
import com.huce.doan.mxh.model.dto.ProfilesDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfilesEntity {
    @Id
    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "gender")
    private GenderEnum gender;

    @Column(name = "address")
    private String address;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "status")
    private StatusEnum status;

    @Column(name = "active_status")
    private ActiveStatusEnum activeStatus;

    public ProfilesEntity mapperProfilesDto(ProfilesDto profile) {
        this.id = profile.getId();
        this.name = profile.getName();
        this.phoneNumber = profile.getPhoneNumber();
        this.birthday = profile.getBirthday();
        this.address = profile.getAddress();
        this.isPublic = profile.getIsPublic();
        this.avatarUrl = profile.getAvatarUrl();
        this.gender = profile.getGender();

        return this;
    }
}
