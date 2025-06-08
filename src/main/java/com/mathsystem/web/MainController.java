package com.mathsystem.web;

import com.mathsystem.domain.plugin.repository.PluginRepository;
import com.mathsystem.domain.plugin.repository.PluginProjection;
import com.mathsystem.domain.graph.repository.GraphRepository;
import com.mathsystem.domain.task.repository.Task;
import com.mathsystem.domain.task.repository.TaskRepository;
import com.mathsystem.domain.user.repository.User;
import com.mathsystem.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    private final GraphRepository graphRepository;
    private final PluginRepository pluginRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @GetMapping
    public String main(Model model) throws FileNotFoundException {
        List<PluginProjection> pluginProjections = pluginRepository.findAll();
        List<Task> tasks = taskRepository.findAll();
        List<User> users = userRepository.findAll();
        Map<Object, Object> data = new HashMap<>();

        data.put("graph", null);
        data.put("algorithms", pluginProjections);
        data.put("tasks", tasks);
        data.put("users", users);
        model.addAttribute("frontendData", data);
        return "index";
    }
}
