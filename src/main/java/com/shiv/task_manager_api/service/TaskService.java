package com.shiv.task_manager_api.service;

import com.shiv.task_manager_api.entity.User;
import com.shiv.task_manager_api.enums.UserRole;
import com.shiv.task_manager_api.entity.Task;
import com.shiv.task_manager_api.dto.TaskRequestDTO;
import com.shiv.task_manager_api.dto.TaskResponseDTO;
import org.modelmapper.ModelMapper;
import com.shiv.task_manager_api.exception.TaskNotFoundException;
import com.shiv.task_manager_api.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import com.shiv.task_manager_api.repository.UserRepository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private UserRepository userRepository;

    public TaskResponseDTO createTask(TaskRequestDTO request){
       
        User user = getCurrentUser();
        Task task = modelMapper.map(request,Task.class);
        task.setUser(user);
        Task savedTask = taskRepository.save(task);
        return modelMapper.map(savedTask,TaskResponseDTO.class);
    }

    public Page<TaskResponseDTO> getAllTasks(int page,int size,String sortBy,String direction){

        Sort sort= direction.equalsIgnoreCase("desc")
        ? Sort.by(sortBy).descending()
        : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page,size,sort);
        User user = getCurrentUser();
        Page<Task> taskPage;
        if (user.getRole() == UserRole.ADMIN) {
           taskPage = taskRepository.findAll(pageable);
            }   
      else {
             taskPage = taskRepository.findByUser(user, pageable);
}
        return taskPage.map(task -> modelMapper.map(page,TaskResponseDTO.class));
    }

    public TaskResponseDTO getTaskById(Long id){
       User currentUser = getCurrentUser();

       Task task;

        if (currentUser.getRole() == UserRole.ADMIN) {
            task = taskRepository.findById(id)
                    .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        } else {
            task = taskRepository.findByIdAndUser(id, currentUser)
                    .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        }
        return modelMapper.map(task,TaskResponseDTO.class);            
    }

    public void deleteTask(Long id){
        User currentUser = getCurrentUser();

        Task task;

            if (currentUser.getRole() == UserRole.ADMIN) {
                task = taskRepository.findById(id)
                        .orElseThrow(() -> new TaskNotFoundException("Task not found"));
            } else {
                task = taskRepository.findByIdAndUser(id, currentUser)
                        .orElseThrow(() -> new TaskNotFoundException("Task not found"));
            }
            
            taskRepository.delete(task);
    }

    public TaskResponseDTO updateTask(Long id , TaskRequestDTO request){
        Task task = taskRepository.findByIdAndUser(id,getCurrentUser()).orElseThrow(() ->
                     new TaskNotFoundException(
                        "Task not found with id " + id
                     ));
        modelMapper.map(request,task); 

       Task updatedTask = taskRepository.save(task);
        return modelMapper.map(updatedTask,TaskResponseDTO.class);
    }

    public List<TaskResponseDTO> getTasksByStatus(String status){
        List<Task> tasks =taskRepository.findByStatus(status);
        
        return tasks.stream()
                    .map(task -> modelMapper.map(task,TaskResponseDTO.class))
                    .toList();
    }

    public List<TaskResponseDTO> searchTasks(String keyword){
    
        List<Task> tasks =taskRepository.findByTitleContainingIgnoreCase(getCurrentUser(),keyword);

        return tasks.stream()
                    .map(task -> modelMapper.map(task,TaskResponseDTO.class))
                    .toList();
    }

    private User getCurrentUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
