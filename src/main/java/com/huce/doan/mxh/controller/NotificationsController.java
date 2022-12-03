package com.huce.doan.mxh.controller;

import com.huce.doan.mxh.model.dto.MessagesDto;
import com.huce.doan.mxh.model.dto.NotificationsDto;
import com.huce.doan.mxh.service.NotificationsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/notifications")
public class NotificationsController {
    private final NotificationsService notificationsService;

    @GetMapping("/not-seen/{id}")
    public ResponseEntity<?> getListNotificationNotSeen(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(notificationsService.getListNotificationNotSeen(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getListNotification(
            @RequestParam("id") Long id,
            @RequestParam("page") int page,
            @RequestParam("page-size") int pageSize
    ) {
        return new ResponseEntity<>(notificationsService.getByReceiverId(id, page, pageSize), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createNotification(
            @RequestBody NotificationsDto notification
    ){
        return new ResponseEntity<>(notificationsService.createNotification(notification), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(
            @PathVariable Long id
    ){
        return new ResponseEntity<>(notificationsService.deleteNotification(id),HttpStatus.OK);
    }

    @PutMapping("/{id)")
    public ResponseEntity<?> updateNotification(
            @RequestBody NotificationsDto notification,
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(notificationsService.updateNotificationStatus(id, notification), HttpStatus.OK);
    }

    @PutMapping("/list")
    public ResponseEntity<?> updateNotificationStatus(
            @RequestBody List<Long> listId
    ){
        return new ResponseEntity<>(notificationsService.updateNotificationStatus(listId),HttpStatus.OK);

    }
}
