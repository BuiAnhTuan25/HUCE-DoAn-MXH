package com.huce.doan.mxh.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.huce.doan.mxh.constains.ProviderEnum;
import com.huce.doan.mxh.model.dto.LoginResponse;
import com.huce.doan.mxh.model.dto.TokenDto;
import com.huce.doan.mxh.model.dto.UsersDto;
import com.huce.doan.mxh.model.entity.UsersEntity;
import com.huce.doan.mxh.model.response.Response;
import com.huce.doan.mxh.security.CustomUserDetails;
import com.huce.doan.mxh.security.jwt.JwtTokenProvider;
import com.huce.doan.mxh.service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;

@CrossOrigin
@RestController
@RequestMapping("api/v1.0/auth-social")
public class LoginSocialController {
    @Value("${spring.security.oauth2.client.registration.google.clientId}")
    String googleClientId;

    private final UsersService userService;

    private final JwtTokenProvider tokenProvider;

    private final ModelMapper mapper;

    private final Response response;

    public LoginSocialController(UsersService userService, JwtTokenProvider tokenProvider, ModelMapper mapper, Response response) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.mapper = mapper;
        this.response = response;
    }

    @PostMapping("/google")
    public ResponseEntity<?> google(@RequestBody TokenDto tokenDto) throws IOException {
        final NetHttpTransport transport = new NetHttpTransport();
        final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder verifier =
                new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                        .setAudience(Collections.singletonList(googleClientId));
        final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), tokenDto.getValue());
        final GoogleIdToken.Payload payload = googleIdToken.getPayload();
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setEmail(payload.getEmail());
        usersEntity.setUsername(payload.getEmail());
        usersEntity = userService.processOAuthPostLogin(usersEntity, ProviderEnum.GOOGLE);
        TokenDto tokenRes = addToken(usersEntity);
        return new ResponseEntity<>(response.responseData("Login with google successfully",new LoginResponse("Bearer " + tokenRes.getValue(), mapper.map(usersEntity, UsersDto.class))), HttpStatus.OK);
    }

    @PostMapping("/facebook")
    public ResponseEntity<?> facebook(@RequestBody TokenDto tokenDto) {
        Facebook facebook = new FacebookTemplate(tokenDto.getValue());
        final String [] fields = {"email","name"};
        User user = facebook.fetchObject("me", User.class, fields);
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setEmail(user.getEmail());
        usersEntity.setUsername(user.getName());
        usersEntity=userService.processOAuthPostLogin(usersEntity, ProviderEnum.FACEBOOK);
        TokenDto tokenRes = addToken(usersEntity);
        return new ResponseEntity<>(response.responseData("Login with facebook successfully",new LoginResponse("Bearer " + tokenRes.getValue(), mapper.map(usersEntity, UsersDto.class))),HttpStatus.OK);

    }

    public TokenDto addToken(UsersEntity user){
        CustomUserDetails userDetails=new CustomUserDetails(user);
        String jwt = tokenProvider.generateToken(userDetails);
        return new TokenDto(jwt);
    }
}
