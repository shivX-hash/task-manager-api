package com.shiv.task_manager_api.exception;

public class TaskNotFoundException extends RuntimeException {
    
    public TaskNotFoundException(String message){
        super(message);
    }
}
