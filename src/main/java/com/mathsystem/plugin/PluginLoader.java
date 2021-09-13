package com.mathsystem.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Objects;

public class PluginLoader {

    private final File pluginDirectory;

    public PluginLoader(File pluginDirectory) {
        this.pluginDirectory = pluginDirectory;
    }


    public Plugin loadPlugin(String pluginName) throws FileNotFoundException {

        return null;
    }
}
