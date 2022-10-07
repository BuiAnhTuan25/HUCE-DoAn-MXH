package com.huce.doan.mxh.repository;

import com.huce.doan.mxh.model.dto.FriendsDto;
import com.huce.doan.mxh.model.entity.FriendsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendsRepository extends JpaRepository<FriendsEntity, Long> {
    Optional<FriendsEntity> findByMeIdAndFriendId(@Param("me_id") Long meId,@Param("friend_id") Long friendId);

    Page<FriendsEntity> findByMeId(@Param("me_id") Long meId, Pageable pageable);

    @Query("select new com.huce.doan.mxh.model.dto.FriendsDto(f.id,f.friendId,f.meId,f.friendStatus,p.name,p.avatarUrl) " +
            " from FriendsEntity f join ProfilesEntity p on f.friendId=p.id where f.meId=:meId")
    Page<FriendsDto> getListFriendByMeId(@Param("meId") Long meId, Pageable pageable);

    @Query("select new com.huce.doan.mxh.model.dto.FriendsDto(f.id,f.friendId,f.meId,f.friendStatus,p.name,p.avatarUrl) " +
            " from FriendsEntity f join ProfilesEntity p on f.friendId=p.id where p.phoneNumber=:phoneNumber")
    Optional<FriendsDto> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
