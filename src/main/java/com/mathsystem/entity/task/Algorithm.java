package com.mathsystem.entity.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mathsystem.entity.graph.GraphType;
import com.mathsystem.graphapi.AbstractGraph;
import com.mathsystem.plugin.GraphCharacteristic;
import com.mathsystem.plugin.GraphProperty;
import com.mathsystem.plugin.Plugin;
import com.mathsystem.plugin.PluginFactory;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "algorithm")
@Entity
@Data
public class Algorithm {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private AlgorithmType algorithmType;
    private GraphType graphType;
    private String fileName;

    @JsonIgnore
    @OneToMany(mappedBy = "algorithm", cascade = CascadeType.ALL)
    private List<AlgAnswer> algAnswerList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public AlgorithmType getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(AlgorithmType algorithmType) {
        this.algorithmType = algorithmType;
    }

    public String getAnswerForGraph(AbstractGraph abstractGraph) {
        String result;
        int index = fileName.lastIndexOf('.');
        String name = fileName.substring(0, index);

        Plugin plugin =  PluginFactory.loadPlugin(name);
        if (plugin instanceof GraphCharacteristic) {
            result = String.valueOf(((GraphCharacteristic) plugin).execute(abstractGraph));

        } else {
            if (((GraphProperty) plugin).execute(abstractGraph)) {
                result = "Верно";
            } else {
                result = "Неверно";
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (AlgAnswer algAnswer : algAnswerList) {
            stringBuilder.append(algAnswer.getId());
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