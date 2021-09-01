package com.example.math_system.entity.task;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String name;
    private String category;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Algorithm",
            joinColumns = { @JoinColumn(name = "task_id") },
            inverseJoinColumns = { @JoinColumn(name = "algorithm_id") }
    )
    private ArrayList<Algorithm> algorithms;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<Algorithm> getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(ArrayList<Algorithm> algorithms) {
        this.algorithms = algorithms;
    }
}
