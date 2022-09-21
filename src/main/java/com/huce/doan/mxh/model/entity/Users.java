package com.huce.doan.mxh.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huce.doan.mxh.constains.ProviderEnum;
import com.huce.doan.mxh.constains.StatusEnum;
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
public class Users {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status", unique = true)
    private StatusEnum status;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    @Column(name = "is_profile", unique = true)
    private Boolean isProfile;

    @Column(name = "email", unique = true)
    private String mail;

    @Column(name = "update_password_token", length = 64)
    private String updatePasswordToken;

    @Column(name = "provider", unique = true)
    private ProviderEnum provider;
}
