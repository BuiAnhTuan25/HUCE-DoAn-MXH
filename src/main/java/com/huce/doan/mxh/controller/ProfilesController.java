package com.huce.doan.mxh.controller;

import com.huce.doan.mxh.constains.ActiveStatusEnum;
import com.huce.doan.mxh.constains.GenderEnum;
import com.huce.doan.mxh.constains.StatusEnum;
import com.huce.doan.mxh.model.dto.ProfileSearchRequest;
import com.huce.doan.mxh.model.dto.ProfilesDto;
import com.huce.doan.mxh.service.ProfilesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/profiles")
public class ProfilesController {
    private final ProfilesService profilesService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfile(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(profilesService.getProfile(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(value = "id-me", required = false) Long idMe,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phone-number", required = false) String phoneNumber,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "gender", required = false) GenderEnum gender,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam("page") int page,
            @RequestParam("page-size") int pageSize
            ) {
        return new ResponseEntity<>(profilesService.search(new ProfileSearchRequest(idMe, name, phoneNumber, address, gender, sortBy), page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/phone-number")
    public ResponseEntity<?> findProfileByPhoneNumberAndStatus(
            @RequestParam("phone-number") String phoneNumber
            ) {
        return new ResponseEntity<>(profilesService.findByPhoneNumber(phoneNumber), HttpStatus.OK);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> findProfilesFriendsByNameOrPhoneNumber(
            @PathVariable("id") Long id,
            @RequestParam("full-text-search") String fullTextSearch,
            @RequestParam("page") int page,
            @RequestParam("page-size") int pageSize
    ){
        return new ResponseEntity<>(profilesService.findProfilesFriendsByNameOrPhoneNumber(id,fullTextSearch,page,pageSize),HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createProfile(
            @ModelAttribute ProfilesDto profile,
            @RequestParam(name = "avatar", required = false) MultipartFile avatar
    ) {
        return new ResponseEntity<>(profilesService.createProfile(profile, avatar), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(
            @ModelAttribute ProfilesDto profile,
            @RequestParam(name = "avatar", required = false) MultipartFile avatar,
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(profilesService.updateProfile(profile, avatar, id), HttpStatus.OK);
    }

    @PutMapping("/active/{id}")
    public ResponseEntity<?> updateActiveStatus(
            @PathVariable Long id,
            @RequestParam("active-status") ActiveStatusEnum activeStatus
            ){
        return new ResponseEntity<>(profilesService.updateActiveStatus(id,activeStatus),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfile(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(profilesService.deleteProfile(id), HttpStatus.OK);
    }
}
