package com.huce.doan.mxh.constains;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageStatusEnum {
    SEEN(0),
    NOT_SEEN(1);

    private final int value;
}
