package com.mathsystem.domain.plugin.nativerealization;

import com.mathsystem.api.graph.GraphFactory;
import com.mathsystem.api.graph.model.Graph;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;

import static com.mathsystem.api.graph.GraphFactory.loadGraphFromFile;

@RestController
@RequiredArgsConstructor
public class NativePluginTestController {

    private final NativePluginService nativePluginService;
    @Value("${plugin.test.path}")
    private String testGraphPath;

    @PostMapping("/nativeTest/{name}")
    public String getAnswerForTestGraph(@PathVariable("name") String pluginName) throws FileNotFoundException {
        Graph graph = loadGraphFromFile(new File(testGraphPath));
        return nativePluginService.runPlugin(pluginName, graph);
    }
}
