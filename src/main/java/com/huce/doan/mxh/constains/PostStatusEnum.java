package com.huce.doan.mxh.constains;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PostStatusEnum {
    PUBLIC(0),
    FRIEND(1),
    PRIVATE(2);

    private final int type;
}
