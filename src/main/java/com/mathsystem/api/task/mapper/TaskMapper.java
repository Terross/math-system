package com.mathsystem.api.task.mapper;

import com.mathsystem.api.task.model.Task;
import com.mathsystem.domain.task.repository.TaskProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task taskProjectionToTask(TaskProjection taskProjection);
    TaskProjection taskToTaskProjection(Task task);
}
