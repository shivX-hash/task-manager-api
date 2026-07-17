package com.shiv.task_manager_api.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String message;
    private String token;
}
