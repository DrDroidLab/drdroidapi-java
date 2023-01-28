package io.drdroid.api.producer;

import com.sun.net.httpserver.HttpServer;
import io.drdroid.api.models.http.request.Data;
import io.drdroid.api.models.http.request.UUIDRegister;
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

import static io.drdroid.api.data.BaseTestDataSupplier.getMockClientConfig;

@RunWith(MockitoJUnitRunner.class)
public class HTTPProducerTest {

    private HttpServer httpServer;
    private HTTPProducer httpProducer;

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);

        httpServer = HttpServer.create(new InetSocketAddress(8000), 0);

        httpProducer = new HTTPProducer(getMockClientConfig());
    }

    @After
    public void teardown() {
        httpServer.stop(0);
    }

    @Test
    public void testSendBatch() {
        httpServer.createContext("/w/agent/push_events", exchange -> {
            byte[] response = "{\"count\": 1}".getBytes();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length);
            exchange.getResponseBody().write(response);
            exchange.close();
        });
        httpServer.start();

        int sentCount = httpProducer.sendBatch(new Data());

        Assert.assertEquals(1, sentCount);
    }

    @Test
    public void testRegister() {
        httpServer.createContext("/w/agent/register", exchange -> {
            byte[] response = "{\"success\": true}".getBytes();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length);
            exchange.getResponseBody().write(response);
            exchange.close();
        });
        httpServer.start();

        Assert.assertTrue(httpProducer.register(new UUIDRegister()));
    }

}