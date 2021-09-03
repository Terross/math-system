package com.example.math_system.controller;

import com.example.math_system.entity.graph.Graph;
import com.example.math_system.entity.task.Task;
import com.example.math_system.repo.AlgorithmRepo;
import com.example.math_system.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("task")
public class TaskController {
    private final AlgorithmRepo algorithmRepo;
    private final TaskRepo taskRepo;

    @Autowired
    public TaskController(AlgorithmRepo algorithmRepo, TaskRepo taskRepo) {
        this.algorithmRepo = algorithmRepo;
        this.taskRepo = taskRepo;
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
    public Task addNewTask(@RequestBody Task task) {
        Task taskForDB = new Task();
        taskForDB.setAlgAnswerList(task.getAlgAnswerList());
        taskForDB.setGraph(task.getGraph());
        taskForDB.setCategory(task.getCategory());
        taskForDB.setName(task.getName());

        taskRepo.save(taskForDB);
        return null;
    }

}
