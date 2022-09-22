package com.huce.doan.mxh.repository;

import com.huce.doan.mxh.model.entity.PostsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<PostsEntity, Long> {
    Page<PostsEntity> findByAuthorId(Pageable pageable, @Param("author_id") Long authorId);
}
