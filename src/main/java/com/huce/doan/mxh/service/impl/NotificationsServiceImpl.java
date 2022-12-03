package com.huce.doan.mxh.service.impl;

import com.huce.doan.mxh.constains.MessageStatusEnum;
import com.huce.doan.mxh.model.dto.MessagesDto;
import com.huce.doan.mxh.model.dto.NotificationsDto;
import com.huce.doan.mxh.model.entity.NotificationsEntity;
import com.huce.doan.mxh.model.response.Data;
import com.huce.doan.mxh.model.response.ListData;
import com.huce.doan.mxh.model.response.Pagination;
import com.huce.doan.mxh.model.response.Response;
import com.huce.doan.mxh.repository.NotificationsRepository;
import com.huce.doan.mxh.service.NotificationsService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class NotificationsServiceImpl implements NotificationsService {
    private final NotificationsRepository notificationsRepository;
    private final ModelMapper mapper;
    private final Response response;

    @Override
    public Data getListNotificationNotSeen(Long receiverId){
        List<NotificationsEntity> notifications = notificationsRepository.getListNotSeen(receiverId);

        return response.responseData("Get list notification not seen successfully",notifications);
    }

    @Override
    public ListData getByReceiverId(Long receiverId, int page, int pageSize) {
        Page<NotificationsEntity> notifications = notificationsRepository.getList(receiverId, PageRequest.of(page, pageSize));

        return response.responseListData(notifications.getContent(), new Pagination(notifications.getNumber(), notifications.getSize(), notifications.getTotalPages(),
                (int) notifications.getTotalElements()));
    }

    @Override
    public Data createNotification(NotificationsDto notification) {
        NotificationsEntity notificationEntity = new NotificationsEntity().mapperNotificationsDto(notification);
        notificationEntity.setSendTime(LocalDateTime.now());
        notificationEntity.setStatus(MessageStatusEnum.NOT_SEEN);

        return response.responseData("Create notification successfully", mapper.map(notificationsRepository.save(notificationEntity), NotificationsDto.class));
    }

    @Override
    public Data deleteNotification(Long id) {
        Optional<NotificationsEntity> notification = notificationsRepository.findById(id);

        return notification.map(data -> {
            notificationsRepository.deleteById(id);
            return response.responseData("Delete notification successfully", mapper.map(data, MessagesDto.class));
        }).orElseGet(() -> response.responseError("Entity not found"));
    }

    @Override
    public Data updateNotificationStatus(List<Long> listId){
        notificationsRepository.updateStatus(listId);
        return response.responseData("Update notification status successfully",null);
    }

    @Override
    public Data updateNotificationStatus(Long id, NotificationsDto notification){
        Optional<NotificationsEntity> notificationEntity = notificationsRepository.findById(id);
        return notificationEntity.map(data -> {
            data.mapperNotificationsDto(notification);
            return response.responseData("Delete notification successfully", mapper.map(notificationsRepository.save(data), MessagesDto.class));
        }).orElseGet(() -> response.responseError("Entity not found"));
    }
}
