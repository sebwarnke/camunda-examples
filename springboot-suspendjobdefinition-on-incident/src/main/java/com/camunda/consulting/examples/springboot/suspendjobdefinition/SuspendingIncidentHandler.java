package com.camunda.consulting.examples.springboot.suspendjobdefinition;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.incident.DefaultIncidentHandler;
import org.camunda.bpm.engine.impl.incident.IncidentContext;
import org.camunda.bpm.engine.impl.persistence.entity.IncidentEntity;
import org.camunda.bpm.engine.management.JobDefinition;
import org.camunda.bpm.engine.runtime.Incident;

public class SuspendingIncidentHandler extends DefaultIncidentHandler {

  private final ManagementService managementService;
  private final RuntimeService runtimeService;

  public SuspendingIncidentHandler(RuntimeService runtimeService, ManagementService managementService) {

    super(IncidentEntity.FAILED_JOB_HANDLER_TYPE);

    this.managementService = managementService;
    this.runtimeService = runtimeService;
  }

  @Override
  public void deleteIncident(IncidentContext arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public String getIncidentHandlerType() {
    return super.getIncidentHandlerType();
  }

  @Override
  public Incident handleIncident(IncidentContext context, String message) {

    IncidentEntity incidentEntity = (IncidentEntity) super.handleIncident(context, message);

    String processDefinitionId = context.getProcessDefinitionId();

    JobDefinition jobDefinition = managementService.createJobDefinitionQuery().processDefinitionId(processDefinitionId)
        .activityIdIn(context.getActivityId()).singleResult();

    managementService.suspendJobDefinitionById(jobDefinition.getId());

    return incidentEntity;

  }

  @Override
  public void resolveIncident(IncidentContext arg0) {
    // TODO Auto-generated method stub

  }

}
