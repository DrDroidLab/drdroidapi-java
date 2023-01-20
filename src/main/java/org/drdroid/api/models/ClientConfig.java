package org.drdroid.api.models;

public class ClientConfig {

    private static final String orgEnvKey = "ORG";
    private static final String orgEnvDefaultValue = "";

    private String sinkUrl;
    private String serviceName;
    private String org;
    private int connectionTimeoutInMs;
    private int socketTimeoutInMs;
    private int asyncMaxWaitTimeInMs;
    private int asyncBatchSize;
    private int maxQueueSize;
    private int messagePerSecond;

    private int servicePort;

    public ClientConfig(String sinkUrl, int servicePort, String serviceName) {
        //TODO: tune configs
        this.connectionTimeoutInMs = 1000;
        this.socketTimeoutInMs = 1000;
        this.asyncMaxWaitTimeInMs = 1000;
        this.asyncBatchSize = 20;
        this.maxQueueSize = 20;
        this.messagePerSecond = 10;
        this.sinkUrl = sinkUrl;
        this.servicePort = servicePort;
        this.serviceName = serviceName;
        this.org = System.getProperty(orgEnvKey, orgEnvDefaultValue);
    }

    public ClientConfig(String org, String sinkUrl, int servicePort, String serviceName) {
        //TODO: tune configs
        this.connectionTimeoutInMs = 1000;
        this.socketTimeoutInMs = 1000;
        this.asyncMaxWaitTimeInMs = 1000;
        this.asyncBatchSize = 20;
        this.maxQueueSize = 20;
        this.messagePerSecond = 10;
        this.sinkUrl = sinkUrl;
        this.servicePort = servicePort;
        this.serviceName = serviceName;
        this.org = org != null && !org.isEmpty() ? org : orgEnvDefaultValue;
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

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
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

    public int getServicePort() {
        return servicePort;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

}
