package com.shiv.task_manager_api.repository;

import java.util.List;
import com.shiv.task_manager_api.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import com.shiv.task_manager_api.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Optional;



public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByStatus(String status);

    List<Task> findByTitleContainingIgnoreCase(User user,String keyword);

    List<Task> findByUser(User user);/*spring sees the Task entity and automatically generates
    an sql query asking for  all tasks having a user_id(foreign key) equals to user's id,this all done by SpringDataJPA  */

    Page<Task> findByUser(User user,Pageable pageable); 

    Optional<Task> findByIdAndUser(Long id,User user);

    void deleteByIdAndUser(Long id ,User User);

    List<Task> findByUserAndTitleContainingIgnoreCase(
        User user,
        String keyword
    );
    
}


