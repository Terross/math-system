package com.mathsystem.domain.plugin;


import com.mathsystem.domain.plugin.repository.PluginProjection;
import com.mathsystem.domain.plugin.repository.PluginRepository;
import com.mathsystem.domain.plugin.repository.PluginType;
import com.mathsystem.domain.task.graph.repository.GraphRepository;
import com.mathsystem.domain.task.graph.repository.GraphType;
import com.mathsystem.exceptions.*;


import com.mathsystem.lib.graphapi.AbstractGraph;
import com.mathsystem.lib.graphapi.GraphFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("plugin/api")
public class PluginController {
    private final PluginRepository pluginRepository;
    private final GraphRepository graphRepository;
    private final String defaultDirForPlugin =
            "/home/dmitry/IdeaProjects/math-system/plugins/";
    private final String testGraphPath = "/home/dmitry/IdeaProjects/math-system/dg.txt";
    private final Logger logger = LoggerFactory.getLogger(PluginController.class);

    @Autowired
    public PluginController(PluginRepository pluginRepository, GraphRepository graphRepository) {
        this.pluginRepository = pluginRepository;
        this.graphRepository = graphRepository;
    }

    @ExceptionHandler(SomethingWrongInPluginException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> somethingWrongException(SomethingWrongInPluginException somethingWrongInPluginException) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .header("SomethingWrong")
                .body(somethingWrongInPluginException.getMessage());
    }

    //TO-DO: Переписать
    @PostMapping
    public PluginProjection addNewAlgorithm(@RequestParam("description") String description,
                                            @RequestParam("name") String name,
                                            @RequestParam("pluginType") PluginType pluginType,
                                            @RequestParam("graphType") GraphType graphType,
                                            @RequestParam("file") MultipartFile file)
            throws IOException, InterruptedException {
        if (!pluginRepository.findAlgorithmByFileName(file.getOriginalFilename()).isEmpty()) {
            logger.error("Plugin already exist!!!");
            throw new PluginAlreadyExistsException();
        }
        if (!pluginRepository.findAlgorithmByName(name).isEmpty()) {
            logger.error("Plugin already exist!!!");
            throw new PluginAlreadyExistsException();
        }
        PluginProjection pluginProjection = null;
        logger.info(file.getOriginalFilename());

            File pluginsDir = new File(defaultDirForPlugin);
            if (!pluginsDir.exists()) {
                pluginsDir.mkdir();
                logger.info(pluginsDir.getAbsolutePath() + " has created");
            }
            File newJarFile = new File(defaultDirForPlugin + file.getOriginalFilename() );
            if (newJarFile.createNewFile()) {
                file.transferTo(newJarFile);
                pluginProjection = new PluginProjection();
                pluginProjection.setName(name);
                pluginProjection.setDescription(description);
                pluginProjection.setPluginType(pluginType);
                pluginProjection.setGraphType(graphType);
                pluginProjection.setFileName(file.getOriginalFilename());
                PluginProjection finalPluginProjection = pluginProjection;
                ArrayList<RuntimeException> exceptions = new ArrayList<>();
                Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread thread, Throwable throwable) {
                        exceptions.add((RuntimeException) throwable);
                    }
                };
                Thread verifyThread = new Thread(() -> {
                    verifyPlugin(finalPluginProjection, file.getOriginalFilename());
                });
                verifyThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
                verifyThread.start();
                long start = System.currentTimeMillis();
                while (verifyThread.isAlive()) {
                    if (System.currentTimeMillis() - start > 3000) {
                        logger.error("time execute exception");
                        deleteFileFromDisk(defaultDirForPlugin + pluginProjection.getFileName());
                        throw new PluginTimeExecuteException();
                    }
                }

                for (RuntimeException exception: exceptions) {
                    if (exception instanceof SomethingWrongInPluginException) {
                        deleteFileFromDisk(defaultDirForPlugin + file.getOriginalFilename());
                        throw new SomethingWrongInPluginException(exception.getMessage());
                    }
                    if (exception instanceof PluginCreatingException) {
                        deleteFileFromDisk(defaultDirForPlugin + file.getOriginalFilename());
                        throw new PluginCreatingException();
                    }
                    if (exception instanceof PluginNotFoundException) {
                        deleteFileFromDisk(defaultDirForPlugin + file.getOriginalFilename());
                        throw new PluginNotFoundException();
                    }
                    if (exception instanceof PluginClassNotFoundException) {
                        deleteFileFromDisk(defaultDirForPlugin + file.getOriginalFilename());
                        throw new PluginClassNotFoundException();
                    }
                }
                pluginRepository.save(pluginProjection);
            } else {
                throw new FileAlreadyExistsException("File already exist!");
            }
        return pluginProjection;
    }

    @DeleteMapping("/{id}")
    public void deleteAlgorithm(@PathVariable UUID id) {
        PluginProjection pluginProjection = pluginRepository.findById(id)
                .orElseThrow(PluginNotFoundException::new);
        deleteFileFromDisk(defaultDirForPlugin + pluginProjection.getFileName());
        pluginRepository.delete(pluginProjection);
    }

    @PutMapping("/{id}")
    public PluginProjection updateAlgorithm(@PathVariable UUID id,
                                            @RequestParam("description") String description,
                                            @RequestParam("name") String name,
                                            @RequestParam("file") MultipartFile file) {

        PluginProjection pluginProjection = pluginRepository.findById(id)
                .orElseThrow(PluginNotFoundException::new);
        deleteFileFromDisk(defaultDirForPlugin + pluginProjection.getName() + ".jar");

        pluginProjection.setDescription(description);
        pluginProjection.setName(name);
        try {
            File newJarFile = new File(defaultDirForPlugin + name + ".jar" );
            if (newJarFile.createNewFile()) {
                file.transferTo(newJarFile);
                pluginRepository.save(pluginProjection);
            } else {
                System.out.println("File exist!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pluginProjection;
    }

    private void deleteFileFromDisk(String fileName) {
        System.out.println();
        File file = new File(fileName);
        if (file.delete()) {
            logger.info(file.getName() + " deleted");
        } else {
            logger.error(file.getName() + " error");
        }
    }

    private void verifyPlugin(PluginProjection algorithm, String jarName) {
        int index = algorithm.getFileName().lastIndexOf('.');
        String name  = algorithm.getFileName().substring(0, index);
        Plugin plugin =  PluginFactory.loadPlugin(name);

        if (algorithm.getPluginType() == PluginType.PROPERTY) {
            if (! (plugin instanceof GraphProperty)) {
                logger.error("Wrong class type");
                throw new PluginCreatingException();
            } else {
                runPluginForTest(algorithm, plugin, PluginType.PROPERTY);
            }
        } else {
            if (! (plugin instanceof GraphCharacteristic)) {
                logger.error("Wrong class type");
                throw new PluginCreatingException();
            } else {
               runPluginForTest(algorithm, plugin, PluginType.CHARACTERISTIC);
            }
        }
    }

    private void runPluginForTest(PluginProjection algorithm, Plugin plugin, PluginType pluginType) {
        AbstractGraph abstractGraph = null;

            switch (algorithm.getGraphType()) {
                case DIRECTED:
                    abstractGraph = GraphFactory.loadDirectedGraphFromFile(new File(testGraphPath));
                    break;
                case UNDIRECTED:
                    abstractGraph = GraphFactory.loadUndirectedGraphFromFile(new File(testGraphPath));
                    break;
            }
        try {
            switch (pluginType) {
                case CHARACTERISTIC:
                    ((GraphCharacteristic) plugin).execute(abstractGraph);
                    break;
                case PROPERTY:
                    ((GraphProperty) plugin).execute(abstractGraph);
                    break;
            }
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            throw new SomethingWrongInPluginException(sw.toString());
        }

    }
}
