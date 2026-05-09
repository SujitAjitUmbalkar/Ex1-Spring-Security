package com.codingshuttle.SecurityApp.SecurityApplication.services;

import com.codingshuttle.SecurityApp.SecurityApplication.dto.LoginDto;
import com.codingshuttle.SecurityApp.SecurityApplication.dto.SignUpDto;
import com.codingshuttle.SecurityApp.SecurityApplication.dto.UserResponseDto;
import com.codingshuttle.SecurityApp.SecurityApplication.entities.User;
import com.codingshuttle.SecurityApp.SecurityApplication.exceptions.ResourceNotFoundException;
import com.codingshuttle.SecurityApp.SecurityApplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService  implements UserDetailsService
{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with email: " + username));
    }

    public UserResponseDto signup(SignUpDto signUpDto)
    {
        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
        if (user.isPresent())
        {
            throw new BadCredentialsException("User already exists with emai l: " + signUpDto.getEmail());
        }
        else
        {
            User toBeCreatedUser =  modelMapper.map(signUpDto, User.class);     // user dto -> user Entity
            toBeCreatedUser.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
            User savedUser =   userRepository.save(toBeCreatedUser);
            return  modelMapper.map(savedUser, UserResponseDto.class);  // user Entity -> user Dto (which is going to return to user)
        }
    }
}
