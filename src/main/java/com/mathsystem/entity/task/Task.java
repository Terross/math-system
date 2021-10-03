package com.mathsystem.entity.task;

import com.mathsystem.entity.graph.Graph;
import com.mathsystem.entity.graph.GraphType;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "task")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private GraphType graphType;

    private String name;
    private String category;
    @Column(name = "task_description", length = 10000000)
    private String taskDescription;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlgAnswer> algAnswerList;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "graph_id")
    private Graph graph;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "task_permission_id")
    private TaskPermission taskPermission;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", algAnswerList=" + algAnswerList +
                ", graph=" + graph +
                ", permission=" + taskPermission +
                '}';
    }
}
