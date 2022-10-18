package com.huce.doan.mxh.repository;

import com.huce.doan.mxh.model.entity.PostsEntity;
import com.huce.doan.mxh.model.dto.PostsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostsRepository extends JpaRepository<PostsEntity, Long> {
    Page<PostsEntity> findByAuthorId(@Param("author_id") Long authorId,Pageable pageable);

    @Query("select new com.huce.doan.mxh.model.dto.PostsDto(o.id,o.authorId,p.name,p.avatarUrl,o.content,o.pictureUrl,o.countLikes,o.privacy,o.postingTime,l.id) " +
            " from PostsEntity o join ProfilesEntity p on o.authorId=p.id left join LikesEntity l on l.userId =:idMe and l.postId = o.id where o.id=:id")
    Optional<PostsDto> getPost(@Param("id") Long id,@Param("idMe") Long idMe);

//    @Query("select new com.huce.doan.mxh.model.dto.PostsDto(o.id,o.authorId,p.name,p.avatarUrl,o.content,o.pictureUrl,o.countLikes,o.privacy,o.postingTime,false) " +
//            " from PostsEntity o join ProfilesEntity p on o.authorId=p.id where o.authorId=:authorId order by o.postingTime desc")
//    Page<PostsDto> getMyPosts(@Param("authorId") Long authorId, Pageable pageable);

    @Query("select new com.huce.doan.mxh.model.dto.PostsDto(o.id,o.authorId,p.name,p.avatarUrl,o.content,o.pictureUrl,o.countLikes,o.privacy,o.postingTime,l.id) "
            +" FROM PostsEntity o join ProfilesEntity p on o.authorId=p.id left join LikesEntity l on o.id = l.postId and l.userId=:authorId where o.authorId =:authorId order by o.postingTime desc")
    Page<PostsDto> getMyPosts(@Param("authorId") Long authorId, Pageable pageable);
    @Query("SELECT new com.huce.doan.mxh.model.dto.PostsDto(o.id,o.authorId,p.name,p.avatarUrl,o.content,o.pictureUrl,o.countLikes,o.privacy,o.postingTime,false) " +
            " FROM PostsEntity o left join LikesEntity l on o.id = l.postId and l.userId =:id join ProfilesEntity p on o.authorId=p.id where l.id is null and (o.privacy = 0 or o.authorId =:id or ( o.privacy = 1 and o.authorId in (select f.friendId from FriendsEntity f where f.meId=:id))) order by o.postingTime desc")
    Page<PostsDto> getNewsFeed(@Param("id") Long id, Pageable pageable);
}
