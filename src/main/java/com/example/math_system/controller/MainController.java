package com.example.math_system.controller;

import com.example.math_system.entity.task.Algorithm;
import com.example.math_system.entity.task.Task;
import com.example.math_system.repo.AlgorithmRepo;
import com.example.math_system.repo.GraphRepo;
import com.example.math_system.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    private final GraphRepo graphRepo;
    private final AlgorithmRepo algorithmRepo;
    private final TaskRepo taskRepo;

    @Autowired
    public MainController(GraphRepo graphRepo, AlgorithmRepo algorithmRepo, TaskRepo taskRepo) {
        this.graphRepo = graphRepo;
        this.algorithmRepo = algorithmRepo;
        this.taskRepo = taskRepo;
    }

    @GetMapping
    public String main(Model model) throws FileNotFoundException {
        List<Algorithm> algorithms = algorithmRepo.findAll();
        List<Task> tasks = taskRepo.findAll();
        HashMap<Object, Object> data = new HashMap<>();
        data.put("graph", null);
        data.put("algorithms", algorithms);
        data.put("tasks", tasks);
        model.addAttribute("frontendData", data);
        return "index";
    }
}
