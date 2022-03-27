package com.mathsystem.domain.task;

import com.mathsystem.domain.task.repository.Task;
import com.mathsystem.domain.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task saveNewTask(Task task) {
        return taskRepository.save(task);
    }
}
