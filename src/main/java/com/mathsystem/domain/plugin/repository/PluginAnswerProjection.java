package com.mathsystem.domain.plugin.repository;

import com.mathsystem.domain.task.repository.TaskProjection;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@RequiredArgsConstructor
public class PluginAnswerProjection {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "plugin_id")
    private PluginProjection pluginProjection;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private TaskProjection taskProjection;

    private PluginType pluginType;

    private String answer;
}
