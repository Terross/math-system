package com.mathsystem.domain.task.repository;


import com.mathsystem.domain.plugin.repository.PluginAnswerProjection;
import com.mathsystem.domain.task.graph.repository.GraphProjection;
import com.mathsystem.domain.task.graph.repository.GraphType;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@ToString
public class TaskProjection {

    @Id
    @GeneratedValue
    private UUID id;

    private GraphType graphType;

    private String name;

    private String category;

    @Column(name = "task_description", length = 10000000)
    private String taskDescription;

    @OneToMany(mappedBy = "taskProjection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PluginAnswerProjection> pluginAnswers;

    @OneToOne(mappedBy = "taskProjection", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "graph_id")
    private GraphProjection graphProjection;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "task_permission_id")
    private TaskPermission taskPermission;
}
