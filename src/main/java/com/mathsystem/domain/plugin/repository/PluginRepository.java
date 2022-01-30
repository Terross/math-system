package com.mathsystem.domain.plugin.repository;

import com.mathsystem.domain.task.graph.repository.GraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PluginRepository extends JpaRepository<PluginProjection, UUID> {
    public List<PluginProjection> findAlgorithmByName(String name);
    public List<PluginProjection> findAlgorithmByFileName(String fileName);
    public List<PluginProjection> findAllByGraphType(GraphType graphType);
}
