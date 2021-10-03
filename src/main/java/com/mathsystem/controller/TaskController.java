package com.mathsystem.controller;

import com.mathsystem.entity.graph.Edge;
import com.mathsystem.entity.graph.Graph;
import com.mathsystem.entity.graph.Vertex;
import com.mathsystem.entity.task.*;
import com.mathsystem.exceptions.TaskAlreadyExistsException;
import com.mathsystem.graphapi.AbstractGraph;
import com.mathsystem.graphapi.GraphFactory;
import com.mathsystem.repo.AlgAnswerRepo;
import com.mathsystem.repo.AlgorithmRepo;
import com.mathsystem.repo.TaskRepo;
import com.mathsystem.repo.VertexRepo;
import com.mathsystem.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    @Autowired
    public TaskController(AlgorithmRepo algorithmRepo, TaskRepo taskRepo, AlgAnswerRepo algAnswerRepo, VertexRepo vertexRepo) {
        this.algorithmRepo = algorithmRepo;
        this.taskRepo = taskRepo;
    }

    @PostMapping("verifyTask/{id}")
    public boolean verifyTask(@PathVariable Long id,
                              @RequestBody Graph graph) throws FileNotFoundException, InterruptedException, ChangeSetPersister.NotFoundException {
        Task task = taskRepo.getById(id);
        Validator validator = new Validator(graph);
        boolean result = validator.verifyTask(task);
        System.out.println(result);
        return result;
    }

    @PostMapping
    public Task addNewTask(@RequestBody Task task) throws ChangeSetPersister.NotFoundException {
        if (!taskRepo.findAllByName(task.getName()).isEmpty() ) {
            logger.error("Task already exist!!!");
            throw new TaskAlreadyExistsException();
        }
        List<AlgAnswer> algAnswerList = task.getAlgAnswerList();
        Graph graph = task.getGraph();
        if (graph != null) {
            List<Vertex> vertexes = graph.getVertexes();
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

        for (AlgAnswer algAnswer : algAnswerList) {
            algAnswer.getAlgorithm().getAlgAnswerList().add(algAnswer);
            algAnswer.setTask(task);
        }
        taskRepo.save(task);
        logger.info(String.valueOf(task));
        return task;
    }

    @PostMapping("/answers")
    public List<Validator.PlgAns> getAnswers(@RequestBody Graph graph) throws ChangeSetPersister.NotFoundException {
        Validator validator = new Validator(graph);

        return validator.findAnswersForPlugins(algorithmRepo.findAllByGraphType(graph.getGraphType()));
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Task task = taskRepo.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        taskRepo.delete(task);
    }
}
