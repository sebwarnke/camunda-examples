package com.camunda.example.tasklistplugin;

import org.camunda.bpm.tasklist.plugin.spi.impl.AbstractTasklistPlugin;

import java.util.HashSet;
import java.util.Set;

public class MyPlugin extends AbstractTasklistPlugin {

    public static final String ID = "sample-plugin";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Set<Class<?>> getResourceClasses() {
        Set<Class<?>> classes = new HashSet<>();

        classes.add(MyPluginRootResource.class);

        return classes;
    }


}
