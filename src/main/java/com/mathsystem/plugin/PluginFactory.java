package com.mathsystem.plugin;

import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.io.File;

public class PluginFactory {
    private final static String pluginPath = "/home/dmitry/plugins/";

    public static Plugin loadPlugin(String pluginName) {


        Plugin instance = null;

        try {
            File jarPlugin = new File(pluginPath + pluginName + ".jar");

            instance = loadPluginFromFile(jarPlugin, pluginName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return instance;
    }

    public static Plugin loadPluginFromFile(File jarPlugin, String pluginName) {
        Plugin instance = null;

        try {
            Class<?> pluginClass = null;
            URL jarURL = jarPlugin.toURI().toURL();
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
            pluginClass = classLoader.loadClass(pluginName);

            assert pluginClass != null;

            instance = (Plugin) pluginClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return instance;
    }
}
