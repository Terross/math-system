package com.example.math_system.controller;

import com.example.math_system.repo.GraphRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;

@Controller
@RequestMapping("/")
public class MainController {

    private GraphRepo graphRepo;

    @Autowired
    public MainController(GraphRepo graphRepo) {
        this.graphRepo = graphRepo;
    }

    @GetMapping
    public String main(Model model) throws FileNotFoundException {
//        HashMap<Object, Object> data = new HashMap<>();
//        data.put("graph", new Graph(new File("/home/dmitry/Documents/tiny.txt")));
        model.addAttribute("frontendData", null);
        return "index";
    }
}
