package com.mathsystem.domain.task.repository;

import com.mathsystem.domain.graph.repository.GraphProjection;
import com.mathsystem.domain.user.repository.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDecision {

    @Id
    @GeneratedValue
    private UUID id;

    private LocalDateTime created;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "graph_id")
    private GraphProjection graphProjection;

    private Boolean isRight;

}
