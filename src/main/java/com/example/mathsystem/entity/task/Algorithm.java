package com.example.mathsystem.entity.task;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "algorithm")
@Entity
public class Algorithm {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "algorithm", cascade = CascadeType.ALL)
    private List<AlgAnswer> algAnswerList = new ArrayList<>();

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

    public List<AlgAnswer> getAlgAnswerList() {
        return algAnswerList;
    }

    public void setAlgAnswerList(List<AlgAnswer> algAnswerList) {
        this.algAnswerList = algAnswerList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (int i = 0; i < algAnswerList.size(); i++) {
            stringBuilder.append(algAnswerList.get(i).getId());
        }
        stringBuilder.append("]");
        return "Algorithm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", algAnswerList=" + stringBuilder +
                '}';
    }
}