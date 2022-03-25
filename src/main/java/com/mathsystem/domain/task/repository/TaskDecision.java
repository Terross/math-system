package com.mathsystem.domain.task.repository;

import com.mathsystem.domain.graph.repository.GraphProjection;
import com.mathsystem.domain.user.repository.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class TaskDecision {

    @Id
    @GeneratedValue
    private UUID id;

    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private TaskProjection taskProjection;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "graph_id")
    private GraphProjection graphProjection;

}
