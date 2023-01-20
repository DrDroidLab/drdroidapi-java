package org.drdroid.api;

import org.drdroid.api.models.ClientConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class DrDroidClientTest {

    private ClientConfig config;
    private DrDroidClient drDroidClient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        config = new ClientConfig("http://localhost:8000", 8000, "test-service-name");
        drDroidClient = new DrDroidClient(config);
    }

    @Test
    public void testSdk_NoAssertion() {
        Map<String, Object> kvPairs = new HashMap<>();
        kvPairs.put("test-key-1", "test-value-1");
        kvPairs.put("test-key-2", 1);

        drDroidClient.send("test-workflow-name", "test-state", kvPairs);

        Assert.assertTrue(true);
    }


}