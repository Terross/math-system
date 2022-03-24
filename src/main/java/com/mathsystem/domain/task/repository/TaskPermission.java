package com.mathsystem.domain.task.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Data
@Table(name = "task_permission")
@ToString
public class TaskPermission {

    @Id
    @GeneratedValue
    private UUID id;

    @JsonIgnore
    @OneToOne(mappedBy = "taskPermission")
    private Task task;

    private boolean edit;

    private boolean color;

    private boolean draw;

    private boolean remove;
}
