package com.mathsystem.domain.task;

import com.mathsystem.domain.task.repository.Task;
import com.mathsystem.domain.task.repository.TaskRepository;
import com.mathsystem.exceptions.BusinessException;
import com.mathsystem.exceptions.DataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.mathsystem.exceptions.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;


    public Task saveNewTask(Task task) {
        verifyTask(task);
        if (taskRepository.findAllByName(task.getName()).isEmpty()) {
            task.getGraph().prepareToSave();
            return taskRepository.save(task);
        } else {
            throw new BusinessException(TASK_ALREADY_EXIST, "Task with id = %s already exist"
                    .formatted(task.getName()));
        }

    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    public void deleteTask(UUID id) {
        Task task = taskRepository
                .findById(id)
                .orElseThrow(() -> new DataException(TASK_NOT_FOUND, TASK_NOT_FOUND.name()));
        taskRepository.delete(task);
    }

    private void verifyTask(Task task) {
        if (task.getTaskDescription().isEmpty() || task.getCategory().isEmpty()) {
            throw new BusinessException(TASK_VALIDATION_ERROR, "Wrong task category or name");
        }
    }
}
