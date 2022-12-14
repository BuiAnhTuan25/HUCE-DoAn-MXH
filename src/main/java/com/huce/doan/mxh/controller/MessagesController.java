package com.huce.doan.mxh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huce.doan.mxh.constains.MessageStatusEnum;
import com.huce.doan.mxh.model.dto.MessagesDto;
import com.huce.doan.mxh.service.MessagesService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/messages")
public class MessagesController {
    private final MessagesService messagesService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/history/{senderId}/{receiverId}")
    public ResponseEntity<?> getBySenderIdAndReceiverId(
            @PathVariable Long senderId,
            @PathVariable Long receiverId,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "page-size") int pageSize
    ){
        return new ResponseEntity<>(messagesService.getBySenderIdAndReceiverId(senderId, receiverId, page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/history/{receiverId}")
    public ResponseEntity<?> getByReceiverId(
            @PathVariable Long receiverId,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "page-size") int pageSize
    ) {
        return new ResponseEntity<>(messagesService.getByReceiverId(receiverId, page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/not-seen/{receiverId}")
    public ResponseEntity<?> getListMessageNotSeen(
            @PathVariable Long receiverId
    ) {
        return new ResponseEntity<>(messagesService.getListMessageNotSeen(receiverId), HttpStatus.OK);
    }

    @GetMapping("/notification/{receiverId}")
    public ResponseEntity<?> getListNotificationNotSeen(
            @PathVariable Long receiverId
    ) {
        return new ResponseEntity<>(messagesService.getListNotificationNotSeen(receiverId), HttpStatus.OK);
    }

    @GetMapping("/friend-chat/{id}")
    public ResponseEntity<?> getListFriendChat(
            @PathVariable Long id,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "page-size") int pageSize
    ) {
        return new ResponseEntity<>(messagesService.getListFriendChat(id, page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> findFriendChat(
            @PathVariable("id") Long id,
            @RequestParam("full-text-search") String fullTextSearch,
            @RequestParam("page") int page,
            @RequestParam("page-size") int pageSize
    ){
        return new ResponseEntity<>(messagesService.findFriendChat(id,fullTextSearch,page,pageSize),HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createMessage(
            @RequestBody MessagesDto message
            ){
        return new ResponseEntity<>(messagesService.createMessage(message),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(
            @PathVariable Long id
    ){
        return new ResponseEntity<>(messagesService.deleteMessage(id),HttpStatus.OK);
    }

    @MessageMapping("/chat/{friendId}")
    public void sendMessage(
            @DestinationVariable Long friendId,
            String message
    )throws JsonProcessingException {
        MessagesDto messagesDto = new ObjectMapper().readValue(message, MessagesDto.class);

        MessagesDto msgSave = (MessagesDto) messagesService.createMessage(messagesDto).getData();
        simpMessagingTemplate.convertAndSend("/topic/receiver/"+messagesDto.getSenderId(),msgSave);
        simpMessagingTemplate.convertAndSend("/topic/receiver/"+messagesDto.getReceiverId(),msgSave);
    }

    @PutMapping("/list")
    public ResponseEntity<?> updateMessageStatus(
            @RequestBody List<Long> listId
    ){
        return new ResponseEntity<>(messagesService.updateMessageStatus(listId),HttpStatus.OK);

    }
}
