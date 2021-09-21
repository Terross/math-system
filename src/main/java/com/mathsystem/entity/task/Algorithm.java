package com.mathsystem.entity.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mathsystem.graphapi.AbstractGraph;
import com.mathsystem.plugin.GraphCharacteristic;
import com.mathsystem.plugin.GraphProperty;
import com.mathsystem.plugin.Plugin;
import com.mathsystem.plugin.PluginFactory;

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
    private AlgorithmType algorithmType;

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

        String name = getName();

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