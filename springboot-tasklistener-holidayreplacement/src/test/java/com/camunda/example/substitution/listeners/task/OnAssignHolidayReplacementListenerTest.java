package com.camunda.example.substitution.listeners.task;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

public class OnAssignHolidayReplacementListenerTest {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = "user-task.bpmn")
  public void testAssignUser() {

    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("holiday-replacement");

    assertThat(processInstance).isWaitingAt("user-task");

    taskService().setAssignee(task().getId(), "Sebastian");

    assertThat(task()).isAssignedTo("Rafael");

  }
}
