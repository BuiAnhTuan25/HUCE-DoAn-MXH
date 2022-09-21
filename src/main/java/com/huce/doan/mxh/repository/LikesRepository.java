package com.huce.doan.mxh.repository;

import com.huce.doan.mxh.model.entity.LikesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<LikesEntity, Long> {
}
