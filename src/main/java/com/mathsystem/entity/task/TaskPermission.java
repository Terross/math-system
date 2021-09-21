package com.mathsystem.entity.task;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


@Entity
@Table(name = "task_permission")
public class TaskPermission {

    @OneToOne(mappedBy = "taskPermission")
    @JsonIgnore
    private Task task;

    private boolean edit;
    private boolean color;
    private boolean weight;
    private boolean remove;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public boolean isWeight() {
        return weight;
    }

    public void setWeight(boolean weight) {
        this.weight = weight;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "TaskPermission{" +
                ", edit=" + edit +
                ", color=" + color +
                ", weight=" + weight +
                ", remove=" + remove +
                ", id=" + id +
                '}';
    }
}
