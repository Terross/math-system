package com.mathsystem.entity.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "task_permission")
public class TaskPermission {

    @OneToOne(mappedBy = "taskPermission")
    @JsonIgnore
    private Task task;

    private boolean edit;
    private boolean color;
    private boolean draw;
    private boolean remove;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;


    @Override
    public String toString() {
        return "TaskPermission{" +
                ", edit=" + edit +
                ", color=" + color +
                ", draw=" + draw +
                ", remove=" + remove +
                ", id=" + id +
                '}';
    }
}
