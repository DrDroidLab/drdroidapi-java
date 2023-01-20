package org.drdroid.api.producer;

import org.drdroid.api.message.Data;
import org.drdroid.api.models.UUIDRegister;

public interface IProducer {

    void send(Data data);

    void sendBatch(Data data);

    boolean register(UUIDRegister register);

    void sendBeat(UUIDRegister register);

}
