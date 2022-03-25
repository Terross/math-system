package com.mathsystem.domain.task;

import com.mathsystem.api.task.mapper.TaskMapper;
import com.mathsystem.api.task.model.Task;
import com.mathsystem.domain.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public Task saveNativePlugin(Task task) {
        return taskMapper
                .taskProjectionToTask(taskRepository
                        .save(taskMapper.taskToTaskProjection(task)));
    }
}
