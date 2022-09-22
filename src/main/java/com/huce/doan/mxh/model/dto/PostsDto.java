package com.huce.doan.mxh.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huce.doan.mxh.constains.PostStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostsDto {
    private Long id;

    @JsonProperty("author_id")
    private Long authorId;

    private String content;

    @JsonProperty("picture_url")
    private String pictureUrl;

    @JsonProperty("count_likes")
    private Integer countLikes;

    @JsonProperty("post_status")
    private PostStatusEnum postStatus;

    @JsonProperty("posting_time")
    private LocalDateTime postingTime;
}
