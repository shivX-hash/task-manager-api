package com.shiv.task_manager_api.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> handleTaskNotFound(
        TaskNotFoundException ex){

            Map<String,String> error = new HashMap<>();

            error.put("message",ex.getMessage());

            return error;
        }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleValidationException(
          MethodArgumentNotValidException ex){
            Map<String,String> errors = new HashMap<>();
            
            ex.getBindingResult()
                   .getFieldErrors()
                   .forEach(error ->
                    errors.put(
                        error.getField(),
                        error.getDefaultMessage())
                    
                   );
                   return errors;
            
          }
}
