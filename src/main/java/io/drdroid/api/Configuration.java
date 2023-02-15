package io.drdroid.api;

public class Configuration {
    private static final String orgEnvKey = "DRDROID_ORG_NAME";
    private static final String orgEnvDefaultValue = "";
    private static final String sinkUrlEnvKey = "DRDROID_HOSTNAME";
    private static final String sinkUrlEnvDefaultValue = "";
    private static final String serviceNameEnvKey = "DRDROID_SERVICE_NAME";
    private static final String serviceNameEnvDefaultValue = "";

    private static String org;
    private static String sinkUrl;
    private static String serviceName;

    protected static void initialise() {
        org = System.getenv(orgEnvKey) != null && !System.getenv(orgEnvKey).isEmpty() ?
                System.getenv(orgEnvKey) : orgEnvDefaultValue;

        sinkUrl = System.getenv(sinkUrlEnvKey) != null && !System.getenv(sinkUrlEnvKey).isEmpty() ?
                System.getenv(sinkUrlEnvKey) : sinkUrlEnvDefaultValue;

        serviceName = System.getenv(serviceNameEnvKey) != null && !System.getenv(serviceNameEnvKey).isEmpty() ?
                System.getenv(serviceNameEnvKey) : serviceNameEnvDefaultValue;
    }

    protected static void initialise(String orgOverride, String sinkUrlOverride, String serviceNameOverride) {
        org = orgOverride;
        sinkUrl = sinkUrlOverride;
        serviceName = serviceNameOverride;
    }

    public static String getOrg() {
        return org;
    }

    public static String getSinkUrl() {
        return sinkUrl;
    }

    public static String getServiceName() {
        return serviceName;
    }

}
