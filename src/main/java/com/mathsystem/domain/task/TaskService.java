package com.mathsystem.domain.task;

import com.mathsystem.api.graph.mapper.GraphMapper;
import com.mathsystem.domain.plugin.nativerealization.NativePluginService;
import com.mathsystem.domain.task.repository.Task;
import com.mathsystem.domain.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
