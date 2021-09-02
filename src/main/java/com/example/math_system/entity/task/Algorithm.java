package com.example.math_system.entity.task;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Table(name = "algorithm")
@Entity
public class Algorithm {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    @ManyToMany(mappedBy = "algorithms")
    private Set<Task> tasks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}