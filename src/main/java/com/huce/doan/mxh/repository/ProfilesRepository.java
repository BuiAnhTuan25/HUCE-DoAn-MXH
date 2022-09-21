package com.huce.doan.mxh.repository;

import com.huce.doan.mxh.model.entity.ProfilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilesRepository extends JpaRepository<ProfilesEntity, Long> {
}
