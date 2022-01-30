package com.mathsystem.repo;

import com.mathsystem.domain.task.graph.repo.GraphType;
import com.mathsystem.entity.task.PluginProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlgorithmRepo extends JpaRepository<PluginProjection, Long> {
    public List<PluginProjection> findAlgorithmByName(String name);
    public List<PluginProjection> findAlgorithmByFileName(String fileName);
    public List<PluginProjection> findAllByGraphType(GraphType graphType);
}
