package com.huce.doan.mxh.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("api/v1")
public class UploadController {
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestBody MultipartFile file){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
