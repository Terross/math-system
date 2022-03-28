package com.mathsystem.domain.plugin.repository;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "plugin_id")
    private PluginProjection plugin;
}
