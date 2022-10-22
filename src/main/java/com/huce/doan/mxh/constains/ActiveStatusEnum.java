package com.huce.doan.mxh.constains;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ActiveStatusEnum {
    OFFLINE(0),
    ONLINE(1);

    private final int value;
}
