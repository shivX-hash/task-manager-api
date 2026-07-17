package com.shiv.task_manager_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import com.shiv.task_manager_api.service.AuthService;
import com.shiv.task_manager_api.dto.AuthResponse;
import com.shiv.task_manager_api.dto.RegisterRequest;
import com.shiv.task_manager_api.dto.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
        @Valid @RequestBody RegisterRequest request){
          
            return ResponseEntity.ok(
                authService.register(request)
            );
        }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
        @RequestBody LoginRequest request){
         
            return ResponseEntity.ok(authService.login(request));
        }
       
}
