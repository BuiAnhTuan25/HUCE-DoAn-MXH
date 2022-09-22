package com.huce.doan.mxh.controller;

import com.huce.doan.mxh.model.dto.MessagesDto;
import com.huce.doan.mxh.service.MessagesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/v1.0/messages")
public class MessagesController {
    private final MessagesService messagesService;

    @GetMapping("/history/{senderId}/{receiverId}")
    public ResponseEntity<?> getBySenderIdAndReceiverId(
            @PathVariable Long senderId,
            @PathVariable Long receiverId,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "page-size") int pageSize
    ) {
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
}
