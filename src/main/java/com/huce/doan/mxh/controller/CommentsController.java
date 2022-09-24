package com.huce.doan.mxh.controller;

import com.huce.doan.mxh.model.dto.CommentsDto;
import com.huce.doan.mxh.service.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/v1.0/comments")
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
