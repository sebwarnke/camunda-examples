package com.camunda.example.tasklistplugin;

import org.camunda.bpm.tasklist.resource.AbstractTasklistPluginRootResource;

import javax.ws.rs.Path;

@Path("plugin/" + MyPlugin.ID)
public class MyPluginRootResource extends AbstractTasklistPluginRootResource {

    public MyPluginRootResource() {
        super(MyPlugin.ID);
    }
}
