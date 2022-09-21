package com.huce.doan.mxh.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentsDto {
    private Long id;

    @JsonProperty("post_id")
    private Long postId;

    @JsonProperty("author_id")
    private Long authorId;

    private String content;

    @JsonProperty("picture_url")
    private String pictureUrl;

    @JsonProperty("comment_time")
    private LocalDate commentTime;
}
