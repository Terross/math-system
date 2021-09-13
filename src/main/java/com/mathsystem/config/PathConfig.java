package com.mathsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PathConfig {

    @Value("${upload.path}")
    private String pluginPath;

    public String getPluginPath() {
        return pluginPath;
    }
}
