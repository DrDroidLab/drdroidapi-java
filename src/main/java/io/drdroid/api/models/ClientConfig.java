package io.drdroid.api.models;

public class ClientConfig {

    private static final String orgEnvKey = "ORG_NAME";
    private static final String orgEnvDefaultValue = "";
    private static final String sinUrlEnvKey = "DRDROID_HOSTNAME";
    private static final String sinUrlEnvDefaultValue = "";

    private int connectionTimeoutInMs = 1000; //1s
    private int socketTimeoutInMs = 1000; //1s
    private int asyncMaxWaitTimeInMs = 10000; //10s
    private int asyncBatchSize = 10;
    private int maxQueueSize = 300;
    private int messagePerSecond = 10;

    private String sinkUrl;
    private String serviceName;
    private int servicePort;
    private String org;

    public ClientConfig(int servicePort, String serviceName) {
        this.servicePort = servicePort;
        this.serviceName = serviceName;
        this.sinkUrl = System.getenv(sinUrlEnvKey) != null && !System.getenv(sinUrlEnvKey).isEmpty() ?
                System.getenv(sinUrlEnvKey) : sinUrlEnvDefaultValue;
        this.org = System.getenv(orgEnvKey) != null && !System.getenv(orgEnvKey).isEmpty() ?
                System.getenv(orgEnvKey) : orgEnvDefaultValue;
    }

    public ClientConfig(String sinkUrl, int servicePort, String serviceName) {
        this.sinkUrl = sinkUrl;
        this.servicePort = servicePort;
        this.serviceName = serviceName;
        this.org = System.getProperty(orgEnvKey, orgEnvDefaultValue);
    }

    public ClientConfig(String org, String sinkUrl, int servicePort, String serviceName) {
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
