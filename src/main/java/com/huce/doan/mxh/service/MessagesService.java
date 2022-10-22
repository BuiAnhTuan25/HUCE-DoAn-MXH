package com.huce.doan.mxh.service;

import com.huce.doan.mxh.model.dto.MessagesDto;
import com.huce.doan.mxh.model.response.Data;
import com.huce.doan.mxh.model.response.ListData;

public interface MessagesService {
    ListData getBySenderIdAndReceiverId(Long senderId, Long receiverId, int page, int pageSize);

    ListData getByReceiverId(Long receiverId, int page, int pageSize);

    Data createMessage(MessagesDto post);

    Data deleteMessage(Long id);

    ListData getListFriendChat(Long id, int page, int pageSize);

    ListData findFriendChat(Long id,String fullTextSearch, int page, int pageSize);

}
