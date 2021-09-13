package com.mathsystem.plugin;

import com.mathsystem.entity.graph.Graph;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Objects;

@Component
public class PluginFactory {

    @Value("${upload.path}")
    private String pluginPath;

    public  Plugin loadPlugin() {

        System.out.println(new File(pluginPath));

//        Plugin instance = null;
//
//        File jarPlugin = Arrays.stream(Objects.requireNonNull(pluginDirectory.listFiles(
//                file -> {
//                    return file.isFile() && file.getName().equals(pluginName + ".jar");
//                }
//        ))).findFirst().orElseThrow(FileNotFoundException::new);
//
//        Class<?> pluginClass = null;
//        try {
//            URL jarURL = jarPlugin.toURI().toURL();
//            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
//            pluginClass = classLoader.loadClass(pluginName);
//        } catch (MalformedURLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            assert pluginClass != null;
//
//            instance = (Plugin) pluginClass.getDeclaredConstructor().newInstance();
//
//
//        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//
//        return instance;

        return null;
    }

}
