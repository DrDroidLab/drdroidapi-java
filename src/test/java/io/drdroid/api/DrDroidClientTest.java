package io.drdroid.api;

import com.sun.net.httpserver.HttpServer;
import io.drdroid.api.data.EnvVars;
import io.drdroid.api.models.ClientConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@RunWith(MockitoJUnitRunner.class)
public class DrDroidClientTest {

    private HttpServer httpServer;
    private DrDroidClient drDroidClient;

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
        httpServer = HttpServer.create(new InetSocketAddress(8000), 0);
        httpServer.createContext("/e/ingest/events/v2", exchange -> {
            byte[] response = "{\"count\": 1}".getBytes();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length);
            exchange.getResponseBody().write(response);
            exchange.close();
        });
        httpServer.start();

        ClientConfig.asyncMaxWaitTimeInMs = 1;
        DrDroidClient.initDrDroidClient(EnvVars.apiToken, EnvVars.sinkUrl, EnvVars.service);
    }

    @After
    public void teardown() {
        httpServer.stop(0);
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