package com.mathsystem.repo;

import com.mathsystem.entity.task.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlgorithmRepo extends JpaRepository<Algorithm, Long> {
    public List<Algorithm> findAlgorithmByName(String name);
}