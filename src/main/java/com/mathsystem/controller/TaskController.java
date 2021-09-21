package com.mathsystem.controller;

import com.mathsystem.entity.graph.Edge;
import com.mathsystem.entity.graph.Graph;
import com.mathsystem.entity.graph.Vertex;
import com.mathsystem.entity.task.*;
import com.mathsystem.graphapi.AbstractGraph;
import com.mathsystem.graphapi.GraphFactory;
import com.mathsystem.repo.AlgAnswerRepo;
import com.mathsystem.repo.AlgorithmRepo;
import com.mathsystem.repo.TaskRepo;
import com.mathsystem.repo.VertexRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("task")
public class TaskController {
    private final AlgorithmRepo algorithmRepo;
    private final TaskRepo taskRepo;
    private final AlgAnswerRepo algAnswerRepo;
    private final VertexRepo vertexRepo;

    @Autowired
    public TaskController(AlgorithmRepo algorithmRepo, TaskRepo taskRepo, AlgAnswerRepo algAnswerRepo, VertexRepo vertexRepo) {
        this.algorithmRepo = algorithmRepo;
        this.taskRepo = taskRepo;
        this.algAnswerRepo = algAnswerRepo;
        this.vertexRepo = vertexRepo;
    }

    @GetMapping("showAllTask")
    public List<Task> getAllTask() {
        List<Task> tasks = taskRepo.findAll();
        return tasks;
    }

    @PostMapping("verifyTask/{id}")
    public boolean verifyTask(@PathVariable Long id,
                              @RequestBody Graph graph) throws FileNotFoundException, InterruptedException, ChangeSetPersister.NotFoundException {
        Task task = taskRepo.getById(id);
        ArrayList<Edge> edgeArrayList = (ArrayList<Edge>) findEdgeList(graph);

        AbstractGraph abstractGraph = GraphFactory.createGraph(graph.getVertexCount(),
                                                               graph.getEdgeCount(),
                                                               edgeArrayList,
                                                               graph.getGraphType(),
                                                               graph.getVertexes());
        return task.verify(abstractGraph);
    }

    @PostMapping
    public Task addNewTask(@RequestBody Task task) throws ChangeSetPersister.NotFoundException {

        List<AlgAnswer> algAnswerList = task.getAlgAnswerList();
        Graph graph = task.getGraph();
        if (graph != null) {
            List<Vertex> vertexes = graph.getVertexes();
            System.out.println(graph.getVertexes());
            for (Vertex vertex:
                    graph.getVertexes()) {
                for (Edge edge:
                        vertex.getIncomingEdges()) {
                    edge.setToVertex(vertex);
                    edge.setFromVertex(vertexes.stream().filter(v -> v.getName().equals(edge.getFromV())).findFirst()
                            .orElseThrow(ChangeSetPersister.NotFoundException::new));
                }
                vertex.setGraph(graph);
            }
            System.out.println(graph);
        }

        for (int i = 0; i < algAnswerList.size(); i++) {
            AlgAnswer algAnswer = algAnswerList.get(i);
            algAnswer.getAlgorithm().getAlgAnswerList().add(algAnswer);
            algAnswer.setTask(task);
        }
        taskRepo.save(task);
        System.out.println(task);
        return task;
    }

    @PostMapping("/answers")
    public List<PlgAns> getAnswers(@RequestBody Graph graph) throws ChangeSetPersister.NotFoundException {
        List<PlgAns> plgAnsList = new ArrayList<>();
        ArrayList<Edge> edgeArrayList = (ArrayList<Edge>) findEdgeList(graph);

        List<Algorithm> algorithms = algorithmRepo.findAll();
        AbstractGraph abstractGraph = GraphFactory.createGraph(graph.getVertexCount(),
                                                                graph.getEdgeCount(),
                                                                edgeArrayList,
                                                                graph.getGraphType(),
                                                                graph.getVertexes());
        for (int i = 0; i < algorithms.size(); i++) {
            Algorithm algorithm = algorithms.get(i);
            plgAnsList.add(new PlgAns(algorithm.getName(),
                    algorithm.getAnswerForGraph(abstractGraph)));
        }

        return plgAnsList;
    }

    private List<Edge> findEdgeList(@RequestBody Graph graph) throws ChangeSetPersister.NotFoundException {
        List<Vertex> vertexes = graph.getVertexes();
        ArrayList<Edge> edgeArrayList = new ArrayList<>();
        for (Vertex vertex:
                graph.getVertexes()) {
            System.out.println(vertex.getIncomingEdges().size() + vertex.getName());
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

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Task task = taskRepo.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        taskRepo.delete(task);
    }

    private static class PlgAns {
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
}
