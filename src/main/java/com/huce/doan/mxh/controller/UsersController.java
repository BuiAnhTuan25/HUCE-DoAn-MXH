package com.huce.doan.mxh.controller;

import com.huce.doan.mxh.model.dto.UserRegister;
import com.huce.doan.mxh.model.dto.UsersDto;
import com.huce.doan.mxh.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/users")
public class UsersController {
    private final UsersService usersService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(usersService.getUser(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createUser(
            @RequestBody UserRegister user
    ) throws MessagingException {
        return new ResponseEntity<>(usersService.createUser(user, new StringBuffer("http://localhost:4200/register-verify/")), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @RequestBody UsersDto user,
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(usersService.updateUser(user, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(usersService.deleteUser(id), HttpStatus.OK);
    }
}
