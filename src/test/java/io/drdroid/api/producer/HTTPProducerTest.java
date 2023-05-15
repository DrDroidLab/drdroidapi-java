package io.drdroid.api.producer;

import io.drdroid.api.DrDroidClient;
import io.drdroid.api.data.EnvVars;
import io.drdroid.api.models.http.request.Data;
import io.drdroid.api.models.http.request.UUIDRegister;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockserver.integration.ClientAndServer;

import java.io.IOException;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@RunWith(MockitoJUnitRunner.class)
public class HTTPProducerTest {

    private ClientAndServer mockServer;

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);

        mockServer = startClientAndServer(1080);

        DrDroidClient.initDrDroidClient(EnvVars.apiToken, EnvVars.sinkUrl, EnvVars.service);
    }

    @After
    public void teardown() {
        mockServer.stop();
    }

    @Test
    public void testSendBatch() {
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

        int sentCount = HTTPProducer.getHTTPProducer().sendBatch(new Data());

        Assert.assertEquals(1, sentCount);
    }

    @Test
    public void testRegister() {
        mockServer.when(request()
                        .withMethod("POST")
                        .withPath("/e/agent/register")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("{\"success\": true}")
                );

        Assert.assertTrue(HTTPProducer.getHTTPProducer().register(new UUIDRegister()));
    }

}