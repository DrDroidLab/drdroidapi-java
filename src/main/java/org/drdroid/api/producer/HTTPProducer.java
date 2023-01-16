package org.drdroid.api.producer;

import com.squareup.okhttp.OkHttpClient;
import org.drdroid.api.message.Data;
import org.drdroid.api.models.ClientConfig;
import org.drdroid.api.models.UUIDRegister;
import org.drdroid.api.message.MessageResponse;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HTTPProducer implements IProducer {

    private final MessageProducer producer;

    public HTTPProducer(ClientConfig config) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout((long) config.getConnectionTimeoutInMs(), TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout((long) config.getSocketTimeoutInMs(), TimeUnit.MILLISECONDS);
        Retrofit retrofit = (new Retrofit.Builder()).baseUrl(config.getSinkUrl()).client(okHttpClient).addConverterFactory(JacksonConverterFactory.create()).build();
        this.producer = (MessageProducer) retrofit.create(MessageProducer.class);
    }

    public void sendBatch(Data data) {
        Payload payload = new Payload(data);
        Call<MessageResponse> call = this.producer.send(payload);
        try {
            Response<MessageResponse> response = call.execute();
            response.body();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public void send(Data data) {
        Payload payload = new Payload(data);
        Call<MessageResponse> call = this.producer.send(payload);
        try {
            Response<MessageResponse> response = call.execute();
            response.body();
        } catch (IOException var4) {
            var4.printStackTrace();
        }
    }

    public boolean register(UUIDRegister register) {
        Call<MessageResponse> call = this.producer.register(register);
        try {
            Response<MessageResponse> response = call.execute();
            response.body();
            return true;
        } catch (IOException var4) {
            var4.printStackTrace();
            return false;
        }
    }

    private interface MessageProducer {
        @Headers({"Accept: application/json"})
        @POST("w/agent/push_events")
        Call<MessageResponse> send(@Body Payload var1);

        @Headers({"Accept: application/json"})
        @POST("w/agent/register")
        Call<MessageResponse> register(@Body UUIDRegister var1);

    }

}
