package com.huce.doan.mxh.controller;

import com.huce.doan.mxh.model.dto.LoginResponse;
import com.huce.doan.mxh.model.dto.UpdatePasswordDto;
import com.huce.doan.mxh.model.dto.UserRegister;
import com.huce.doan.mxh.model.dto.UsersDto;
import com.huce.doan.mxh.model.response.Response;
import com.huce.doan.mxh.security.CustomUserDetails;
import com.huce.doan.mxh.security.jwt.JwtTokenProvider;
import com.huce.doan.mxh.service.UsersService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/auth")
public class LoginController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;

    private final UsersService userService;

    private final ModelMapper mapper;

    private final Response response;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UsersDto user) {
        // Xác thực thông tin người dùng Request lên
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Trả về jwt cho người dùng.
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String jwt = tokenProvider.generateToken(userDetails);
            return ResponseEntity.ok(response.responseData("Login successfully",new LoginResponse("Bearer " + jwt, mapper.map(userDetails.getUser(), UsersDto.class))));

        }
        catch (Exception ex){
            return new ResponseEntity<>(response.responseError("Incorrect account or password"), HttpStatus.BAD_REQUEST);
        }

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegister user, HttpServletRequest request) throws MessagingException {
        return ResponseEntity.ok(userService.createUser(user, new StringBuffer("http://localhost:4200/register-verify/")));
    }

    @GetMapping("/register/verify")
    public ResponseEntity<?> verifyUser(@RequestParam("code") String code) {
        return ResponseEntity.ok(userService.verify(code));
    }

    @PutMapping("/change-password/{id}")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody UpdatePasswordDto passwordDto) {
        return ResponseEntity.ok(userService.changePassword(id, passwordDto));
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String mail) throws MessagingException {
        return ResponseEntity.ok(userService.forgotPassword(mail));
    }
}
