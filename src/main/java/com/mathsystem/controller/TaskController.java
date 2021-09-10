package com.mathsystem.controller;

import com.mathsystem.entity.graph.Edge;
import com.mathsystem.entity.graph.Graph;
import com.mathsystem.entity.graph.Vertex;
import com.mathsystem.entity.task.AlgAnswer;
import com.mathsystem.entity.task.Task;
import com.mathsystem.repo.AlgAnswerRepo;
import com.mathsystem.repo.AlgorithmRepo;
import com.mathsystem.repo.TaskRepo;
import com.mathsystem.repo.VertexRepo;
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

    @PostMapping("verifyTask/{id}")
    public boolean verifyTask(@PathVariable Long id,
                              @RequestBody Graph graph) throws FileNotFoundException {
        Task task = taskRepo.getById(id);
        boolean result = task.verify(graph);
        return result;
    }

    @PostMapping
    public Task addNewTask(@RequestBody Task task) throws ChangeSetPersister.NotFoundException {
        System.out.println(task);
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

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Task task = taskRepo.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        taskRepo.delete(task);
    }


}
