package com.mathsystem.entity.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mathsystem.plugin.PluginType;

import javax.persistence.*;

@Entity
@Table(name = "alg_answer")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "algorithm_type")
@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PropertyAnswer.class, name = "property"),
        @JsonSubTypes.Type(value = CharacteristicAnswer.class, name = "characteristic")
})
public abstract class AlgAnswer {
    @Id
    @GeneratedValue
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "algorithm_id")
    protected Algorithm algorithm;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "task_id")
    protected Task task;

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
//                ", algorithm=" + algorithm +
//                ", task=" + task +
                '}';
    }
}
