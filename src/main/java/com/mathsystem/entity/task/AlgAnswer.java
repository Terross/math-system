package com.mathsystem.entity.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mathsystem.plugin.PluginType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "alg_answer")
@Data
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


    @Override
    public String toString() {
        return "AlgAnswer{" +
                "id=" + id +
//                ", algorithm=" + algorithm +
//                ", task=" + task +
                '}';
    }
}
