package com.shiv.task_manager_api.entity;

import com.shiv.task_manager_api.enums.TaskStatus;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import com.shiv.task_manager_api.entity.User;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)

    private Long id;

    @NotBlank(message ="Title cannot be empty")
    @Size(min=3 , max=500)
    private String title;

    @NotBlank(message = "Description cannot be empty")
    @Size(min=5,max=500)
    private String description;

    @NotBlank(message ="Status should not be empty")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
   
}
