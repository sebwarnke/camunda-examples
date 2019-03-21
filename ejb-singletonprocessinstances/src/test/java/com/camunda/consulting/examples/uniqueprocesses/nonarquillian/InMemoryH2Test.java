package com.camunda.consulting.examples.uniqueprocesses.nonarquillian;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.init;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.processEngine;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class InMemoryH2Test {

  @ClassRule
  @Rule
  public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

  private static final String UNIQUE_PROCESS_DEFINITION_KEY = "uniqueprocess";
  private static final String REGULAR_PROCESS_DEFINITION_KEY = "regularprocess";

  static {
    LogFactory.useSlf4jLogging(); // MyBatis
  }

  @Before
  public void setup() {
    init(rule.getProcessEngine());
  }

  /**
   * Just tests if the process definition is deployable.
   */
  @Test
  @Deployment(resources = {"uniqueprocess.bpmn","regularprocess.bpmn"})
  public void testParsingAndDeployment() {
    // nothing is done here, as we just want to check for exceptions during deployment
  }

  @Test
  @Deployment(resources = {"uniqueprocess.bpmn","regularprocess.bpmn"})
  public void testHappyPath() {
	  ProcessInstance firstProcessInstance = processEngine().getRuntimeService().startProcessInstanceByKey(UNIQUE_PROCESS_DEFINITION_KEY, "mybusinesskey");
	  assertThat(firstProcessInstance).isWaitingAt("StartEventProcessStarted");
	  execute(job());
	  assertThat(firstProcessInstance).isWaitingAt("Task_MakeSomethingUnique");
	  
	  try {
	    ProcessInstance secondProcessInstance = processEngine().getRuntimeService().startProcessInstanceByKey(UNIQUE_PROCESS_DEFINITION_KEY, "mybusinesskey");
      assertThat(secondProcessInstance).isActive();
      execute(job());
	    fail("should not be executed :(");
    } catch (Exception e) {
      assertThat(e.getMessage()).isEqualTo("not unique!");
    }
	  
	  
	  
  }

}
