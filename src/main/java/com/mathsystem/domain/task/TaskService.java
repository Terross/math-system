package com.mathsystem.domain.task;

import com.mathsystem.domain.task.repository.Task;
import com.mathsystem.domain.task.repository.TaskRepository;
import com.mathsystem.exceptions.DataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.mathsystem.exceptions.ErrorCode.TASK_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;


    public Task saveNewTask(Task task) {
        task.getGraph().prepareToSave();
        return taskRepository.save(task);
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
}
