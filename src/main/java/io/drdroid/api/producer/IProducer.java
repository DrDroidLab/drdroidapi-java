package io.drdroid.api.producer;

import io.drdroid.api.models.http.request.Data;
import io.drdroid.api.models.http.request.UUIDRegister;

public interface IProducer {

    static Integer sendBatch(Data data) {
        return null;
    }

    boolean register(UUIDRegister register);

    void sendBeat(UUIDRegister register);

}
