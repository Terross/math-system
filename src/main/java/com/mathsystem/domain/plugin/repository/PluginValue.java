package com.mathsystem.domain.plugin.repository;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class PluginValue {
    @Id
    @GeneratedValue
    private UUID id;

    private String value;

    @ManyToOne
    @JoinColumn(name = "plugin_id")
    private PluginProjection plugin;
}
