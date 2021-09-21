package com.mathsystem.controller;

import com.mathsystem.entity.task.Algorithm;
import com.mathsystem.entity.task.AlgorithmType;
import com.mathsystem.exceptions.PluginAlreadyExistsException;
import com.mathsystem.exceptions.PluginCreatingException;
import com.mathsystem.exceptions.PluginNotFoundException;
import com.mathsystem.plugin.GraphCharacteristic;
import com.mathsystem.plugin.GraphProperty;
import com.mathsystem.plugin.Plugin;
import com.mathsystem.plugin.PluginFactory;
import com.mathsystem.repo.AlgorithmRepo;
import com.mathsystem.repo.GraphRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final String defaultDirForPlugin =
            "/home/dmitry/Documents/Diplom/math-system/plugins/";
    private Logger logger = LoggerFactory.getLogger(PluginController.class);

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

    @PostMapping
    public Algorithm addNewAlgorithm(@RequestParam("description") String description,
                                     @RequestParam("name") String name,
                                     @RequestParam("algType") String algType,
                                     @RequestParam("file") MultipartFile file) {

            if (!algorithmRepo.findAlgorithmByName(name).isEmpty()) {
                logger.error("Plugin already exist!!!");
                throw new PluginAlreadyExistsException();
            }



        Algorithm algorithm = null;

        try {
            File pluginsDir = new File(defaultDirForPlugin);
            if (!pluginsDir.exists()) {
                pluginsDir.mkdir();
                logger.info(pluginsDir.getAbsolutePath() + " has created");
            }
            File newJarFile = new File(defaultDirForPlugin + name + ".jar" );
            if (newJarFile.createNewFile()) {
                file.transferTo(newJarFile);
                algorithm = new Algorithm();
                algorithm.setName(name);
                algorithm.setDescription(description);
                algorithm.setAlgorithmType(AlgorithmType.valueOf(algType));
                verifyPlugin(algorithm, file.getOriginalFilename());
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
            logger.info(file.getName() + " deleted");
        } else {
            logger.error(file.getName() + " error");
        }
    }

    private void verifyPlugin(Algorithm algorithm, String jarName) {
        if (!(algorithm.getName() + ".jar").equals(jarName)) {
            logger.error("Plugin name doesn't equal file name");
        }
        if (algorithm.getAlgorithmType() == AlgorithmType.PROPERTY) {
            Plugin plugin =  PluginFactory.loadPlugin(algorithm.getName());
            if (! (plugin instanceof GraphProperty)) {
                logger.error("Wrong class type");
                deleteFileFromDisk(defaultDirForPlugin + algorithm.getName() + ".jar");
                throw new PluginCreatingException();
            }
        } else {
            Plugin plugin =  PluginFactory.loadPlugin(algorithm.getName());
            if (! (plugin instanceof GraphCharacteristic)) {
                logger.error("Wrong class type");
                deleteFileFromDisk(defaultDirForPlugin + algorithm.getName() + ".jar");
                throw new PluginCreatingException();
            }
        }
    }

}
