package io.drdroid.api.producer;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import io.drdroid.api.Configuration;
import io.drdroid.api.models.ClientConfig;
import io.drdroid.api.models.http.request.Data;
import io.drdroid.api.models.http.request.RequestPayload;
import io.drdroid.api.models.http.request.UUIDRegister;
import io.drdroid.api.models.http.response.RegisterAPIResponse;
import io.drdroid.api.models.http.response.SendBatchAPIResponse;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HTTPProducer implements IProducer {

    private static final Object producerSync = new Object();
    public static boolean isInitialisedWithError;
    private static HTTPProducer instance = null;

    private MessageProducer producer = null;

    public HTTPProducer() {
        try {
            final String apiToken;
            if (!Configuration.getApiToken().startsWith("Bearer")) {
                apiToken = "Bearer " + Configuration.getApiToken();
            } else {
                apiToken = Configuration.getApiToken();
            }
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(ClientConfig.connectionTimeoutInMs, TimeUnit.MILLISECONDS);
            okHttpClient.setReadTimeout(ClientConfig.socketTimeoutInMs, TimeUnit.MILLISECONDS);
            okHttpClient.interceptors().add(chain -> {
                Request request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", apiToken)
                        .build();
                return chain.proceed(request);
            });

            Retrofit retrofit = (new Retrofit.Builder())
                    .baseUrl(Configuration.getSinkUrl())
                    .client(okHttpClient)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            producer = retrofit.create(MessageProducer.class);
        } catch (Exception ignored) {
            isInitialisedWithError = true;
        }
    }

    public static HTTPProducer getHTTPProducer() {
        synchronized (producerSync) {
            if (null == instance || isInitialisedWithError) {
                instance = new HTTPProducer();
            }
        }
        return instance;
    }

    @Override
    public Integer sendBatch(Data data) {
        if (null == this.producer) {
            return 0;
        }
        Call<SendBatchAPIResponse> call = this.producer.send(new RequestPayload(data));
        try {
            Response<SendBatchAPIResponse> response = call.execute();
            return response.body().getCount();
        } catch (IOException var4) {
            var4.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean register(UUIDRegister register) {
        if (null == instance.producer) {
            return false;
        }
        Call<RegisterAPIResponse> call = instance.producer.register(register);
        try {
            Response<RegisterAPIResponse> response = call.execute();
            return response.body().getSuccess();
        } catch (IOException var4) {
            var4.printStackTrace();
        }
        return false;
    }

    @Override
    public void sendBeat(UUIDRegister register) {
    }


    protected interface MessageProducer {
        @POST("e/ingest/events/v2")
        Call<SendBatchAPIResponse> send(@Body RequestPayload var1);

        @POST("e/agent/register")
        Call<RegisterAPIResponse> register(@Body UUIDRegister var1);

        @POST("e/agent/register")
        Call<RegisterAPIResponse> sendBeat(@Body UUIDRegister var1);
    }

}
