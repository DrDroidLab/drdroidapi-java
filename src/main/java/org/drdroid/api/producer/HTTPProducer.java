package org.drdroid.api.producer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import org.drdroid.api.message.Data;
import org.drdroid.api.models.ClientConfig;
import org.drdroid.api.models.UUIDRegister;
import org.drdroid.api.message.MessageResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HTTPProducer implements IProducer {
    private final MessageProducer producer;

    public HTTPProducer(ClientConfig config) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(config.getConnectionTimeoutInMs(), TimeUnit.MILLISECONDS)
                .readTimeout(config.getSocketTimeoutInMs(), TimeUnit.MILLISECONDS)
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(config.getSinkUrl())
                .client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.producer = (MessageProducer) retrofit.create(MessageProducer.class);
    }

    public void sendBatch(Data data) {
        Call<MessageResponse> call = this.producer.send(data);
        try {
            Response<MessageResponse> response = call.execute();
            response.body();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public void send(Data data) {
        Call<MessageResponse> call = this.producer.send(data);
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
        @POST("/w/agent/push_events")
        Call<MessageResponse> send(@Body Data var1);

        @POST("/w/agent/register")
        Call<MessageResponse> register(@Body UUIDRegister var1);

    }

}
