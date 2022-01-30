package com.mathsystem.entity.task;

import com.mathsystem.domain.task.graph.repo.Graph;
import com.mathsystem.domain.task.graph.repo.GraphType;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@ToString
public class Task {

    @Id
    @GeneratedValue
    private UUID id;

    private GraphType graphType;

    private String name;

    private String category;

    @Column(name = "task_description", length = 10000000)
    private String taskDescription;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PluginAnswer> pluginAnswers;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "graph_id")
    private Graph graph;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "task_permission_id")
    private TaskPermission taskPermission;
}
