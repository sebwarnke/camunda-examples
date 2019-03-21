package com.camunda.consulting.examples.dmn.dmnfromcode;

import java.io.InputStream;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleExecution {
  
  private static final Logger log = LoggerFactory.getLogger(RuleExecution.class);
  
  public static void main(String[] args) {

    // create configuration
    DmnEngineConfiguration configuration = DmnEngineConfiguration.createDefaultDmnEngineConfiguration();

    // configure if needed...

    // build engine
    DmnEngine dmnEngine = configuration.buildEngine();

    // load DMN file
    InputStream inputStream = RuleExecution.class.getClassLoader().getResourceAsStream("decision2.dmn");

    // create and add variables
    VariableMap variables = Variables.createVariables();
    variables.put("string1", "Mobile");
    variables.put("string2", "Mobile");

    // create decision
    DmnDecision decision = dmnEngine.parseDecision("decision2", inputStream);

    // evaluate decision
    DmnDecisionTableResult tableResult = dmnEngine.evaluateDecisionTable(decision, variables);
    
    tableResult.forEach(result-> {
      log.info(result.getFirstEntry());
    });
  }
}
