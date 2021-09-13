package com.mathsystem.plugin;

import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.io.File;

public class PluginFactory {
    private final static String pluginPath = "/home/dmitry/plugins/";

    public static Plugin loadPlugin(String pluginName) throws FileNotFoundException {

        Plugin instance = null;
        try {
            File jarPlugin = new File(pluginPath + pluginName + ".jar");

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
