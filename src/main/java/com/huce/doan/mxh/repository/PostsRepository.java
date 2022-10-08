package com.huce.doan.mxh.repository;

import com.huce.doan.mxh.model.entity.PostsEntity;
import com.huce.doan.mxh.model.dto.PostsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<PostsEntity, Long> {
    Page<PostsEntity> findByAuthorId(@Param("author_id") Long authorId,Pageable pageable);

    @Query("select new com.huce.doan.mxh.model.dto.PostsDto(o.id,o.authorId,p.name,p.avatarUrl,o.content,o.pictureUrl,o.countLikes,o.privacy,o.postingTime) " +
            " from PostsEntity o join ProfilesEntity p on o.authorId=p.id where o.authorId=:authorId order by o.postingTime desc")
    Page<PostsDto> getListPostsByAuthorId(@Param("authorId") Long authorId, Pageable pageable);
}
