package com.mathsystem.domain.plugin;

import com.mathsystem.api.graph.mapper.GraphMapper;
import com.mathsystem.api.graph.mapper.UndirectedGraphCreator;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.graph.repository.GraphProjection;
import com.mathsystem.domain.graph.repository.GraphRepository;
import com.mathsystem.domain.plugin.nativerealization.NativePluginService;
import com.mathsystem.domain.plugin.repository.PluginProjection;
import com.mathsystem.domain.plugin.repository.PluginRepository;
import com.mathsystem.exceptions.SqlConflictException;
import com.mathsystem.exceptions.SqlNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
    private final PluginRepository pluginRepository;
    private final NativePluginService nativePluginService;
    private final UndirectedGraphCreator undirectedGraphCreator;
    private final GraphRepository graphRepository;
    private final GraphMapper graphMapper;

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

    public String checkPlugin(UUID id, Graph graph) {
        PluginProjection plugin = pluginRepository
                .findById(id)
                .orElseThrow(null);
        String name = UPPER_CAMEL.to(LOWER_CAMEL, getNameWithoutExtension(plugin.getFileName()));
        return nativePluginService.runPlugin(name, graph);
    }
}
