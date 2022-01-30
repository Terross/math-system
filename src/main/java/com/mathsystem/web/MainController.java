package com.mathsystem.controller;

import com.mathsystem.entity.task.PluginProjection;
import com.mathsystem.entity.task.Task;
import com.mathsystem.repo.AlgorithmRepo;
import com.mathsystem.domain.task.graph.repo.GraphRepository;
import com.mathsystem.repo.TaskRepo;
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

    private final GraphRepository graphRepository;
    private final AlgorithmRepo algorithmRepo;
    private final TaskRepo taskRepo;

    @Autowired
    public MainController(GraphRepository graphRepository, AlgorithmRepo algorithmRepo, TaskRepo taskRepo) {
        this.graphRepository = graphRepository;
        this.algorithmRepo = algorithmRepo;
        this.taskRepo = taskRepo;
    }

    @GetMapping
    public String main(Model model) throws FileNotFoundException {
        List<PluginProjection> pluginProjections = algorithmRepo.findAll();
        List<Task> tasks = taskRepo.findAll();
        HashMap<Object, Object> data = new HashMap<>();

        data.put("graph", null);
        data.put("algorithms", pluginProjections);
        data.put("tasks", tasks);
        model.addAttribute("frontendData", data);
        return "index";
    }
}
