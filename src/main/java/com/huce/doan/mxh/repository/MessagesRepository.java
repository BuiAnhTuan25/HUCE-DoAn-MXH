package com.huce.doan.mxh.repository;

import com.huce.doan.mxh.constains.MessageStatusEnum;
import com.huce.doan.mxh.model.dto.FriendChatDto;
import com.huce.doan.mxh.model.dto.MessagesDto;
import com.huce.doan.mxh.model.entity.MessagesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<MessagesEntity, Long> {
//    Page<MessagesEntity> findBySenderIdAndReceiverId(@Param("sender_id") Long senderId,@Param("receiver_id") Long receiverId, Pageable pageable);
    @Query("select m from MessagesEntity m where (m.senderId=:sender_id and m.receiverId=:receiver_id) or (m.senderId=:receiver_id and m.receiverId=:sender_id)")
    Page<MessagesEntity> findBySenderIdAndReceiverId(@Param("sender_id") Long senderId,@Param("receiver_id") Long receiverId, Pageable pageable);

    Page<MessagesEntity> findByReceiverId(@Param("receiver_id") Long receiverId, Pageable pageable);

    @Query("select new com.huce.doan.mxh.model.dto.MessagesDto(m.id,m.receiverId,m.senderId,m.content,m.sendTime,m.messageType,m.messageStatus,p.name,p.avatarUrl) " +
            " from MessagesEntity m join ProfilesEntity p on m.senderId=p.id where (m.senderId=:senderId and m.receiverId=:receiverId) or (m.senderId=:receiverId and m.receiverId=:senderId) order by m.sendTime desc")
    Page<MessagesDto> getListMessageBySenderAndReceiver(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId, Pageable pageable);

    @Query("select new com.huce.doan.mxh.model.dto.MessagesDto(m.id,m.receiverId,m.senderId,m.content,m.sendTime,m.messageType,m.messageStatus,p.name,p.avatarUrl) " +
            " from MessagesEntity m join ProfilesEntity p on m.senderId=p.id where m.receiverId=:receiverId order by m.sendTime desc")
    Page<MessagesDto> getListMessageByReceiver(@Param("receiverId") Long receiverId, Pageable pageable);

    @Query("select new com.huce.doan.mxh.model.dto.MessagesDto(m.id,m.receiverId,m.senderId,m.content,m.sendTime,m.messageType,m.messageStatus) " +
            " from MessagesEntity m where m.receiverId=:receiverId and m.messageStatus=1 and m.messageType=0")
    List<MessagesDto> getListMessageNotSeen(@Param("receiverId") Long receiverId);

    @Query("select new com.huce.doan.mxh.model.dto.MessagesDto(m.id,m.receiverId,m.senderId,m.content,m.sendTime,m.messageType,m.messageStatus) " +
            " from MessagesEntity m where m.receiverId=:receiverId and m.messageStatus=1 and m.messageType=1")
    List<MessagesDto> getListNotificationNotSeen(@Param("receiverId") Long receiverId);

    @Query("select new com.huce.doan.mxh.model.dto.FriendChatDto(p.id,p.name,p.avatarUrl,max(m.sendTime),p.activeStatus) FROM ProfilesEntity p join  MessagesEntity m on m.senderId=p.id or m.receiverId=p.id where (m.senderId=:idMe or m.receiverId=:idMe) and p.id <>:idMe group by p.id order by max(m.sendTime) desc")
    Page<FriendChatDto> getListFriendChat(@Param("idMe") Long idMe, Pageable pageable);

    @Query("select new com.huce.doan.mxh.model.dto.FriendChatDto(p.id,p.name,p.avatarUrl,max(m.sendTime),p.activeStatus) FROM ProfilesEntity p join  MessagesEntity m on m.senderId=p.id or m.receiverId=p.id where (m.senderId=:idMe or m.receiverId=:idMe) and p.id <>:idMe and (p.name like %:fullTextSearch% or p.phoneNumber like %:fullTextSearch%) group by p.id order by max(m.sendTime) desc")
    Page<FriendChatDto> findFriendChat(@Param("idMe") Long idMe, @Param("fullTextSearch") String fullTextSearch, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update MessagesEntity m set m.messageStatus = 0 where m.id in (:listId)")
    void updateMessageStatus(@Param("listId") List<Long> listId);
}
