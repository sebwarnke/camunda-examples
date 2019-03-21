package com.camunda.consulting.examples.ejb.forms.dynamicselectionform;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

public class StartListener implements ExecutionListener {

  @Override
  public void notify(DelegateExecution execution) throws Exception {

    List<String> options = new ArrayList<String>();
    options.add("option a");
    options.add("option b");
    options.add("option c");
    options.add("option d");
    
    execution.setVariable("selectOptions", options);

  }

}
