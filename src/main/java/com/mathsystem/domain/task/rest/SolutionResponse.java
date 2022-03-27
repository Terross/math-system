package com.mathsystem.domain.task.rest;

import com.mathsystem.domain.task.PluginResult;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SolutionResponse {
    private Boolean right = true;
    private List<PluginResult> pluginResultList = new ArrayList<>();
}
