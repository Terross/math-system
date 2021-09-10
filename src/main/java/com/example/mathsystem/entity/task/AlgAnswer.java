package com.example.mathsystem.entity.task;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "alg_answer")
public class AlgAnswer {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "algorithm_id")
    private Algorithm algorithm;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "task_id")
    private Task task;

    private Double answer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Double getAnswer() {
        return answer;
    }

    public void setAnswer(Double answer) {
        this.answer = answer;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "AlgAnswer{" +
                "id=" + id +
                ", algorithm=" + algorithm +
                ", answer=" + answer +
                '}';
    }
}
