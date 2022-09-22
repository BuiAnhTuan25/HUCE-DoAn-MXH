package com.huce.doan.mxh.controller;

import com.huce.doan.mxh.model.dto.LikesDto;
import com.huce.doan.mxh.service.LikesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/v1.0/likes")
public class LikesController {
    private final LikesService likesService;

    @GetMapping("/post-id/{id}")
    public ResponseEntity<?> getByPostId(
            @PathVariable Long id,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "page-size") int pageSize
    ) {
        return new ResponseEntity<>(likesService.getByPostId(id, page, pageSize), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createLike(
            @RequestBody LikesDto like
    ){
        return new ResponseEntity<>(likesService.createLike(like),HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}/{userId}")
    public ResponseEntity<?> deleteLike(
            @PathVariable Long postId,
            @PathVariable Long userId
    ){
        return new ResponseEntity<>(likesService.deleteLike(postId, userId),HttpStatus.OK);
    }
}
