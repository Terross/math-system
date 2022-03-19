package com.mathsystem.domain.plugin.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mathsystem.domain.graph.repository.GraphType;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@ToString
public class PluginProjection {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String description;

    private PluginType pluginType;

    private GraphType graphType;

    private String fileName;

    private boolean nativeRealization;

    private String authorEmail;

    @JsonIgnore
    @OneToMany(mappedBy = "pluginProjection", cascade = CascadeType.ALL)
    private List<PluginAnswerProjection> pluginAnswerProjections = new ArrayList<>();
}