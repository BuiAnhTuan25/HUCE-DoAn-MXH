package com.huce.doan.mxh.service.impl;

import com.huce.doan.mxh.constains.MessageStatusEnum;
import com.huce.doan.mxh.model.dto.FriendChatDto;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessagesServiceImpl implements MessagesService {
    private final MessagesRepository messagesRepository;
    private final ModelMapper mapper;
    private final Response response;

    @Override
    public ListData getBySenderIdAndReceiverId(Long senderId, Long receiverId, int page, int pageSize) {
        Page<MessagesDto> messages = messagesRepository.getListMessageBySenderAndReceiver(senderId, receiverId, PageRequest.of(page, pageSize));

        return response.responseListData(messages.getContent(), new Pagination(messages.getNumber(), messages.getSize(), messages.getTotalPages(),
                (int) messages.getTotalElements()));
    }

    @Override
    public ListData getListFriendChat(Long id, int page, int pageSize){
        Page<FriendChatDto> friendChatDto = messagesRepository.getListFriendChat(id, PageRequest.of(page, pageSize));

        return response.responseListData(friendChatDto.getContent(), new Pagination(friendChatDto.getNumber(), friendChatDto.getSize(), friendChatDto.getTotalPages(),
                (int) friendChatDto.getTotalElements()));
    }

    @Override
    public Data getListMessageNotSeen(Long receiverId){
        List<MessagesDto> friendChatDto = messagesRepository.getListMessageNotSeen(receiverId);

        return response.responseData("Get list message not seen successfully",friendChatDto);
    }

    @Override
    public Data getListNotificationNotSeen(Long receiverId){
        List<MessagesDto> friendChatDto = messagesRepository.getListNotificationNotSeen(receiverId);

        return response.responseData("Get list notification not seen successfully",friendChatDto);
    }

    @Override
    public ListData findFriendChat(Long id, String fullTextSearch, int page, int pageSize){
        Page<FriendChatDto> friendChatDto = messagesRepository.findFriendChat(id, fullTextSearch, PageRequest.of(page, pageSize));

        return response.responseListData(friendChatDto.getContent(), new Pagination(friendChatDto.getNumber(), friendChatDto.getSize(), friendChatDto.getTotalPages(),
                (int) friendChatDto.getTotalElements()));
    }

    @Override
    public ListData getByReceiverId(Long receiverId, int page, int pageSize) {
        Page<MessagesDto> messages = messagesRepository.getListMessageByReceiver(receiverId, PageRequest.of(page, pageSize));

        return response.responseListData(messages.getContent(), new Pagination(messages.getNumber(), messages.getSize(), messages.getTotalPages(),
                (int) messages.getTotalElements()));
    }

    @Override
    public Data createMessage(MessagesDto message) {
        MessagesEntity messagesEntity = new MessagesEntity().mapperMessagesDto(message);
        messagesEntity.setSendTime(LocalDateTime.now());
        messagesEntity.setMessageStatus(MessageStatusEnum.NOT_SEEN);
        messagesEntity = messagesRepository.save(messagesEntity);

        message.setId(messagesEntity.getId());
        message.setSendTime(messagesEntity.getSendTime());

        return response.responseData("Create message successfully", message);
    }

    @Override
    public Data deleteMessage(Long id) {
        Optional<MessagesEntity> messagesEntity = messagesRepository.findById(id);

        return messagesEntity.map(data -> {
            messagesRepository.deleteById(id);
            return response.responseData("Delete message successfully", mapper.map(data, MessagesDto.class));
        }).orElseGet(() -> response.responseError("Entity not found"));
    }

    @Override
    public Data updateMessageStatus(List<Long> listId){
        messagesRepository.updateMessageStatus(listId);
        return response.responseData("Update message status successfully",null);
    }
}
