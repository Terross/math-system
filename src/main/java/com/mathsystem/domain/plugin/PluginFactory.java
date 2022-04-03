package com.mathsystem.domain.plugin;

import com.mathsystem.domain.plugin.plugintype.Plugin;
import com.mathsystem.exceptions.BusinessException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import static com.mathsystem.exceptions.ErrorCode.PLUGIN_CLASS_NOT_FOUND;
import static com.mathsystem.exceptions.ErrorCode.PLUGIN_JAR_NOT_FOUND;

/**
 * Клас фабрики для создания графов
 */
@Slf4j
@Data
@Component
public class PluginFactory {

    @Value("${plugin.path}")
    private String pluginPath;

    /**
     * Статическая функция загружающая модуль
     * @param pluginName - имя модуля (без формата файла)
     * @return возващает тип Plugin - общий тип для GraphCharacteristic и GraphProperty
     */
    public Plugin loadPlugin(String pluginName) {
        File jarPlugin = new File(pluginPath + pluginName + ".jar");
        log.info("%s jar file".formatted(jarPlugin.getAbsolutePath()));
        return  loadPluginFromFile(jarPlugin, pluginName);
    }

    /**
     * Статическая функция загружающая модуль из конкретного файла
     * @param jarPlugin - объект файла с плагином
     * @param pluginName - имя модуля (без формата файла)
     * @return возващает тип Plugin - общий тип для GraphCharacteristic и GraphProperty
     */
    public Plugin loadPluginFromFile(File jarPlugin, String pluginName) {

        Plugin instance = null;
        if (jarPlugin.exists()) {
            log.info(jarPlugin.getAbsolutePath() + " was found");
        } else {
            log.error(jarPlugin.getAbsolutePath() + " was found");
            throw new BusinessException(PLUGIN_JAR_NOT_FOUND, PLUGIN_JAR_NOT_FOUND.name());
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
                throw new BusinessException(PLUGIN_CLASS_NOT_FOUND, PLUGIN_CLASS_NOT_FOUND.name());
            }
        }

        return instance;
    }
}
