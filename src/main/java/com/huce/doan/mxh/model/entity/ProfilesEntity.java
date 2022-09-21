package com.huce.doan.mxh.model.entity;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birthday", unique = true)
    private LocalDate birthday;

    @Column(name = "address")
    private String address;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "is_public", unique = true)
    private Boolean isPublic;

    @Column(name = "status", unique = true)
    private StatusEnum status;

    public ProfilesEntity mapperProfilesDto(ProfilesDto profile) {
        this.id = profile.getId();
        this.name = profile.getName();
        this.phoneNumber = profile.getPhoneNumber();
        this.birthday = profile.getBirthday();
        this.address = profile.getAddress();
        this.isPublic = profile.getIsPublic();
        this.avatarUrl = profile.getAvatarUrl();

        return this;
    }
}
