package com.mathsystem.domain.task;

import com.mathsystem.api.task.model.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/all/task/task")
    public ResponseEntity<?> saveNewTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.saveNativePlugin(task));
    }
//    private final Validator validator;

//    @PostMapping("/all/task/task")
//    public ResponseEntity<?> saveTask() {
//
//    }

//    @PostMapping("verifyTask/{id}")
//    public boolean verifyTask(@PathVariable UUID id,
//                              @RequestBody GraphProjection graphProjection) throws FileNotFoundException, InterruptedException, ChangeSetPersister.NotFoundException {
//        TaskProjection taskProjection = taskRepository.getById(id);
//        return validator.verifyTask(taskProjection, graphProjection);
//    }

//    @PostMapping
//    public TaskProjection addNewTask(@RequestBody TaskProjection taskProjection) throws ChangeSetPersister.NotFoundException {
//        if (!taskRepository.findAllByName(taskProjection.getName()).isEmpty() ) {
//            throw new TaskAlreadyExistsException();
//        }
//        List<PluginAnswerProjection> pluginAnswers = taskProjection.getPluginAnswers();
//        GraphProjection graphProjection = taskProjection.getGraphProjection();
//        if (graphProjection != null) {
//            List<VertexProjection> vertexProjections = graphProjection.getVertexProjections();
//            for (VertexProjection vertexProjection :
//                    graphProjection.getVertexProjections()) {
//                for (EdgeProjection edgeProjection :
//                        vertexProjection.getIncomingEdgeProjections()) {
//                    edgeProjection.setToVertexProjection(vertexProjection);
//                    edgeProjection.setFromVertexProjection(vertexProjections.stream().filter(v -> v.getName().equals(edgeProjection.getFromV())).findFirst()
//                            .orElseThrow(ChangeSetPersister.NotFoundException::new));
//                }
//                vertexProjection.setGraphProjection(graphProjection);
//            }
//        }
//
//        for (PluginAnswerProjection pluginAnswerProjection : pluginAnswers) {
//            pluginAnswerProjection.getPluginProjection().getPluginAnswerProjections().add(pluginAnswerProjection);
//            pluginAnswerProjection.setTaskProjection(taskProjection);
//        }
//        taskRepository.save(taskProjection);
//        return taskProjection;
//    }

//    @PostMapping("/answers")
//    public List<Validator.PlgAns> getAnswers(@RequestBody GraphProjection graphProjection) throws ChangeSetPersister.NotFoundException {
//
//        return validator
//                .findAnswersForPlugins(
//                        pluginRepository.findAllByGraphType(graphProjection.getGraphType()),
//                        graphProjection
//                );
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteTask(@PathVariable UUID id) throws ChangeSetPersister.NotFoundException {
//        TaskProjection taskProjection = taskRepository
//                .findById(id)
//                .orElseThrow(ChangeSetPersister.NotFoundException::new);
//        taskRepository.delete(taskProjection);
//    }
}
