package com.huce.doan.mxh.constains;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProviderEnum {
    DEFAULT(0),
    GOOGLE(1),
    FACEBOOK(2);

    private final int type;
}
