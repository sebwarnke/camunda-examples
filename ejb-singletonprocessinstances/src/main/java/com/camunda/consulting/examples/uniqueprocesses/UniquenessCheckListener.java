package com.camunda.consulting.examples.uniqueprocesses;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;

public class UniquenessCheckListener implements ExecutionListener {

  @Override
  public void notify(DelegateExecution execution) throws Exception {

    String businessKey = execution.getBusinessKey();
    String processDefinitionId = execution.getProcessInstance().getProcessDefinitionId();

    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();

    ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery()
        .processInstanceBusinessKey(businessKey)
        .processDefinitionId(processDefinitionId);

    long count = query.count();

    if (count > 1) {
      throw new Exception("not unique!");
    }
  }

}
