package com.mathsystem.api.task.model;

import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.model.GraphType;
import com.mathsystem.domain.plugin.repository.PluginAnswer;
import com.mathsystem.domain.task.repository.TaskPermission;
import com.mathsystem.domain.user.repository.User;
import lombok.Data;

import java.util.List;

@Data
public class Task {

    private GraphType graphType;

    private String name;

    private String category;

    private User author;

    private String taskDescription;

    private List<PluginAnswer> pluginAnswers;

    private Graph graph;

    private TaskPermission taskPermission;

}
