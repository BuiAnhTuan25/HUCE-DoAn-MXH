package com.huce.doan.mxh.model.response;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Response {
    public Data responseData(Object data){
        return new Data(true,"success",data);
    }

    public ListData responseListData(List<?> listData,Pagination pagination){
        return new ListData(true,"success",listData,pagination);
    }

    public Error responseError(String message,int code){
        return new Error(false,message,code);
    }
}
