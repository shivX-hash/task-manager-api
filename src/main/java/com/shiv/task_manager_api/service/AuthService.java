package com.shiv.task_manager_api.service;

import com.shiv.task_manager_api.entity.User;
import com.shiv.task_manager_api.enums.UserRole;
import com.shiv.task_manager_api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.shiv.task_manager_api.dto.RegisterRequest;
import com.shiv.task_manager_api.dto.AuthResponse;
import com.shiv.task_manager_api.dto.LoginRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request){
        if(userRepository.findByUsername(request.getUsername()).isPresent()){
           throw new RuntimeException("Username already exists");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);
        userRepository.save(user);
        AuthResponse response = new AuthResponse();
        response.setMessage("User registered successfully");
        return response;
    }

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
                
            )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        String token = jwtService.generateToken(user);
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        return response;         
    }
} 
