package io.drdroid.api;

import com.sun.net.httpserver.HttpServer;
import io.drdroid.api.models.ClientConfig;
import org.junit.After;
import org.junit.Assert;
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

import static io.drdroid.api.data.BaseTestDataSupplier.getMockClientConfig;
import static org.awaitility.Awaitility.await;

@RunWith(MockitoJUnitRunner.class)
public class DrDroidClientTest {

    private HttpServer httpServer;
    private DrDroidClient drDroidClient;

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
        httpServer = HttpServer.create(new InetSocketAddress(8000), 0);
        httpServer.createContext("/w/agent/push_events", exchange -> {
            byte[] response = "{\"count\": 1}".getBytes();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length);
            exchange.getResponseBody().write(response);
            exchange.close();
        });
        httpServer.start();

        drDroidClient = new DrDroidClient(getMockClientConfig());
    }

    @After
    public void teardown() {
        httpServer.stop(0);
    }

    @Test
    public void testSendAPI_WithMockServer() {
        Map<String, Object> kvPairs = new HashMap<>();
        kvPairs.put("test-key-1", "test-value-1");
        kvPairs.put("test-key-2", 1);

        drDroidClient.send("test-workflow-name", "test-state", kvPairs);

        await().atMost(3, TimeUnit.SECONDS).ignoreExceptions().until(() ->
                drDroidClient.getSentEventCount() == 1);
    }


}