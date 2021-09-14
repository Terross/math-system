package com.mathsystem.repo;

import com.mathsystem.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
    public List<Task> findAllByName(String name);
}