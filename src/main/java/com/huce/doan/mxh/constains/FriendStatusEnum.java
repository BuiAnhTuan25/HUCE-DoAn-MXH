package com.huce.doan.mxh.constains;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FriendStatusEnum {
    FRIENDED(0),
    WAITING(1),
    CONFIRM(2);
    private final int type;
}
