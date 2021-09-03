package com.example.math_system.repo;

import com.example.math_system.entity.task.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlgorithmRepo extends JpaRepository<Algorithm, Long> {
    public List<Algorithm> findAlgorithmByName(String name);
}
