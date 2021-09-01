package com.example.math_system.controller;

import com.example.math_system.entity.task.Algorithm;
import com.example.math_system.repo.AlgorithmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController("plugin")
public class PluginController {
    private final AlgorithmRepository algorithmRepository;

    @Autowired
    public PluginController(AlgorithmRepository algorithmRepository) {
        this.algorithmRepository = algorithmRepository;
    }

    @PostMapping("newPlugin")
    public Algorithm addNewAlgorithm(@RequestBody String pluginName) {
        File jarFile = new File("");
        return null;
    }
}
