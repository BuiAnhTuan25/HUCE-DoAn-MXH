package com.huce.doan.mxh.service;

import com.huce.doan.mxh.model.dto.MessagesDto;
import com.huce.doan.mxh.model.dto.NotificationsDto;
import com.huce.doan.mxh.model.response.Data;
import com.huce.doan.mxh.model.response.ListData;

import java.util.List;

public interface NotificationsService {
    ListData getByReceiverId(Long receiverId, int page, int pageSize);

    Data getListNotificationNotSeen(Long receiverId);

    Data createNotification(NotificationsDto notification);

    Data deleteNotification(Long id);

    Data updateNotificationStatus(List<Long> listId);

    Data updateNotificationStatus(Long id, NotificationsDto notification);
}
