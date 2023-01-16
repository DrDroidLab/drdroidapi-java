package org.drdroid.api.utils;

import org.drdroid.api.models.KeyValue;
import org.drdroid.api.models.Value;
import org.drdroid.api.models.Workflow;
import org.drdroid.api.models.WorkflowEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class WorkflowEventDecoratorTest {

    private WorkflowEventDecorator workflowEventDecorator;

    private WorkflowEvent workflowEvent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        workflowEventDecorator = new WorkflowEventDecorator("test-service-name");
    }

    @Test
    public void testWorkflowDecoratorWithKvPairs() {

        Value testValue = new Value();
        testValue.setStringValue("test_value");
        testValue.setValid(true);
        KeyValue kv = new KeyValue("test_key", testValue);
        List<KeyValue> kvPairs = new ArrayList<>();
        kvPairs.add(kv);
        workflowEvent = new WorkflowEvent(new Workflow("test_workflow"), "yyyy-MM-dd HH:mm:ss",
                "test_state", kvPairs);

        List<KeyValue> expectedKvList = new ArrayList<>();
        expectedKvList.add(kv);

        Value drdEvIdValue = new Value();
        drdEvIdValue.setLongValue(1L);
        drdEvIdValue.setValid(true);
        expectedKvList.add(new KeyValue("$drd_ev_id", drdEvIdValue));

        Value drdAgentIdValue = new Value();
        drdAgentIdValue.setStringValue("123456789");
        drdAgentIdValue.setValid(true);
        expectedKvList.add(new KeyValue("$drd_agent_id", drdAgentIdValue));

        Value serviceNameValue = new Value();
        serviceNameValue.setStringValue("test-service-name");
        serviceNameValue.setValid(true);
        expectedKvList.add(new KeyValue("service", serviceNameValue));

        WorkflowEvent expected = new WorkflowEvent(new Workflow("test_workflow"), "yyyy-MM-dd HH:mm:ss",
                "test_state", expectedKvList);

        WorkflowEvent actual = workflowEventDecorator.build(workflowEvent, 1L, "123456789");

        Assert.assertEquals(expected.getKvPairs().size(), actual.getKvPairs().size());
        Assert.assertEquals(expected.getWorkflow().getName(), actual.getWorkflow().getName());
        Assert.assertEquals(expected.getState(), actual.getState());
        Assert.assertEquals(expected.getTimestamp(), actual.getTimestamp());
    }

}