package com.huce.doan.mxh.constains;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusEnum {
    INACTIVE(0),
    ACTIVE(1);

    private final int value;
}
