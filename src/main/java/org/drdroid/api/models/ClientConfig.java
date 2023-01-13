package org.drdroid.api.models;

public class ClientConfig {

    private String sinkUrl;
    private String serviceName;
    private int connectionTimeoutInMs;
    private int socketTimeoutInMs;
    private int asyncMaxWaitTimeInMs;
    private int asyncBatchSize;
    private int maxQueueSize;
    private int messagePerSecond;

    private int port;

    public ClientConfig(String sinkUrl, int port, String serviceName) {
        //TODO: tune configs
        this.connectionTimeoutInMs = 100;
        this.socketTimeoutInMs = 100;
        this.asyncMaxWaitTimeInMs = 100;
        this.asyncBatchSize = 10;
        this.maxQueueSize = 10;
        this.messagePerSecond = 10;
        this.sinkUrl = sinkUrl;
        this.port = port;
        this.serviceName = serviceName;
    }

    public String getSinkUrl() {
        return sinkUrl;
    }

    public void setSinkUrl(String sinkUrl) {
        this.sinkUrl = sinkUrl;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getConnectionTimeoutInMs() {
        return connectionTimeoutInMs;
    }

    public void setConnectionTimeoutInMs(int connectionTimeoutInMs) {
        this.connectionTimeoutInMs = connectionTimeoutInMs;
    }

    public int getSocketTimeoutInMs() {
        return socketTimeoutInMs;
    }

    public void setSocketTimeoutInMs(int socketTimeoutInMs) {
        this.socketTimeoutInMs = socketTimeoutInMs;
    }

    public int getAsyncMaxWaitTimeInMs() {
        return asyncMaxWaitTimeInMs;
    }

    public void setAsyncMaxWaitTimeInMs(int asyncMaxWaitTimeInMs) {
        this.asyncMaxWaitTimeInMs = asyncMaxWaitTimeInMs;
    }

    public int getAsyncBatchSize() {
        return asyncBatchSize;
    }

    public void setAsyncBatchSize(int asyncBatchSize) {
        this.asyncBatchSize = asyncBatchSize;
    }

    public int getMaxQueueSize() {
        return maxQueueSize;
    }

    public void setMaxQueueSize(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }

    public int getMessagePerSecond() {
        return messagePerSecond;
    }

    public void setMessagePerSecond(int messagePerSecond) {
        this.messagePerSecond = messagePerSecond;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
