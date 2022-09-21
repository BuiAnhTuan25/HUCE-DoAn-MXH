package com.huce.doan.mxh.repository;

import com.huce.doan.mxh.constains.StatusEnum;
import com.huce.doan.mxh.model.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
    Optional<UsersEntity> findByIdAndStatus(@Param("id") Long id, @Param("status") StatusEnum status);
}
