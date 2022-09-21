package com.huce.doan.mxh.repository;

import com.huce.doan.mxh.model.entity.MessagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends JpaRepository<MessagesEntity, Long> {
}
