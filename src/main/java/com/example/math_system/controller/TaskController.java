package com.example.math_system.controller;

import com.example.math_system.entity.graph.Edge;
import com.example.math_system.entity.graph.Graph;
import com.example.math_system.entity.graph.Vertex;
import com.example.math_system.entity.task.AlgAnswer;
import com.example.math_system.entity.task.Task;
import com.example.math_system.repo.AlgAnswerRepo;
import com.example.math_system.repo.AlgorithmRepo;
import com.example.math_system.repo.TaskRepo;
import com.example.math_system.repo.VertexRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
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

    @GetMapping("verifyTask/{id}")
    public boolean verifyTask(@PathVariable Long id,
                              @RequestBody Graph graph) throws FileNotFoundException {
        Task task = taskRepo.getById(id);
        return task.verify(graph);
    }

    @PostMapping("addNewTask")
    public Task addNewTask(@RequestBody Task task) throws ChangeSetPersister.NotFoundException {
        System.out.println(task);
        List<AlgAnswer> algAnswerList = task.getAlgAnswerList();
        Graph graph = task.getGraph();
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
            for (Edge edge:
                    vertex.getOutgoingEdges()) {
                edge.setFromVertex(vertex);
                edge.setToVertex(vertexes.stream().filter(v -> v.getName().equals(edge.getToV())).findFirst()
                        .orElseThrow(ChangeSetPersister.NotFoundException::new));
            }
            vertex.setGraph(graph);
        }
        System.out.println(graph);
        for (int i = 0; i < algAnswerList.size(); i++) {
            AlgAnswer algAnswer = algAnswerList.get(i);
            algAnswer.getAlgorithm().getAlgAnswerList().add(algAnswer);
            algAnswer.setTask(task);
        }
        taskRepo.save(task);

        return task;
    }
    @PostMapping("newEdge")
    public void test(@RequestBody Edge edge) {
        System.out.println(edge);
    }

}
