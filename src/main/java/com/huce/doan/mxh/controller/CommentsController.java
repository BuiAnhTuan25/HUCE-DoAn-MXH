package com.huce.doan.mxh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huce.doan.mxh.model.dto.CommentsDto;
import com.huce.doan.mxh.model.dto.MessagesDto;
import com.huce.doan.mxh.service.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/comments")
public class CommentsController {
    private final CommentsService commentsService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(commentsService.getComment(id), HttpStatus.OK);
    }

    @GetMapping("/post-id/{id}")
    public ResponseEntity<?> getByPostId(
            @PathVariable Long id,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "page-size") int pageSize
    ) {
        return new ResponseEntity<>(commentsService.getByPostId(id, page, pageSize), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createComment(
            @ModelAttribute CommentsDto comment,
            @RequestParam(name = "picture", required = false) MultipartFile picture
    ) {
        return new ResponseEntity<>(commentsService.createComment(comment, picture), HttpStatus.CREATED);
    }

//    @MessageMapping("/comment/{id}")
//    public void sendMessage(
//            @DestinationVariable Long id,
//            String comment
//    )throws JsonProcessingException {
//        CommentsDto commentsDto = new ObjectMapper().readValue(comment, CommentsDto.class);
//
//        CommentsDto commentSave = (CommentsDto) commentsService.createComment(commentsDto).getData();
//        simpMessagingTemplate.convertAndSend("/topic/receiver/"+messagesDto.getSenderId(),msgSave);
//        simpMessagingTemplate.convertAndSend("/topic/receiver/"+messagesDto.getReceiverId(),msgSave);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(
            @PathVariable Long id,
            @ModelAttribute CommentsDto comment,
            @RequestParam(name = "picture", required = false) MultipartFile picture
    ) {
        return new ResponseEntity<>(commentsService.updateComment(comment, picture, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(commentsService.deleteComment(id), HttpStatus.OK);
    }
}
