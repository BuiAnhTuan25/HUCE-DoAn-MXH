package com.huce.doan.mxh.repository;

import com.huce.doan.mxh.constains.StatusEnum;
import com.huce.doan.mxh.model.dto.ProfilesDto;
import com.huce.doan.mxh.model.entity.ProfilesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfilesRepository extends JpaRepository<ProfilesEntity, Long> {
    Optional<ProfilesEntity> findByIdAndStatus(@Param("id") Long id, @Param("status") StatusEnum status);

    Optional<ProfilesEntity> findByPhoneNumber(@Param("phone_number") String phoneNumber);

    @Query("SELECT distinct new com.huce.doan.mxh.model.dto.ProfilesDto(p.id,p.name,p.phoneNumber,p.birthday,p.gender,p.address,p.avatarUrl,p.isPublic,p.activeStatus) FROM FriendsEntity f join ProfilesEntity p on f.friendId=p.id where p.id <>:idMe and f.friendStatus=0 and (p.name like %:fullTextSearch% or p.phoneNumber like %:fullTextSearch%)")
    Page<ProfilesDto> findProfilesFriendsByNameOrPhoneNumber(@Param("idMe") Long idMe, @Param("fullTextSearch") String fullTextSearch, Pageable pageable);
}
