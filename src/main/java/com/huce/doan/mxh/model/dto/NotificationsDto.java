package com.huce.doan.mxh.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.huce.doan.mxh.constains.MessageStatusEnum;
import com.huce.doan.mxh.constains.MessageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationsDto {

    private Long id;

    @JsonProperty("receiver_id")
    private Long receiverId;

    private String content;

    @JsonProperty("post_id")
    private Long postId;

    @JsonProperty("friend_id")
    private Long friendId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonProperty("send_time")
    private LocalDateTime sendTime;

    @JsonProperty("message_type")
    private MessageTypeEnum messageType;

    @JsonProperty("status")
    private MessageStatusEnum status;

}
