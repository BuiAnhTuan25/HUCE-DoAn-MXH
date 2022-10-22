package com.huce.doan.mxh.constains;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageTypeEnum {
    MESSAGE(0),
    NOTIFICATION(1),
    ACTIVE_STATUS(2);

    private final int type;
}
