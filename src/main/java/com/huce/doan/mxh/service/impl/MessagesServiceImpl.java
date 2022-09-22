package com.huce.doan.mxh.service.impl;

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

@Service
@AllArgsConstructor
public class MessagesServiceImpl implements MessagesService {
    private final MessagesRepository messagesRepository;
    private final ModelMapper mapper;
    private final Response response;

    @Override
    public ListData getBySenderIdAndReceiverId(Long senderId,Long receiverId, int page, int pageSize){
        Page<MessagesEntity> messages = messagesRepository.findBySenderIdAndReceiverId(senderId, receiverId, PageRequest.of(page, pageSize));

        return response.responseListData(messages.getContent(), new Pagination(messages.getNumber(), messages.getSize(), messages.getTotalPages(),
                (int) messages.getTotalElements()));
    }

    @Override
    public ListData getByReceiverId(Long receiverId, int page, int pageSize){
        Page<MessagesEntity> messages = messagesRepository.findByReceiverId(receiverId, PageRequest.of(page, pageSize));

        return response.responseListData(messages.getContent(), new Pagination(messages.getNumber(), messages.getSize(), messages.getTotalPages(),
                (int) messages.getTotalElements()));
    }

    @Override
    public Data createMessage(MessagesDto message){
        MessagesEntity messagesEntity = new MessagesEntity().mapperMessagesDto(message);
        messagesEntity.setSendTime(LocalDateTime.now());

        return response.responseData(mapper.map(messagesRepository.save(messagesEntity),MessagesDto.class));
    }

    @Override
    public Data deleteMessage(Long id){
        MessagesEntity messagesEntity = messagesRepository.findById(id).orElseThrow(EntityExistsException::new);
        messagesRepository.deleteById(id);

        return response.responseData(mapper.map(messagesEntity,MessagesDto.class));
    }
}
