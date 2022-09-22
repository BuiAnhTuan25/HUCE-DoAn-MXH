package com.huce.doan.mxh.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huce.doan.mxh.constains.MessageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessagesDto {
    private Long id;

    @JsonProperty("receiver_id")
    private Long receiverId;

    @JsonProperty("sender_id")
    private Long senderId;

    private String content;

    @JsonProperty("send_time")
    private LocalDateTime sendTime;

    @JsonProperty("message_type")
    private MessageTypeEnum messageType;
}
