package com.codingshuttle.SecurityApp.SecurityApplication.services;

import com.codingshuttle.SecurityApp.SecurityApplication.dto.LoginDto;
import com.codingshuttle.SecurityApp.SecurityApplication.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService
{
    private final AuthenticationManager authenticationManager;  // should not be used in class where UserDetails is implemented
    private final JwtService jwtService;

    public String login(LoginDto loginDto)
    {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
                );

        User user = (User) authentication.getPrincipal();

        return jwtService.generateToken(user);
    }

}

// UserDetailsService should not use AuthenticationManager because both have different responsibilities in Spring Security.
// UserDetailsService is only used to load user information from the database,
// while AuthenticationManager is responsible for verifying user credentials and performing authentication.
// During authentication, AuthenticationManager already calls UserDetailsService internally.
// So using AuthenticationManager again inside UserDetailsService can create circular dependency and unnecessary recursion.
// Keeping both components separate follows clean architecture
// and makes the authentication flow easier to manage and understand.
