package com.huce.doan.mxh.repository;

import com.huce.doan.mxh.model.entity.LikesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<LikesEntity, Long> {
    Page<LikesEntity> findByPostId(@Param("post_id") Long postId, Pageable pageable);
    Optional<LikesEntity> findByPostIdAndUserId(@Param("post_id") Long postId,@Param("user_id") Long userId);
}
