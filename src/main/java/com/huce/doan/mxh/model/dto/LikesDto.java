package com.huce.doan.mxh.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LikesDto {
    private Long id;

    @JsonProperty("post_id")
    private Long postId;

    @JsonProperty("user_id")
    private Long userId;
}
