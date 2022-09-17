package com.mathsystem.domain.plugin;

import com.mathsystem.api.graph.GraphFactory;
import com.mathsystem.api.graph.mapper.GraphMapper;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.graph.repository.GraphProjection;
import com.mathsystem.domain.graph.repository.GraphRepository;
import com.mathsystem.domain.graph.repository.GraphType;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;
import com.mathsystem.domain.plugin.plugintype.Plugin;
import com.mathsystem.domain.plugin.repository.PluginProjection;
import com.mathsystem.domain.plugin.repository.PluginRepository;
import com.mathsystem.domain.plugin.repository.PluginType;
import com.mathsystem.exceptions.BusinessException;
import com.mathsystem.exceptions.DataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import static com.google.common.io.Files.getNameWithoutExtension;
import static com.mathsystem.api.graph.GraphFactory.loadGraphFromFile;
import static com.mathsystem.domain.plugin.repository.PluginType.CHARACTERISTIC;
import static com.mathsystem.domain.plugin.repository.PluginType.PROPERTY;
import static com.mathsystem.exceptions.ErrorCode.*;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@Slf4j
@Service
@RequiredArgsConstructor
public class PluginService {
    @Value("${plugin.native.path}")
    private String baseNativePluginPath;

    @Value("${plugin.path}")
    private String basePluginPath;

    @Value("${plugin.test.path}")
    private String testGraphPath;

    private final PluginRepository pluginRepository;
    private final PluginRunner nativePluginService;
    private final GraphRepository graphRepository;
    private final GraphMapper graphMapper;
    private final PluginRunner externalPluginService;
    private final PluginFactory pluginFactory;


    public PluginProjection saveNativePlugin(PluginProjection pluginProjection) {
        if (!pluginRepository.findAlgorithmByFileName(pluginProjection.getFileName()).isEmpty()) {
            throw new DataException(PLUGIN_ALREADY_EXIST, PLUGIN_ALREADY_EXIST.name());
        }

        if (!getNativeFileNames().contains(pluginProjection.getFileName())) {
            throw new DataException(PLUGIN_NOT_FOUND, PLUGIN_NOT_FOUND.name());
        }

        pluginProjection.setId(UUID.randomUUID());
        return pluginRepository.save(pluginProjection);
    }

    public List<?> getAllPlugins() {
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
            deleteFileFromDisk(pluginProjection.getFileName());
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
                .orElseThrow(() -> new DataException(PLUGIN_NOT_FOUND, PLUGIN_NOT_FOUND.name()));

        String name = getPluginName(plugin);
        Graph graph = graphMapper.graphProjectionToGraph(graphProjection);

        return plugin.isNativeRealization()
                ? nativePluginService.runPlugin(name, graph)
                : externalPluginService.runPlugin(name, graph);
    }

    public PluginProjection saveExternalPlugin(String name, String description, String authorEmail,
                                               GraphType graphType, MultipartFile file) throws IOException {

        if (!pluginRepository.findAlgorithmByFileName(name).isEmpty()) {
            throw new DataException(PLUGIN_ALREADY_EXIST, PLUGIN_ALREADY_EXIST.name());
        }

        PluginProjection pluginProjection = PluginProjection.builder()
                .authorEmail(authorEmail).description(description).fileName(file.getOriginalFilename())
                .name(name).graphType(graphType).build();

        File jarFile = new File(basePluginPath + file.getOriginalFilename());
        log.info("%s has been created".formatted(basePluginPath + file.getOriginalFilename()));
        if (jarFile.createNewFile()) {
            file.transferTo(jarFile);

            ArrayList<RuntimeException> exceptions = new ArrayList<>();

            Thread.UncaughtExceptionHandler uncaughtExceptionHandler =
                    (thread, throwable) -> exceptions.add((RuntimeException) throwable);
            Thread verifyThread = new Thread(() -> {
                verifyPlugin(pluginProjection, new File(testGraphPath));
            });
            verifyThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
            verifyThread.start();

            long start = System.currentTimeMillis();
            log.info("Verifying has been started");
            while (verifyThread.isAlive()) {
                if (System.currentTimeMillis() - start > 3000) {
                    log.info("Too long");
                    deleteFileFromDisk(pluginProjection.getFileName());
                    throw new BusinessException(PLUGIN_TIME_LIMIT, PLUGIN_TIME_LIMIT.name());
                }
            }

            pluginExceptionHandler(exceptions, pluginProjection.getFileName());
            pluginProjection.setPluginType(calculatePluginType(getPluginName(pluginProjection)));
            pluginRepository.save(pluginProjection);
        } else {
            throw new DataException(PLUGIN_JAR_ALREADY_EXIST, "%s already exist".formatted(jarFile.getAbsolutePath()));
        }
        return pluginProjection;
    }

    private void deleteFileFromDisk(String fileName) {
        File targetFile = new File(basePluginPath + fileName);
        if (targetFile.delete()) {
            log.info("File %s has been deleted".formatted(targetFile.getAbsolutePath()));
        }
    }

    private String getPluginName(PluginProjection plugin) {
        return  plugin.isNativeRealization()
                ? UPPER_CAMEL.to(LOWER_CAMEL, getNameWithoutExtension(plugin.getFileName()))
                : getNameWithoutExtension(plugin.getFileName());
    }

    private void pluginExceptionHandler(List<RuntimeException> exceptions, String fileName) {
        for (RuntimeException exception: exceptions) {
            deleteFileFromDisk(fileName);
            switch (exception) {
                case BusinessException businessException ->
                        throw new BusinessException(businessException.getErrorCode(), businessException.getMessage());
                case DataException dataException ->
                        throw new DataException(dataException.getErrorCode(), dataException.getMessage());
                default -> {
                    throw new BusinessException(PLUGIN_INTERNAL_ERROR, getStackTrace(exception));
                }
            }
        }
    }

    private void verifyPlugin(PluginProjection pluginProjection, File file) {
        String name  = getPluginName(pluginProjection);
        try {
            log.info(file.getAbsolutePath());
            externalPluginService.runPlugin(name, loadGraphFromFile(file));
        } catch (FileNotFoundException e) {
            throw new BusinessException(PLUGIN_JAR_NOT_FOUND, PLUGIN_JAR_NOT_FOUND.name());
        }
    }

    private PluginType calculatePluginType(String name) {
        return switch (pluginFactory.loadPlugin(name)) {
            case GraphCharacteristic characteristic -> CHARACTERISTIC;
            case GraphProperty property -> PROPERTY;
            default -> throw new IllegalArgumentException();
        };
    }

    public PluginProjection verifyPlugin(UUID id) {
        var plugin = pluginRepository.getById(id);
        plugin.setVerified(true);
        pluginRepository.save(plugin);
        return plugin;
    }
}
