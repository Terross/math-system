package com.mathsystem.util;

import com.mathsystem.entity.graph.Edge;
import com.mathsystem.entity.graph.Graph;
import com.mathsystem.entity.graph.Vertex;
import com.mathsystem.entity.task.*;
import com.mathsystem.graphapi.AbstractGraph;
import com.mathsystem.graphapi.GraphFactory;
import com.mathsystem.plugin.GraphCharacteristic;
import com.mathsystem.plugin.GraphProperty;
import com.mathsystem.plugin.PluginFactory;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Validator {
    private final Graph graph;

    public Validator(Graph graph) {
        this.graph = graph;
    }

    public boolean verifyTask(Task task) throws ChangeSetPersister.NotFoundException {
        boolean result = true;

        AbstractGraph abstractGraph = GraphFactory.createGraph(graph.getVertexCount(),
                graph.getEdgeCount(),
                findEdgeList(graph),
                graph.getGraphType(),
                graph.getVertexes());


        for (AlgAnswer alganswer:
                task.getAlgAnswerList()) {
            int index = alganswer.getAlgorithm().getFileName().lastIndexOf('.');
            String name  = alganswer.getAlgorithm().getFileName().substring(0, index);

            if (alganswer instanceof CharacteristicAnswer) {
                GraphCharacteristic graphCharacteristic =
                        (GraphCharacteristic) PluginFactory.loadPlugin(name);
                if (!Objects.equals(graphCharacteristic.execute(abstractGraph), ((CharacteristicAnswer) alganswer).getAnswer())) {
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

    public List<PlgAns> findAnswersForPlugins(List<Algorithm> algorithms) throws ChangeSetPersister.NotFoundException {
        List<Validator.PlgAns> plgAnsList = new ArrayList<>();
        List<Edge> edgeArrayList = findEdgeList(graph);

        AbstractGraph abstractGraph = GraphFactory.createGraph(graph.getVertexCount(),
                graph.getEdgeCount(),
                edgeArrayList,
                graph.getGraphType(),
                graph.getVertexes());

        for (Algorithm algorithm : algorithms) {
            plgAnsList.add(new PlgAns(algorithm.getName(),
                    algorithm.getAnswerForGraph(abstractGraph)));
        }

        return plgAnsList;
    }

    public static class PlgAns {
        private String pluginName;
        private String answer;

        public PlgAns(String pluginName, String answer) {
            this.pluginName = pluginName;
            this.answer = answer;
        }

        public String getPluginName() {
            return pluginName;
        }
        public void setPluginName(String pluginName) {
            this.pluginName = pluginName;
        }
        public String getAnswer() {
            return answer;
        }
        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }

    private List<Edge> findEdgeList(Graph graph) throws ChangeSetPersister.NotFoundException {
        List<Vertex> vertexes = graph.getVertexes();
        ArrayList<Edge> edgeArrayList = new ArrayList<>();
        for (Vertex vertex:
                graph.getVertexes()) {
            for (Edge edge:
                    vertex.getIncomingEdges()) {
                edge.setToVertex(vertex);
                edge.setFromVertex(vertexes.stream().filter(v -> v.getName().equals(edge.getFromV())).findFirst()
                        .orElseThrow(ChangeSetPersister.NotFoundException::new));
                edgeArrayList.add(edge);
            }
            vertex.setGraph(graph);
        }

        return edgeArrayList;
    }
}
