package com.huce.doan.mxh.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.huce.doan.mxh.constains.PrivacyEnum;
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
public class PostsDto {
    private Long id;

    @JsonProperty("author_id")
    private Long authorId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    private String content;

    @JsonProperty("picture_url")
    private String pictureUrl;

    @JsonProperty("count_likes")
    private Integer countLikes;

    @JsonProperty("privacy")
    private PrivacyEnum privacy;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonProperty("posting_time")
    private LocalDateTime postingTime;

    @JsonProperty("is_like")
    private Boolean isLike;

    public PostsDto(Long id, Long authorId, String name, String avatarUrl, String content, String pictureUrl, Integer countLikes, PrivacyEnum privacy, LocalDateTime postingTime, Long likeId) {
        this.id=id;
        this.authorId=authorId;
        this.name=name;
        this.avatarUrl=avatarUrl;
        this.content=content;
        this.pictureUrl=pictureUrl;
        this.countLikes=countLikes;
        this.privacy=privacy;
        this.postingTime=postingTime;
        this.isLike= likeId != null;
    }
}
