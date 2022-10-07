package com.huce.doan.mxh.model.response;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Response {
    public Data responseData(String message,Object data){
        return new Data(true,message,200,data);
    }

    public ListData responseListData(List<?> listData,Pagination pagination){
        return new ListData(true,"success",200,listData,pagination);
    }

    public Data responseError(String message){
        return new Data(false,message,400,null);
    }
}
