package com.shiv.task_manager_api.controller;
import java.util.List;

import com.shiv.task_manager_api.dto.TaskRequestDTO;
import com.shiv.task_manager_api.dto.TaskResponseDTO;
import com.shiv.task_manager_api.entity.Task;
import com.shiv.task_manager_api.repository.TaskRepository;
import com.shiv.task_manager_api.service.TaskService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    @Autowired
    
    private TaskService taskService;
    

    @PostMapping
    public ResponseEntity<TaskResponseDTO>createTask(@Valid @RequestBody TaskRequestDTO request){
        
              
        return ResponseEntity
                  .status(HttpStatus.CREATED)
                  .body(taskService.createTask(request));

    }

    @GetMapping
    public Page<TaskResponseDTO> getAllTasks(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue="asc") String direction
    )    {
       return taskService.getAllTasks(page,size,sortBy,direction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id){
        
        return ResponseEntity.ok( taskService.getTaskById(id));
    }
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return "Task Deleted";
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id,
                          @Valid @RequestBody TaskRequestDTO request){

      return ResponseEntity.ok(taskService.updateTask(id,request));
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByStatus(
        @PathVariable String status) {
         return ResponseEntity.ok(
            taskService.getTasksByStatus(status)
         );
        }
    @GetMapping("/search")
    public ResponseEntity<List<TaskResponseDTO>> searchByKeyWord(@RequestParam String keyword){
        return ResponseEntity.ok(
            taskService.searchTasks(keyword)
        );
    }    
    
    }
    

