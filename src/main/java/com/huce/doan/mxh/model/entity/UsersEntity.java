package com.huce.doan.mxh.model.entity;

import com.huce.doan.mxh.constains.ActiveStatusEnum;
import com.huce.doan.mxh.constains.ProviderEnum;
import com.huce.doan.mxh.constains.StatusEnum;
import com.huce.doan.mxh.model.dto.UsersDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private StatusEnum status;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    @Column(name = "is_profile")
    private Boolean isProfile;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "provider")
    private ProviderEnum provider;

    public UsersEntity mapperUsersDto(UsersDto user) {
        this.id = user.getId();
        this.isProfile = user.getIsProfile();
        this.email = user.getEmail();
        this.provider = user.getProvider();
        this.username = user.getUsername();
        this.password = user.getPassword();

        return this;
    }
}
