package io.drdroid.api.models;

public class ClientConfig {

    public static int connectionTimeoutInMs = 1000; //1s
    public static int socketTimeoutInMs = 1000; //1s
    public static int asyncMaxWaitTimeInMs = 10000; //10s
    public static int asyncBatchSize = 10;
    public static int maxQueueSize = 300;
    public static int messagePerSecond = 10;

}
