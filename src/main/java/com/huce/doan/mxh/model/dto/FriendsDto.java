package com.huce.doan.mxh.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huce.doan.mxh.constains.FriendStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FriendsDto {
    private Long id;

    @JsonProperty("friend_id")
    private Long friendId;

    @JsonProperty("me_id")
    private Long meId;

    @JsonProperty("friend_status")
    private FriendStatusEnum friendStatus;

    private String name;

    @JsonProperty("avatar_url")
    private String avatarUrl;
}
