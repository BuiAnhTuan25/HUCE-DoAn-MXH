package com.huce.doan.mxh.repository;

import com.huce.doan.mxh.constains.StatusEnum;
import com.huce.doan.mxh.model.entity.UsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
    Optional<UsersEntity> findByIdAndStatus(@Param("id") Long id, @Param("status") StatusEnum status);

    Optional<UsersEntity> findByUsername(@Param("username") String username);

    UsersEntity findByEmail(@Param("email") String email);

    @Query("SELECT u FROM UsersEntity u WHERE u.username = :username")
    UsersEntity getUserByUsername(@Param("username") String username);

    Optional<UsersEntity> findByUpdatePasswordToken(String token);

    Optional<UsersEntity> findByVerificationCode(String verificationCode);
}
