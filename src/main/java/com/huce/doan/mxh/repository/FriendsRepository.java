package com.huce.doan.mxh.repository;

import com.huce.doan.mxh.model.entity.FriendsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendsRepository extends JpaRepository<FriendsEntity, Long> {
}
