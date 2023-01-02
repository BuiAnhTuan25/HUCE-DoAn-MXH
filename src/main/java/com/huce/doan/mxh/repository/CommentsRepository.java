package com.huce.doan.mxh.repository;

import com.huce.doan.mxh.model.dto.CommentsDto;
import com.huce.doan.mxh.model.dto.MessagesDto;
import com.huce.doan.mxh.model.entity.CommentsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<CommentsEntity, Long> {
    Page<CommentsEntity> findByPostId(@Param("post_id") Long postId, Pageable pageable);

    @Query("select new com.huce.doan.mxh.model.dto.CommentsDto(c.id,c.postId,c.userId,c.content,c.pictureUrl,c.commentTime,p.name,p.avatarUrl) " +
            " from CommentsEntity c join ProfilesEntity p on c.userId=p.id where c.postId=:postId and c.status = 1 order by c.commentTime desc")
    Page<CommentsDto> getListCommentByPostId(@Param("postId") Long postId, Pageable pageable);

}
