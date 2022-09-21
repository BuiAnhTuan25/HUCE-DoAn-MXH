package com.huce.doan.mxh.model.entity;

import com.huce.doan.mxh.constains.MessageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Messages {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "receiver_id", unique = true)
    private Long receiverId;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "content", unique = true)
    private String content;

    @Column(name = "send_time", unique = true)
    private LocalDate sendTime;

    @Column(name = "message_type", unique = true)
    private MessageTypeEnum messageType;
}
