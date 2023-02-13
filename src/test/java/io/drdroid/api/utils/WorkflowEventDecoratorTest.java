package io.drdroid.api.utils;

import io.drdroid.api.models.Workflow;
import io.drdroid.api.models.WorkflowEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

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

        Map<String, Object> nestedPayload = new HashMap<>();
        nestedPayload.put("inner-key-1", false);
        nestedPayload.put("inner-key-2", 1000);
        nestedPayload.put("inner-key-3", 3.14);

        Map<String, Object> payload = new HashMap<>();
        payload.put("test-key-1", true);
        payload.put("test-key-2", nestedPayload);

        workflowEvent = new WorkflowEvent(new Workflow("test_workflow"), "yyyy-MM-dd HH:mm:ss",
                "test_state", payload);

        Map<String, Object> expectedPayload = new HashMap<>();
        expectedPayload.put("test-key-1", true);
        expectedPayload.put("test-key-2", nestedPayload);
        expectedPayload.put("$drd_ev_id", 1L);
        expectedPayload.put("$drd_agent_id", "123456789");
        expectedPayload.put("service", "test-service-name");

        WorkflowEvent expected = new WorkflowEvent(new Workflow("test_workflow"), "yyyy-MM-dd HH:mm:ss",
                "test_state", expectedPayload);

        WorkflowEvent actual = workflowEventDecorator.build(workflowEvent, 1L, "123456789");

        Assert.assertEquals(expected.getPayload().size(), actual.getPayload().size());
        Assert.assertEquals(expected.getWorkflow().getName(), actual.getWorkflow().getName());
        Assert.assertEquals(expected.getState(), actual.getState());
        Assert.assertEquals(expected.getTimestamp(), actual.getTimestamp());
    }

}