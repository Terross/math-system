package com.mathsystem.domain.task.repository;


import com.mathsystem.domain.graph.repository.GraphProjection;
import com.mathsystem.api.graph.model.GraphType;
import com.mathsystem.domain.plugin.repository.PluginAnswer;
import com.mathsystem.domain.user.repository.User;
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

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "task_description", length = 10000000)
    private String taskDescription;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PluginAnswer> pluginAnswers;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "graph_id")
    private GraphProjection graphProjection;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "task_permission_id")
    private TaskPermission taskPermission;
}
