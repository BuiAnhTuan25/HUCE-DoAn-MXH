package com.huce.doan.mxh.repository;

import com.huce.doan.mxh.model.entity.FriendsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendsRepository extends JpaRepository<FriendsEntity, Long> {
    Page<FriendsEntity> findByMeId(@Param("me_id") Long meId,Pageable pageable);
    Optional<FriendsEntity> findByFriendCode(@Param("friend_code") Long friendCode);
}
