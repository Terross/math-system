package com.mathsystem.domain.plugin;


import com.mathsystem.api.graph.mapper.GraphMapper;
import com.mathsystem.domain.graph.repository.GraphProjection;
import com.mathsystem.domain.graph.repository.GraphRepository;
import com.mathsystem.domain.graph.repository.GraphType;
import com.mathsystem.domain.plugin.repository.PluginProjection;
import com.mathsystem.domain.plugin.repository.PluginRepository;
import com.mathsystem.domain.plugin.repository.PluginType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PluginController {

    @Value("${plugin.path}")
    private String defaultDirForPlugin;

    @Value("${plugin.test.path}")
    private String testGraphPath;

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

    @PostMapping("/all/plugin/external-plugin")
    public ResponseEntity<?> saveExternalPlugin(@RequestParam("description") String description,
                                                @RequestParam("name") String name,
                                                @RequestParam("author") String author,
                                                @RequestParam("pluginType") PluginType pluginType,
                                                @RequestParam("graphType") GraphType graphType,
                                                @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(pluginService.saveExternalPlugin(name, description, author, pluginType, graphType, file));
    }
}
