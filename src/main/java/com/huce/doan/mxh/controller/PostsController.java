package com.huce.doan.mxh.controller;

import com.huce.doan.mxh.model.dto.PostsDto;
import com.huce.doan.mxh.service.PostsService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/posts")
public class PostsController {
    private final PostsService postsService;

    @GetMapping("/{idMe}/{id}")
    public ResponseEntity<?> getPost(
            @PathVariable Long id,
            @PathVariable Long idMe
    ) {
        return new ResponseEntity<>(postsService.getPost(id,idMe), HttpStatus.OK);
    }

    @GetMapping("/author-id/{id}")
    public ResponseEntity<?> getByAuthorId(
            @PathVariable Long id,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "page-size") int pageSize
    ) {
        return new ResponseEntity<>(postsService.getMyPosts(id, page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/news-feed/{id}")
    public ResponseEntity<?> getNewsFeed(
            @PathVariable Long id,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "page-size") int pageSize
    ) {
        return new ResponseEntity<>(postsService.getNewsFeed(id, page, pageSize), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createPost(
            @ModelAttribute PostsDto post,
            @RequestParam(name = "picture", required = false) MultipartFile picture
    ) {
        return new ResponseEntity<>(postsService.createPost(post, picture), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long id,
            @ModelAttribute PostsDto post,
            @RequestParam(name = "picture", required = false) MultipartFile picture
    ) {
        return new ResponseEntity<>(postsService.updatePost(post, picture, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(postsService.deletePost(id), HttpStatus.OK);
    }
}
