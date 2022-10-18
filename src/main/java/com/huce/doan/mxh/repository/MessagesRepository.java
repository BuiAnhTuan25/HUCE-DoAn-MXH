package com.huce.doan.mxh.repository;

import com.huce.doan.mxh.model.dto.FriendChatDto;
import com.huce.doan.mxh.model.dto.MessagesDto;
import com.huce.doan.mxh.model.entity.MessagesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends JpaRepository<MessagesEntity, Long> {
//    Page<MessagesEntity> findBySenderIdAndReceiverId(@Param("sender_id") Long senderId,@Param("receiver_id") Long receiverId, Pageable pageable);
    @Query("select m from MessagesEntity m where (m.senderId=:sender_id and m.receiverId=:receiver_id) or (m.senderId=:receiver_id and m.receiverId=:sender_id)")
    Page<MessagesEntity> findBySenderIdAndReceiverId(@Param("sender_id") Long senderId,@Param("receiver_id") Long receiverId, Pageable pageable);

    Page<MessagesEntity> findByReceiverId(@Param("receiver_id") Long receiverId, Pageable pageable);

    @Query("select new com.huce.doan.mxh.model.dto.MessagesDto(m.id,m.receiverId,m.senderId,m.content,m.sendTime,m.messageType,p.name,p.avatarUrl) " +
            " from MessagesEntity m join ProfilesEntity p on m.senderId=p.id where (m.senderId=:senderId and m.receiverId=:receiverId) or (m.senderId=:receiverId and m.receiverId=:senderId)")
    Page<MessagesDto> getListMessageBySenderAndReceiver(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId, Pageable pageable);

    @Query("select new com.huce.doan.mxh.model.dto.MessagesDto(m.id,m.receiverId,m.senderId,m.content,m.sendTime,m.messageType,p.name,p.avatarUrl) " +
            " from MessagesEntity m join ProfilesEntity p on m.senderId=p.id where m.receiverId=:receiverId")
    Page<MessagesDto> getListMessageByReceiver(@Param("receiverId") Long receiverId, Pageable pageable);

    @Query("select new com.huce.doan.mxh.model.dto.FriendChatDto(p.id,p.name,p.avatarUrl,max(m.sendTime)) FROM ProfilesEntity p join  MessagesEntity m on m.senderId=p.id or m.receiverId=p.id where (m.senderId=:idMe or m.receiverId=:idMe) and p.id <>:idMe group by p.id order by max(m.sendTime) desc")
    Page<FriendChatDto> getListFriendChat(@Param("idMe") Long idMe, Pageable pageable);

}
