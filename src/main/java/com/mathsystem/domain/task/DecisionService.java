package com.mathsystem.domain.task;

import com.mathsystem.api.graph.mapper.GraphMapper;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.graph.repository.GraphProjection;
import com.mathsystem.domain.plugin.ExternalPluginService;
import com.mathsystem.domain.plugin.nativerealization.NativePluginService;
import com.mathsystem.domain.plugin.repository.PluginProjection;
import com.mathsystem.domain.task.repository.Task;
import com.mathsystem.domain.task.repository.TaskDecision;
import com.mathsystem.domain.task.repository.TaskDecisionRepository;
import com.mathsystem.domain.task.repository.TaskRepository;
import com.mathsystem.domain.task.rest.SolutionRequest;
import com.mathsystem.domain.task.rest.SolutionResponse;
import com.mathsystem.domain.user.repository.User;
import com.mathsystem.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import static com.google.common.io.Files.getNameWithoutExtension;

@Service
@RequiredArgsConstructor
public class DecisionService {

    private final TaskDecisionRepository taskDecisionRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final GraphMapper graphMapper;
    private final NativePluginService nativePluginService;
    private final ExternalPluginService externalPluginService;

    public SolutionResponse checkSolution(SolutionRequest solutionRequest, UUID id) {
        Task task = taskRepository.findById(id).orElseThrow();
        Graph graph = graphMapper.graphProjectionToGraph(solutionRequest.getGraphProjection());
        SolutionResponse solutionResponse = new SolutionResponse();
        task.getPluginValues().forEach(pluginValue -> {
            PluginProjection plugin = pluginValue.getPlugin();
            String name = getPluginName(plugin);
            String pluginAnswer =  plugin.isNativeRealization()
                    ? nativePluginService.runPlugin(name, graph)
                    : externalPluginService.runPlugin(name, graph);

            if (!pluginAnswer.equals(pluginValue.getValue())) {
                solutionResponse.setRight(false);
                solutionResponse.getPluginResultList().add(new PluginResult(plugin, pluginAnswer));
            }
        });

        savePluginDecision(solutionRequest, task, solutionResponse.getRight());
        return solutionResponse;
    }

    private void savePluginDecision(SolutionRequest solutionRequest, Task task, Boolean isRight) {
        User user = userRepository.findByEmail(solutionRequest.getEmail());
        GraphProjection graph = solutionRequest.getGraphProjection();
        graph.prepareToSave();
        TaskDecision taskDecision = TaskDecision.builder()
                .task(task)
                .created(LocalDateTime.now())
                .graphProjection(graph)
                .user(user)
                .isRight(isRight)
                .build();
        taskDecision.getGraphProjection().prepareToSave();
        taskDecisionRepository.save(taskDecision);
    }

    private String getPluginName(PluginProjection plugin) {
        return  plugin.isNativeRealization()
                ? UPPER_CAMEL.to(LOWER_CAMEL, getNameWithoutExtension(plugin.getFileName()))
                : getNameWithoutExtension(plugin.getFileName());
    }
}
