package com.mathsystem.domain.plugin;

import com.mathsystem.exceptions.PluginClassNotFoundException;
import com.mathsystem.exceptions.PluginNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLClassLoader;
import java.io.File;

/**
 * Клас фабрики для создания графов
 */
public class PluginFactory {
    private final static String pluginPath = "/home/dmitry/IdeaProjects/math-system/plugins/";
    private static Logger logger = LoggerFactory.getLogger(PluginFactory.class);

    /**
     * Статическая функция загружающая модуль
     * @param pluginName - имя модуля (без формата файла)
     * @return возващает тип Plugin - общий тип для GraphCharacteristic и GraphProperty
     */
    public static Plugin loadPlugin(String pluginName) {
        Plugin instance = null;
        File jarPlugin = new File(pluginPath + pluginName + ".jar");
        instance = loadPluginFromFile(jarPlugin, pluginName);

        return instance;
    }

    /**
     * Статическая функция загружающая модуль из конкретного файла
     * @param jarPlugin - объект файла с плагином
     * @param pluginName - имя модуля (без формата файла)
     * @return возващает тип Plugin - общий тип для GraphCharacteristic и GraphProperty
     */
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

        } catch (Exception classNotFoundException) {
            if (classNotFoundException instanceof ClassNotFoundException) {
                throw new PluginClassNotFoundException();
            }

        }

        return instance;
    }
}
