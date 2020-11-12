package com.sebwarnke.camunda.examples.funwithvariables;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTests {

    public static final String FUN_WITH_VARIABLES = "funwithvariables";
    public static final String MESSAGE_EVENT = "message_event";
    public static final String MESSAGE_NAME = "variable-received";

    public static final String BUSINESS_KEY_1 = "business_key_1";
    public static final String BUSINESS_KEY_2 = "business_key_2";

    @Autowired
    ProcessEngine processEngine;

    @Before
    public void setup() {
        init(processEngine);
    }

    @Test
    public void useOfCorrelateMessageWithVariable() {
        /*Start Process Instance*/
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(FUN_WITH_VARIABLES, BUSINESS_KEY_1);

        assertThat(processInstance).isWaitingAt(MESSAGE_EVENT);

        /*Correlate Message with a variable*/
        runtimeService.createMessageCorrelation(MESSAGE_NAME).processInstanceBusinessKey(BUSINESS_KEY_1).setVariable("Foo", "Bar").correlate();

        /*Assert*/
        assertThat(processInstance).hasVariables("Foo");
        assertThat(processInstance).isEnded();
    }

    @Test
    public void useOfRuntimeServiceSetVariable() {
        /*Start Process Instance*/
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(FUN_WITH_VARIABLES, BUSINESS_KEY_2);

        assertThat(processInstance).isWaitingAt(MESSAGE_EVENT);

        /*
         * While waiting for the message we want to add a variable to the running process instance via API. Let's
         * consider we do not have the process instance id available from the caller.
         */
        List<ProcessInstance> processInstancesWithBusinessKey2 = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(BUSINESS_KEY_2).list();

        /* Assert that we only found one instance with business key 2 */
        assertThat(processInstancesWithBusinessKey2).hasSize(1);

        String processInstanceId = processInstancesWithBusinessKey2.get(0).getProcessInstanceId();

        runtimeService.setVariable(processInstanceId, "Foo", "foo");

        /* Assert that process instance has not left the message event. Why should it, though? */
        assertThat(processInstance).isWaitingAt(MESSAGE_EVENT);

        /*Correlate Message with a variable*/
        runtimeService.createMessageCorrelation(MESSAGE_NAME).processInstanceBusinessKey(BUSINESS_KEY_2).setVariable("Bar", "Bar").correlate();

        /*Assert*/
        assertThat(processInstance).hasVariables("Foo", "Bar");
        assertThat(processInstance).isEnded();
    }
}
