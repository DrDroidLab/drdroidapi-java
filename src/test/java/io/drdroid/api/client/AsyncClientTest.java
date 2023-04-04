package io.drdroid.api.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class AsyncClientTest {

    private IDrDroidAPI asyncClient;

    @Before
    public void setup() {
        asyncClient = AsyncClient.getAsyncClientInstance();
    }

    @Test
    public void testEventSend_AddNewEventToBlockingQueue() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("test-key-1", "test-value-1");
        payload.put("test-key-2", 1);

        asyncClient.send("test-event-name", payload, System.currentTimeMillis());

        Assert.assertEquals(1, asyncClient.getNumOfPendingEvents());
    }

}