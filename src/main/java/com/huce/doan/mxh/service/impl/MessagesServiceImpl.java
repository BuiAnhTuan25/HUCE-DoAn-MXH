package com.huce.doan.mxh.service.impl;

import com.huce.doan.mxh.model.dto.MessageResponse;
import com.huce.doan.mxh.model.dto.MessagesDto;
import com.huce.doan.mxh.model.entity.MessagesEntity;
import com.huce.doan.mxh.model.response.Data;
import com.huce.doan.mxh.model.response.ListData;
import com.huce.doan.mxh.model.response.Pagination;
import com.huce.doan.mxh.model.response.Response;
import com.huce.doan.mxh.repository.MessagesRepository;
import com.huce.doan.mxh.service.MessagesService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessagesServiceImpl implements MessagesService {
    private final MessagesRepository messagesRepository;
    private final ModelMapper mapper;
    private final Response response;

    @Override
    public ListData getBySenderIdAndReceiverId(Long senderId, Long receiverId, int page, int pageSize) {
        Page<MessageResponse> messages = messagesRepository.getListMessageBySenderAndReceiver(senderId, receiverId, PageRequest.of(page, pageSize));

        return response.responseListData(messages.getContent(), new Pagination(messages.getNumber(), messages.getSize(), messages.getTotalPages(),
                (int) messages.getTotalElements()));
    }

    @Override
    public ListData getByReceiverId(Long receiverId, int page, int pageSize) {
        Page<MessageResponse> messages = messagesRepository.getListMessageByReceiver(receiverId, PageRequest.of(page, pageSize));

        return response.responseListData(messages.getContent(), new Pagination(messages.getNumber(), messages.getSize(), messages.getTotalPages(),
                (int) messages.getTotalElements()));
    }

    @Override
    public Data createMessage(MessagesDto message) {
        message.setSendTime(LocalDateTime.now());
        MessagesEntity messagesEntity = new MessagesEntity().mapperMessagesDto(message);
        messagesEntity.setSendTime(LocalDateTime.now());

        return response.responseData("Create message successfully", mapper.map(messagesRepository.save(messagesEntity), MessagesDto.class));
    }

    @Override
    public Data deleteMessage(Long id) {
        Optional<MessagesEntity> messagesEntity = messagesRepository.findById(id);

        return messagesEntity.map(data -> {
            messagesRepository.deleteById(id);
            return response.responseData("Delete message successfully", mapper.map(messagesEntity, MessagesDto.class));
        }).orElseGet(() -> response.responseError("Entity not found"));
    }
}
