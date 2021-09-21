package com.mathsystem.plugin;

import com.mathsystem.exceptions.PluginClassNotFoundException;
import com.mathsystem.exceptions.PluginNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLClassLoader;
import java.io.File;

public class PluginFactory {
    private final static String pluginPath = "/home/dmitry/Documents/Diplom/math-system/plugins/";
    private static Logger logger = LoggerFactory.getLogger(PluginFactory.class);

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
        if (jarPlugin.exists()) {
            logger.info(jarPlugin.getAbsolutePath() + " was found");
        } else {
            logger.error(jarPlugin.getAbsolutePath() + " was found");
            throw new PluginNotFoundException();
        }

        try {
            Class<?> pluginClass = null;
            URL jarURL = jarPlugin.toURI().toURL();
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});

            pluginClass = classLoader.loadClass(pluginName);

            assert pluginClass != null;
            instance = (Plugin) pluginClass.getDeclaredConstructor().newInstance();

        } catch (ClassNotFoundException classNotFoundException) {
            throw new PluginClassNotFoundException();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return instance;
    }
}
