package com.huce.doan.mxh.repository;

import com.huce.doan.mxh.model.entity.CommentsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<CommentsEntity, Long> {
    Page<CommentsEntity> findByPostId(@Param("post_id") Long postId, Pageable pageable);
}
