package com.codingshuttle.SecurityApp.SecurityApplication.controllers;

import com.codingshuttle.SecurityApp.SecurityApplication.dto.LoginDto;
import com.codingshuttle.SecurityApp.SecurityApplication.dto.SignUpDto;
import com.codingshuttle.SecurityApp.SecurityApplication.dto.UserResponseDto;
import com.codingshuttle.SecurityApp.SecurityApplication.services.AuthService;
import com.codingshuttle.SecurityApp.SecurityApplication.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody SignUpDto signUpDto)
    {
        UserResponseDto userResponseDto = userService.signup(signUpDto);
        return ResponseEntity.ok(userResponseDto);
    }

        @PostMapping("/login")
        public ResponseEntity<String> login(@RequestBody LoginDto loginDto , HttpServletRequest request ,
                                            HttpServletResponse response)
        {
                String token = authService.login(loginDto);

            Cookie cookie = new Cookie("token", token);

            cookie.setHttpOnly(true);

            response.addCookie(cookie);     // response will contain cookies 

                return ResponseEntity.ok(token);
        }
}

