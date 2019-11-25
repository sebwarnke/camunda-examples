package com.camunda.example.substitution.listeners.task;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class OnAssignHolidayReplacementListener implements TaskListener {

  @Override
  public void notify(final DelegateTask delegateTask) {

    List<String> absenceList = new ArrayList<>(Arrays.asList("Sebastian", "Roland"));
    String assignee = delegateTask.getAssignee();

    if (absenceList.contains(assignee)) {
      TaskService taskService = delegateTask.getProcessEngineServices().getTaskService();
      taskService.setAssignee(delegateTask.getId(), "Rafael");
    }
  }
}
