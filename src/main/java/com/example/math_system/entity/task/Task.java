package com.example.math_system.entity.task;

import com.example.math_system.entity.graph.Graph;
import com.example.math_system.plugin.Plugin;
import com.example.math_system.plugin.PluginLoader;

import javax.persistence.*;
import java.io.FileNotFoundException;
import java.util.*;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String name;
    private String category;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<AlgAnswer> algAnswerList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "graph_id")
    private Graph graph;

    public boolean verify(Graph graph) throws FileNotFoundException {
        boolean result = true;
        PluginLoader pluginLoader = new PluginLoader();
        for (AlgAnswer alganswer:
             algAnswerList) {
            Plugin plugin = pluginLoader.loadPlugin(alganswer.getAlgorithm().getName());
            if (Math.abs(plugin.execute(graph) - alganswer.getAnswer()) > 0.001 ) {
                result = false;
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

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", algAnswerList=" + algAnswerList +
                ", graph=" + graph +
                '}';
    }
}
