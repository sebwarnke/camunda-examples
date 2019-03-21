package com.camunda.consulting.examples.springboot.suspendjobdefinition;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This is an easy adapter implementation illustrating how a Java Delegate can
 * be used from within a BPMN 2.0 Service Task.
 */
@Component("RuinThisTaskDelegate")
public class RuinThisTaskDelegate implements JavaDelegate {

  private final Logger log = LoggerFactory.getLogger(RuinThisTaskDelegate.class);

  public void execute(DelegateExecution execution) throws Exception {

    log.warn("Exception to be thrown in 3... 2... 1...");
    throw new Exception("Congratulation, you ruined that!");
  }

}
