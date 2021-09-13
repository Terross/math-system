package com.mathsystem.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class PluginLoaderFactory {

    private static final Logger logger = LoggerFactory.getLogger(PluginLoaderFactory.class);

    @Value("${upload.path}")
    private static String pluginsPath;

    public static PluginLoader createPluginLoader() {
        File pluginDir = new File(pluginsPath);
        if (!pluginDir.exists()) {
            logger.info("Directory " + pluginsPath + " has created");
            pluginDir.mkdir();
        }
        return new PluginLoader(new File(pluginsPath));
    }
}
