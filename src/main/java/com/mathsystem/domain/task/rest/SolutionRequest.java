package com.mathsystem.domain.task.rest;

import com.mathsystem.domain.graph.repository.GraphProjection;
import lombok.Data;

@Data
public class SolutionRequest {

    private GraphProjection graphProjection;
    private String email;
}
