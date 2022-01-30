package com.mathsystem.entity.task;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@RequiredArgsConstructor
public class PluginAnswer {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "plugin_id")
    private PluginProjection pluginProjection;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private PluginType pluginType;

    private String answer;
}
