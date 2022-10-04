package com.huce.doan.mxh.controller;

import com.huce.doan.mxh.model.dto.FriendsDto;
import com.huce.doan.mxh.service.FriendsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/v1.0/friends")
public class FriendsController {
    private final FriendsService friendsService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getFriend(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(friendsService.getFriend(id), HttpStatus.OK);
    }

    @GetMapping("/{meId}/{friendId}")
    public ResponseEntity<?> findByMeIdAndFriendId(
            @PathVariable Long meId,
            @PathVariable Long friendId
    ) {
        return new ResponseEntity<>(friendsService.findByMeIdAndFriendId(meId, friendId), HttpStatus.OK);
    }

    @GetMapping("/me-id/{id}")
    public ResponseEntity<?> getByMeId(
            @PathVariable Long id,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "page-size") int pageSize
    ) {
        return new ResponseEntity<>(friendsService.getListFriendByMeId(id, page, pageSize), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createFriend(
            @RequestBody FriendsDto friend
    ) {
        return new ResponseEntity<>(friendsService.createFriend(friend), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFriend(
            @PathVariable Long id,
            @RequestBody FriendsDto friend
    ) {
        return new ResponseEntity<>(friendsService.updateFriend(friend, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFriend(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(friendsService.deleteFriend(id), HttpStatus.OK);
    }
}
