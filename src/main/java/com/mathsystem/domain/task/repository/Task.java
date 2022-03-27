package com.mathsystem.domain.task.repository;


import com.mathsystem.domain.graph.repository.GraphType;
import com.mathsystem.domain.graph.repository.GraphProjection;
import com.mathsystem.domain.plugin.repository.PluginValue;
import com.mathsystem.domain.user.repository.User;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
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

    private String authorEmail;

    @Column(name = "task_description", length = 10000000)
    private String taskDescription;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "graph_id")
    private GraphProjection graph;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "task_permission_id")
    private TaskPermission taskPermission;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "task_id")
    private List<PluginValue> pluginValues;
}
