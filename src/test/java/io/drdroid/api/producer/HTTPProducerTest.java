package io.drdroid.api.producer;

import com.sun.net.httpserver.HttpServer;
import io.drdroid.api.Configuration;
import io.drdroid.api.data.EnvVars;
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

@RunWith(MockitoJUnitRunner.class)
public class HTTPProducerTest {

    private HttpServer httpServer;

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);

        httpServer = HttpServer.create(new InetSocketAddress(8000), 0);

        Configuration.org = EnvVars.org;
        Configuration.sinkUrl = EnvVars.sinkUrl;
        Configuration.serviceName = EnvVars.service;
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

        int sentCount = HTTPProducer.getHTTPProducer().sendBatch(new Data());

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

        Assert.assertTrue(HTTPProducer.getHTTPProducer().register(new UUIDRegister()));
    }

}