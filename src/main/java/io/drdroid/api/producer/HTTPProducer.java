package io.drdroid.api.producer;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
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

    private final MessageProducer producer;

    public HTTPProducer(ClientConfig config) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(config.getConnectionTimeoutInMs(), TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(config.getSocketTimeoutInMs(), TimeUnit.MILLISECONDS);
        okHttpClient.interceptors().add(chain -> {
            Request request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("X-REQUEST-ORG", config.getOrg())
                    .build();
            return chain.proceed(request);
        });

        Retrofit retrofit = (new Retrofit.Builder())
                .baseUrl(config.getSinkUrl())
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        this.producer = retrofit.create(MessageProducer.class);
    }

    public Integer sendBatch(Data data) {
        Call<SendBatchAPIResponse> call = this.producer.send(new RequestPayload(data));
        try {
            Response<SendBatchAPIResponse> response = call.execute();
            return response.body().getCount();
        } catch (IOException var4) {
            var4.printStackTrace();
        }
        return 0;
    }

    public boolean register(UUIDRegister register) {
        Call<RegisterAPIResponse> call = this.producer.register(register);
        try {
            call.execute();
            return true;
        } catch (IOException var4) {
            var4.printStackTrace();
            return false;
        }
    }

    @Override
    public void sendBeat(UUIDRegister register) {
    }


    protected interface MessageProducer {
        @POST("w/agent/push_events")
        Call<SendBatchAPIResponse> send(@Body RequestPayload var1);

        @POST("w/agent/register")
        Call<RegisterAPIResponse> register(@Body UUIDRegister var1);

        @POST("w/agent/register")
        Call<RegisterAPIResponse> sendBeat(@Body UUIDRegister var1);

    }

}