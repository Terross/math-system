package com.mathsystem.domain.plugin;

import com.mathsystem.api.graph.mapper.GraphMapper;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.graph.repository.GraphProjection;
import com.mathsystem.domain.graph.repository.GraphRepository;
import com.mathsystem.domain.graph.repository.GraphType;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;
import com.mathsystem.domain.plugin.plugintype.Plugin;
import com.mathsystem.domain.plugin.repository.PluginProjection;
import com.mathsystem.domain.plugin.repository.PluginRepository;
import com.mathsystem.domain.plugin.repository.PluginType;
import com.mathsystem.exceptions.PluginCreatingException;
import com.mathsystem.exceptions.SqlConflictException;
import com.mathsystem.exceptions.SqlNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import static com.google.common.io.Files.getNameWithoutExtension;
import static com.mathsystem.exceptions.ErrorCode.PLUGIN_ALREADY_EXIST;
import static com.mathsystem.exceptions.ErrorCode.PLUGIN_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class PluginService {
    @Value("${plugin.native.path}")
    private String baseNativePluginPath;
    @Value("${plugin.path}")
    private String basePluginPath;

    private final PluginRepository pluginRepository;
    private final PluginRunner nativePluginService;
    private final GraphRepository graphRepository;
    private final GraphMapper graphMapper;
    private final PluginRunner externalPluginService;


    public PluginProjection saveNativePlugin(PluginProjection pluginProjection) {
        if (!pluginRepository.findAlgorithmByFileName(pluginProjection.getFileName()).isEmpty()) {
            throw new SqlConflictException(PLUGIN_ALREADY_EXIST.name(), PLUGIN_ALREADY_EXIST);
        }

        if (!getNativeFileNames().contains(pluginProjection.getFileName())) {
            throw new SqlNotFoundException(PLUGIN_NOT_FOUND.name(), PLUGIN_NOT_FOUND);
        }

        pluginProjection.setId(UUID.randomUUID());
        return pluginRepository.save(pluginProjection);
    }

    public List<?> getAllPugins() {
        return pluginRepository.findAll();
    }

    private List<String> getNativeFileNames() {
        return Arrays.stream(Objects.requireNonNull(new File(baseNativePluginPath).listFiles()))
                .map(File::getName)
                .toList();
    }

    public void deletePlugin(UUID id) {
        PluginProjection pluginProjection = pluginRepository.getById(id);
//        if (!pluginProjection.isNativeRealization()) {
            pluginRepository.delete(pluginProjection);
//        }
    }

    public PluginProjection updatePluginInfo(UUID id, PluginProjection pluginProjection) {
        PluginProjection plugin = pluginRepository.getById(id);
        plugin.setName(pluginProjection.getName());
        plugin.setDescription(pluginProjection.getDescription());
        return pluginRepository.save(plugin);
    }

    public String checkPlugin(UUID id, GraphProjection graphProjection) {
        PluginProjection plugin = pluginRepository
                .findById(id)
                .orElseThrow(null);

        String name = plugin.isNativeRealization()
                ? UPPER_CAMEL.to(LOWER_CAMEL, getNameWithoutExtension(plugin.getFileName()))
                : getNameWithoutExtension(plugin.getFileName());
        Graph graph = graphMapper.graphProjectionToGraph(graphProjection);
        return plugin.isNativeRealization()
                ? nativePluginService.runPlugin(name, graph)
                : externalPluginService.runPlugin(name, graph);
    }

    public PluginProjection saveExternalPlugin(String name, String description, String authorEmail, PluginType pluginType,
                                               GraphType graphType, MultipartFile file) throws IOException {
        PluginProjection pluginProjection = PluginProjection
                .builder()
                .pluginType(pluginType)
                .authorEmail(authorEmail)
                .description(description)
                .fileName(file.getOriginalFilename())
                .name(name)
                .graphType(graphType)
                .build();

        File jarFile = new File(basePluginPath + file.getOriginalFilename());
        if (jarFile.createNewFile()) {
            file.transferTo(jarFile);

            ArrayList<RuntimeException> exceptions = new ArrayList<>();
//            Thread.UncaughtExceptionHandler uncaughtExceptionHandler =
//                    (thread, throwable) -> exceptions.add((RuntimeException) throwable);
//            Thread verifyThread = new Thread(() -> {
//                verifyPlugin(finalPluginProjection, file.getOriginalFilename());
//            });
//            verifyThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
//            verifyThread.start();
//            long start = System.currentTimeMillis();
//            while (verifyThread.isAlive()) {
//                if (System.currentTimeMillis() - start > 3000) {
//                    deleteFileFromDisk(defaultDirForPlugin + pluginProjection.getFileName());
//                    throw new PluginTimeExecuteException();
//                }
//            }
//
//            for (RuntimeException exception: exceptions) {
//                if (exception instanceof SomethingWrongInPluginException) {
//                    deleteFileFromDisk(defaultDirForPlugin + file.getOriginalFilename());
//                    throw new SomethingWrongInPluginException(exception.getMessage());
//                }
//                if (exception instanceof PluginCreatingException) {
//                    deleteFileFromDisk(defaultDirForPlugin + file.getOriginalFilename());
//                    throw new PluginCreatingException();
//                }
//                if (exception instanceof PluginNotFoundException) {
//                    deleteFileFromDisk(defaultDirForPlugin + file.getOriginalFilename());
//                    throw new PluginNotFoundException();
//                }
//                if (exception instanceof PluginClassNotFoundException) {
//                    deleteFileFromDisk(defaultDirForPlugin + file.getOriginalFilename());
//                    throw new PluginClassNotFoundException();
//                }
//            }
            pluginRepository.save(pluginProjection);
        } else {
            throw new FileAlreadyExistsException("File already exist!");
        }
        return pluginProjection;
    }

//    private void verifyPlugin(PluginProjection algorithm, String jarName) {
//        int index = algorithm.getFileName().lastIndexOf('.');
//        String name  = algorithm.getFileName().substring(0, index);
//        Plugin plugin =  PluginFactory.loadPlugin(name);
//
//        if (algorithm.getPluginType() == PluginType.PROPERTY) {
//            if (! (plugin instanceof GraphProperty)) {
//                throw new PluginCreatingException();
//            } else {
//                runPluginForTest(algorithm, plugin, PluginType.PROPERTY);
//            }
//        } else {
//            if (! (plugin instanceof GraphCharacteristic)) {
//                throw new PluginCreatingException();
//            } else {
//                runPluginForTest(algorithm, plugin, PluginType.CHARACTERISTIC);
//            }
//        }
//    }

//    private void runPluginForTest(PluginProjection algorithm, Plugin plugin, PluginType pluginType) {
//        AbstractGraph abstractGraph = null;
//
//        switch (algorithm.getGraphType()) {
//            case DIRECTED:
//                abstractGraph = GraphFactory.loadDirectedGraphFromFile(new File(testGraphPath));
//                break;
//            case UNDIRECTED:
//                abstractGraph = GraphFactory.loadUndirectedGraphFromFile(new File(testGraphPath));
//                break;
//        }
//        try {
//            switch (pluginType) {
//                case CHARACTERISTIC:
//                    ((GraphCharacteristic) plugin).execute(abstractGraph);
//                    break;
//                case PROPERTY:
//                    ((GraphProperty) plugin).execute(abstractGraph);
//                    break;
//            }
//        } catch (Exception e) {
//            StringWriter sw = new StringWriter();
//            e.printStackTrace(new PrintWriter(sw));
//            throw new SomethingWrongInPluginException(sw.toString());
//        }
//
//    }
}
