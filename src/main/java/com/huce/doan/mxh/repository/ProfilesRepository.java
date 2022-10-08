package com.huce.doan.mxh.repository;

import com.huce.doan.mxh.constains.StatusEnum;
import com.huce.doan.mxh.model.entity.ProfilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfilesRepository extends JpaRepository<ProfilesEntity, Long> {
    Optional<ProfilesEntity> findByIdAndStatus(@Param("id") Long id, @Param("status") StatusEnum status);

    Optional<ProfilesEntity> findByPhoneNumber(@Param("phone_number") String phoneNumber);
}
