package com.mathsystem.domain.plugin;


import com.mathsystem.domain.graph.repository.GraphProjection;
import com.mathsystem.domain.plugin.repository.PluginProjection;
import com.mathsystem.domain.plugin.repository.PluginRepository;
import com.mathsystem.domain.plugin.repository.PluginType;
import com.mathsystem.domain.graph.repository.GraphRepository;
import com.mathsystem.domain.graph.repository.GraphType;
import com.mathsystem.exceptions.*;
import com.mathsystem.lib.graphapi.AbstractGraph;
import com.mathsystem.lib.graphapi.GraphFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.UUID;

import static com.mathsystem.exceptions.ErrorCode.PLUGIN_ALREADY_EXIST;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PluginController {
    @Value("${plugin.path}")
    private String defaultDirForPlugin;
    @Value("${plugin.test.path}")
    private String testGraphPath;

    private final PluginRepository pluginRepository;
    private final GraphRepository graphRepository;
    private final PluginService pluginService;


    @PostMapping("/all/plugin/native-plugin")
    public ResponseEntity<?> saveNativePlugin(@RequestBody PluginProjection pluginProjection) {
        return ResponseEntity.ok(pluginService.saveNativePlugin(pluginProjection));
    }

    @GetMapping("/all/plugin/plugins")
    public ResponseEntity<?> getAllPlugins() {
        return ResponseEntity.ok(pluginService.getAllPugins());
    }

    @DeleteMapping("/all/plugin/plugin/{id}")
    public ResponseEntity<?> deletePlugin(@PathVariable("id") UUID id) {
        pluginService.deletePlugin(id);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/all/plugin/plugin/{id}")
    public ResponseEntity<?> updatePlugin(@PathVariable("id") UUID id, @RequestBody PluginProjection pluginProjection) {
        return ResponseEntity.ok(pluginService.updatePluginInfo(id, pluginProjection));
    }

    @PostMapping("/all/plugin/chech-plugin/{id}")
    public ResponseEntity<?> checkPlugin(@PathVariable("id") UUID id, @RequestBody GraphProjection graphProjection) {
        return ResponseEntity.ok(pluginService.checkPlugin(id, graphProjection));
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
            throw new SqlConflictException(PLUGIN_ALREADY_EXIST.name(), PLUGIN_ALREADY_EXIST);
        }
        if (!pluginRepository.findAlgorithmByName(name).isEmpty()) {
            throw new SqlConflictException(PLUGIN_ALREADY_EXIST.name(), PLUGIN_ALREADY_EXIST);
        }
        PluginProjection pluginProjection = null;

            File pluginsDir = new File(defaultDirForPlugin);
            if (!pluginsDir.exists()) {
                pluginsDir.mkdir();
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
        } else {
        }
    }

    private void verifyPlugin(PluginProjection algorithm, String jarName) {
        int index = algorithm.getFileName().lastIndexOf('.');
        String name  = algorithm.getFileName().substring(0, index);
        Plugin plugin =  PluginFactory.loadPlugin(name);

        if (algorithm.getPluginType() == PluginType.PROPERTY) {
            if (! (plugin instanceof GraphProperty)) {
                throw new PluginCreatingException();
            } else {
                runPluginForTest(algorithm, plugin, PluginType.PROPERTY);
            }
        } else {
            if (! (plugin instanceof GraphCharacteristic)) {
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
