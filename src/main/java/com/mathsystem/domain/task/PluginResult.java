package com.mathsystem.domain.task;

import com.mathsystem.domain.plugin.repository.PluginProjection;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PluginResult {

    private PluginProjection plugin;
    private String result;
}
