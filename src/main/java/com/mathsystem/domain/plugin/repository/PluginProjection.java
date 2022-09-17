package com.mathsystem.domain.plugin.repository;

import com.mathsystem.domain.graph.repository.GraphType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@ToString
@Table(name = "plugin")
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private boolean verified;
}