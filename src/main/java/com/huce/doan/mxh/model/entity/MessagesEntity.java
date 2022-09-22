package com.huce.doan.mxh.model.entity;

import com.huce.doan.mxh.constains.MessageTypeEnum;
import com.huce.doan.mxh.model.dto.MessagesDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessagesEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "content")
    private String content;

    @Column(name = "send_time")
    private LocalDateTime sendTime;

    @Column(name = "message_type")
    private MessageTypeEnum messageType;

    public MessagesEntity mapperMessagesDto(MessagesDto message){
        this.id = message.getId();
        this.senderId = message.getSenderId();
        this.receiverId = message.getReceiverId();
        this.content = message.getContent();
        this.sendTime = message.getSendTime();
        this.messageType = message.getMessageType();

        return this;
    }
}
