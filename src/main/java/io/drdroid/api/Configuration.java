package io.drdroid.api;

public class Configuration {
    private static final String orgEnvKey = "DRDROID_ORG_NAME";
    private static final String orgEnvDefaultValue = "";
    private static final String sinkUrlEnvKey = "DRDROID_HOSTNAME";
    private static final String sinkUrlEnvDefaultValue = "";
    private static final String serviceNameEnvKey = "DRDROID_SERVICE_NAME";
    private static final String serviceNameEnvDefaultValue = "";

    public static String org = System.getenv(orgEnvKey) != null && !System.getenv(orgEnvKey).isEmpty() ?
            System.getenv(orgEnvKey) : orgEnvDefaultValue;

    public static String sinkUrl = System.getenv(sinkUrlEnvKey) != null && !System.getenv(sinkUrlEnvKey).isEmpty() ?
            System.getenv(sinkUrlEnvKey) : sinkUrlEnvDefaultValue;

    public static String serviceName = System.getenv(serviceNameEnvKey) != null && !System.getenv(serviceNameEnvKey).isEmpty() ?
            System.getenv(serviceNameEnvKey) : serviceNameEnvDefaultValue;

}
