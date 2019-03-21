package com.camunda.consulting.examples.springboot.suspendjobdefinition;

import java.util.Arrays;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.incident.IncidentHandler;
import org.camunda.bpm.spring.boot.starter.configuration.Ordering;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordering.DEFAULT_ORDER + 1)
public class SuspendingIncidentEnginePlugin implements ProcessEnginePlugin {

  @Override
  public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
  }

  @Override
  public void postProcessEngineBuild(ProcessEngine arg0) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
    // TODO Auto-generated method stub
    
    
    RuntimeService runtimeService = processEngineConfiguration.getRuntimeService();
    ManagementService managementService = processEngineConfiguration.getManagementService();
    
    SuspendingIncidentHandler incidentHandler = new SuspendingIncidentHandler(runtimeService, managementService);
    
    processEngineConfiguration.setCustomIncidentHandlers(Arrays.asList((IncidentHandler) incidentHandler));
  }

  
}
