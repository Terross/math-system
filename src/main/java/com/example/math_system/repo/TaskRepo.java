package com.example.math_system.repo;

import com.example.math_system.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
    public List<Task> findAllByName(String name);
}
