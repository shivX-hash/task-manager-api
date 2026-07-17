package com.shiv.task_manager_api.dto;

import com.shiv.task_manager_api.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskRequestDTO {
    
    @NotBlank(message = "Title cannot be empty")
    @Size(min = 3,max=100)
    private String title;

    @NotBlank(message="Description cannot be empty")
    @Size(min = 5,max=500)
    private String description;

    @NotNull(message = "Status cannot be empty")
    private TaskStatus status;
}
