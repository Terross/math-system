package com.mathsystem.entity.task;

import com.mathsystem.entity.graph.Graph;
import com.mathsystem.entity.graph.GraphType;
import com.mathsystem.graphapi.AbstractGraph;
import com.mathsystem.graphapi.GraphFactory;
import com.mathsystem.plugin.GraphCharacteristic;
import com.mathsystem.plugin.GraphProperty;

import com.mathsystem.plugin.PluginFactory;

import javax.persistence.*;
import java.io.FileNotFoundException;
import java.util.*;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private GraphType graphType;

    private String name;
    private String category;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlgAnswer> algAnswerList;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "graph_id")
    private Graph graph;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "task_permission_id")
    private TaskPermission taskPermission;

    public boolean verify(AbstractGraph abstractGraph) throws FileNotFoundException {
        boolean result = true;

        //TO-DO: переписать этот костыль
        for (AlgAnswer alganswer:
             algAnswerList) {
            String name = alganswer.getAlgorithm().getName();

            if (alganswer instanceof CharacteristicAnswer) {
                GraphCharacteristic graphCharacteristic =
                        (GraphCharacteristic) PluginFactory.loadPlugin(name);

                if (Math.abs(graphCharacteristic.execute(abstractGraph) - ((CharacteristicAnswer) alganswer).getAnswer()) > 0.0001 ) {
                    result = false;
                }
            } else {
                GraphProperty graphProperty =
                        (GraphProperty) PluginFactory.loadPlugin(name);

                    result = graphProperty.execute(abstractGraph) == ((PropertyAnswer) alganswer).isAnswer();

            }
        }
        return result;
    }

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

    public List<AlgAnswer> getAlgAnswerList() {
        return algAnswerList;
    }

    public void setAlgAnswerList(List<AlgAnswer> algAnswerList) {
        this.algAnswerList = algAnswerList;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public TaskPermission getTaskPermission() {
        return taskPermission;
    }

    public void setTaskPermission(TaskPermission taskPermission) {
        this.taskPermission = taskPermission;
    }

    public GraphType getGraphType() {
        return graphType;
    }

    public void setGraphType(GraphType graphType) {
        this.graphType = graphType;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", algAnswerList=" + algAnswerList +
                ", graph=" + graph +
                ", permission=" + taskPermission +
                '}';
    }
}
