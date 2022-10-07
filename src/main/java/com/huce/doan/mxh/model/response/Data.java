package com.huce.doan.mxh.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Data {
    private boolean success;
    private String message;
    private Integer code;
    private Object data;
}
