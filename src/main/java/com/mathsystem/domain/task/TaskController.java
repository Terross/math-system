package com.mathsystem.domain.task;

import com.mathsystem.domain.plugin.repository.PluginAnswerProjection;
import com.mathsystem.domain.plugin.repository.PluginRepository;
import com.mathsystem.domain.task.graph.repository.EdgeProjection;
import com.mathsystem.domain.task.graph.repository.GraphProjection;
import com.mathsystem.domain.task.graph.repository.VertexProjection;
import com.mathsystem.domain.task.repository.TaskProjection;
import com.mathsystem.domain.task.repository.TaskRepository;
import com.mathsystem.exceptions.TaskAlreadyExistsException;
import com.mathsystem.domain.plugin.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("task")
public class TaskController {
    private final PluginRepository pluginRepository;
    private final TaskRepository taskRepository;
    private final Validator validator;
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    public TaskController(PluginRepository pluginRepository, TaskRepository taskRepository, Validator validator) {
        this.pluginRepository = pluginRepository;
        this.taskRepository = taskRepository;
        this.validator = validator;
    }

    @PostMapping("verifyTask/{id}")
    public boolean verifyTask(@PathVariable UUID id,
                              @RequestBody GraphProjection graphProjection) throws FileNotFoundException, InterruptedException, ChangeSetPersister.NotFoundException {
        TaskProjection taskProjection = taskRepository.getById(id);
        return validator.verifyTask(taskProjection, graphProjection);
    }

    @PostMapping
    public TaskProjection addNewTask(@RequestBody TaskProjection taskProjection) throws ChangeSetPersister.NotFoundException {
        if (!taskRepository.findAllByName(taskProjection.getName()).isEmpty() ) {
            logger.error("Task already exist!!!");
            throw new TaskAlreadyExistsException();
        }
        List<PluginAnswerProjection> pluginAnswers = taskProjection.getPluginAnswers();
        GraphProjection graphProjection = taskProjection.getGraphProjection();
        if (graphProjection != null) {
            List<VertexProjection> vertexProjections = graphProjection.getVertexProjections();
            for (VertexProjection vertexProjection :
                    graphProjection.getVertexProjections()) {
                for (EdgeProjection edgeProjection :
                        vertexProjection.getIncomingEdgeProjections()) {
                    edgeProjection.setToVertexProjection(vertexProjection);
                    edgeProjection.setFromVertexProjection(vertexProjections.stream().filter(v -> v.getName().equals(edgeProjection.getFromV())).findFirst()
                            .orElseThrow(ChangeSetPersister.NotFoundException::new));
                }
                vertexProjection.setGraphProjection(graphProjection);
            }
        }

        for (PluginAnswerProjection pluginAnswerProjection : pluginAnswers) {
            pluginAnswerProjection.getPluginProjection().getPluginAnswerProjections().add(pluginAnswerProjection);
            pluginAnswerProjection.setTaskProjection(taskProjection);
        }
        taskRepository.save(taskProjection);
        logger.info(String.valueOf(taskProjection));
        return taskProjection;
    }

    @PostMapping("/answers")
    public List<Validator.PlgAns> getAnswers(@RequestBody GraphProjection graphProjection) throws ChangeSetPersister.NotFoundException {

        return validator
                .findAnswersForPlugins(
                        pluginRepository.findAllByGraphType(graphProjection.getGraphType()),
                        graphProjection
                );
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable UUID id) throws ChangeSetPersister.NotFoundException {
        TaskProjection taskProjection = taskRepository
                .findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        taskRepository.delete(taskProjection);
    }
}
