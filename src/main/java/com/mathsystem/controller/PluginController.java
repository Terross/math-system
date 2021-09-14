package com.mathsystem.controller;

import com.mathsystem.entity.task.Algorithm;
import com.mathsystem.exceptions.PluginAlreadyExistsException;
import com.mathsystem.exceptions.PluginNotFoundException;
import com.mathsystem.repo.AlgorithmRepo;
import com.mathsystem.repo.GraphRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

@RestController()
@RequestMapping("plugin/api")
public class PluginController {
    private final AlgorithmRepo algorithmRepo;
    private final GraphRepo graphRepo;
    private final String defaultDirForPlugin = "/home/dmitry/IdeaProjects/math-system/plugins/";

    private static class PluginBody {
        private String description;
        private String name;
        private MultipartFile file;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public MultipartFile getFile() {
            return file;
        }

        public void setFile(MultipartFile file) {
            this.file = file;
        }
    }

    @Autowired
    public PluginController(AlgorithmRepo algorithmRepo, GraphRepo graphRepo) {
        this.algorithmRepo = algorithmRepo;
        this.graphRepo = graphRepo;
    }

    @GetMapping("showPlugins")
    public List<Algorithm> showAllAlgorithm() {
        List<Algorithm> algorithms = algorithmRepo.findAll();
        return algorithms;
    }



    @PostMapping
    public Algorithm addNewAlgorithm(@RequestParam("description") String description,
                                     @RequestParam("name") String name,
                                     @RequestParam("file") MultipartFile file) {
        if (!algorithmRepo.findAlgorithmByName(name).isEmpty()) {
            throw new PluginAlreadyExistsException();
        }

        Algorithm algorithm = null;

        try {
            File newJarFile = new File(defaultDirForPlugin + name + ".jar" );
            if (newJarFile.createNewFile()) {
                file.transferTo(newJarFile);
                algorithm = new Algorithm();
                algorithm.setName(name);
                algorithm.setDescription(description);
                algorithmRepo.save(algorithm);
            } else {
                throw new FileAlreadyExistsException("File already exist!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return algorithm;
    }

    @DeleteMapping("/{id}")
    public void deleteAlgorithm(@PathVariable Long id) {
        Algorithm algorithm = algorithmRepo.findById(id)
                .orElseThrow(PluginNotFoundException::new);
        deleteFileFromDisk(defaultDirForPlugin + algorithm.getName() + ".jar");
        algorithmRepo.delete(algorithm);
    }

    @PutMapping("/{id}")
    public Algorithm updateAlgorithm(@PathVariable Long id,
                                     @RequestParam("description") String description,
                                     @RequestParam("name") String name,
                                     @RequestParam("file") MultipartFile file) {

        Algorithm algorithm = algorithmRepo.findById(id)
                .orElseThrow(PluginNotFoundException::new);
        deleteFileFromDisk(defaultDirForPlugin + algorithm.getName() + ".jar");

        algorithm.setDescription(description);
        algorithm.setName(name);
        try {
            File newJarFile = new File(defaultDirForPlugin + name + ".jar" );
            if (newJarFile.createNewFile()) {
                file.transferTo(newJarFile);
                algorithmRepo.save(algorithm);
            } else {
                System.out.println("File exist!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return algorithm;

    }

    private void deleteFileFromDisk(String fileName) {
        File file = new File(fileName);
        if (file.delete()) {
            System.out.println(file.getName() + " deleted");
        } else {
            System.out.println("Failed");
        }
    }

}