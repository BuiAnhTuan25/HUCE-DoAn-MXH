package com.huce.doan.mxh.model.entity;

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
public class Profiles {
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

    @Column(name = "is_public", unique = true)
    private Boolean is_public;
}
