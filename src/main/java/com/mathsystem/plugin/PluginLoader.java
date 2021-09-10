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
    private final File pluginDirectory = new File("/home/dmitry/IdeaProjects/PluginDir");
    public PluginLoader() {
    }
    public Plugin loadPlugin(String pluginName) throws FileNotFoundException {

        Plugin instance = null;

        File jarPlugin = Arrays.stream(Objects.requireNonNull(pluginDirectory.listFiles(
                file -> {
                    return file.isFile() && file.getName().equals(pluginName + ".jar");
                }
        ))).findFirst().orElseThrow(FileNotFoundException::new);

        Class<?> pluginClass = null;
        try {
            URL jarURL = jarPlugin.toURI().toURL();
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
            pluginClass = classLoader.loadClass(pluginName);
        } catch (MalformedURLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            assert pluginClass != null;
            instance = (Plugin) pluginClass.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return instance;
    }
}
