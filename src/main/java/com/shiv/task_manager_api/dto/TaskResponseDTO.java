package com.shiv.task_manager_api.dto;

import lombok.Data;

@Data
public class TaskResponseDTO {
    
    private Long id;
    private String title;
    private String description;
    private String status;
}
