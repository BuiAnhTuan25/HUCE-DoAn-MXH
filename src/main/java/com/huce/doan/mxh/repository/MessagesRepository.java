package com.huce.doan.mxh.repository;

import com.huce.doan.mxh.model.dto.FriendResponse;
import com.huce.doan.mxh.model.dto.MessageResponse;
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

    @Query("select new com.huce.doan.mxh.model.dto.MessageResponse(m.id,m.receiverId,m.senderId,m.content,m.sendTime,m.messageType,p.name,p.avatarUrl) " +
            " from MessagesEntity m join ProfilesEntity p on m.senderId=p.id where (m.senderId=:senderId and m.receiverId=:receiverId) or (m.senderId=:receiverId and m.receiverId=:senderId)")
    Page<MessageResponse> getListMessageBySenderAndReceiver(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId, Pageable pageable);

    @Query("select new com.huce.doan.mxh.model.dto.MessageResponse(m.id,m.receiverId,m.senderId,m.content,m.sendTime,m.messageType,p.name,p.avatarUrl) " +
            " from MessagesEntity m join ProfilesEntity p on m.senderId=p.id where m.receiverId=:receiverId")
    Page<MessageResponse> getListMessageByReceiver(@Param("receiverId") Long receiverId, Pageable pageable);

}
