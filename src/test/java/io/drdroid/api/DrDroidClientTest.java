package io.drdroid.api;

import io.drdroid.api.data.EnvVars;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockserver.integration.ClientAndServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@RunWith(MockitoJUnitRunner.class)
public class DrDroidClientTest {

    private ClientAndServer mockServer;

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
        mockServer = startClientAndServer(1080);

        mockServer.when(request()
                        .withMethod("POST")
                        .withPath("/e/ingest/events/v2")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("{\"count\": 1}")
                );

        ClientConfiguration.drDroidAsyncMaxWaitTimeInMs = 1;
        DrDroidClient.initDrDroidClient(EnvVars.apiToken, EnvVars.sinkUrl, EnvVars.service);
    }

    @After
    public void teardown() {
        mockServer.stop();
    }

    @Test
    public void testSendAPI_WithMockServer() {
        Map<String, Object> nestedPayload = new HashMap<>();
        nestedPayload.put("inner-key-1", false);
        nestedPayload.put("inner-key-2", 1000);
        nestedPayload.put("inner-key-3", 3.14);

        Map<String, Object> payload = new HashMap<>();
        payload.put("test-key-1", "test-value-1");
        payload.put("test-key-2", 1);
        payload.put("test-key-3", nestedPayload);

        DrDroidClient.send("test-event-name", payload);

        await().atMost(3, TimeUnit.SECONDS).ignoreExceptions().until(() ->
                DrDroidClient.getSentEventCount() == 1);
    }

}