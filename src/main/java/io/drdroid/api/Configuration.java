package io.drdroid.api;

public class Configuration {
    private static final String apiTokenEnvKey = "DRDROID_API_TOKEN";
    private static final String apiTokenEnvDefaultValue = "";
    private static final String sinkUrlEnvKey = "DRDROID_HOSTNAME";
    private static final String sinkUrlEnvDefaultValue = "http://ingestion.drdroid.io";
    private static final String serviceNameEnvKey = "DRDROID_SERVICE_NAME";
    private static final String serviceNameEnvDefaultValue = "";

    private static String apiToken;
    private static String sinkUrl;
    private static String serviceName;

    protected static void initialise() {
        apiToken = System.getenv(apiTokenEnvKey) != null && !System.getenv(apiTokenEnvKey).isEmpty() ?
                System.getenv(apiTokenEnvKey) : apiTokenEnvDefaultValue;

        sinkUrl = System.getenv(sinkUrlEnvKey) != null && !System.getenv(sinkUrlEnvKey).isEmpty() ?
                System.getenv(sinkUrlEnvKey) : sinkUrlEnvDefaultValue;

        serviceName = System.getenv(serviceNameEnvKey) != null && !System.getenv(serviceNameEnvKey).isEmpty() ?
                System.getenv(serviceNameEnvKey) : serviceNameEnvDefaultValue;
    }

    protected static void initialise(String apiTokenOverride, String sinkUrlOverride, String serviceNameOverride) {
        apiToken = apiTokenOverride;
        sinkUrl = sinkUrlOverride;
        serviceName = serviceNameOverride;
    }

    public static String getApiToken() {
        return apiToken;
    }

    public static String getSinkUrl() {
        return sinkUrl;
    }

    public static String getServiceName() {
        return serviceName;
    }

}
