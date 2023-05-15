package io.drdroid.api;

public class ClientConfiguration {

    private static final String connectionTimeoutInMsKey = "drdroid-connect-timeout";
    private static final int connectionTimeoutInMsDefaultValue = 1000; //1 second

    private static final String socketTimeoutInMsKey = "drdroid-socket-timeout";
    private static final int socketTimeoutInMsDefaultValue = 1000; //1 second

    private static final String asyncMaxWaitTimeInMsKey = "drdroid-async-max-wait-time";
    private static final int asyncMaxWaitTimeInMsDefaultValue = 10000; //10 seconds

    private static final String asyncBatchSizeKey = "drdroid-async-batch-size";
    private static final int asyncBatchSizeDefaultValue = 10;

    private static final String maxQueueSizeKey = "drdroid-max-queue-size";
    private static final int maxQueueSizeDefaultValue = 300;

    private static final String messagePerSecondKey = "drdroid-message-per-second";
    private static final int messagePerSecondDefaultValue = 10;

    private static int connectionTimeoutInMs;
    private static int socketTimeoutInMs;
    private static int asyncMaxWaitTimeInMs;
    private static int asyncBatchSize;
    private static int maxQueueSize;
    private static int messagePerSecond;

    public static int drDroidConnectionTimeoutInMs;
    public static int drDroidSocketTimeoutInMs;
    public static int drDroidAsyncMaxWaitTimeInMs;
    public static int drDroidAsyncBatchSize;
    public static int drDroidMaxQueueSize;
    public static int drDroidMessagePerSecond;

    protected static void initialise() {
        if (drDroidConnectionTimeoutInMs == 0) {
            connectionTimeoutInMs = (System.getenv(connectionTimeoutInMsKey) != null &&
                    !System.getenv(connectionTimeoutInMsKey).isEmpty()) ?
                    Integer.parseInt(System.getenv(connectionTimeoutInMsKey)) : connectionTimeoutInMsDefaultValue;
        } else {
            connectionTimeoutInMs = drDroidConnectionTimeoutInMs;
        }

        if (drDroidSocketTimeoutInMs == 0) {
            socketTimeoutInMs = (System.getenv(socketTimeoutInMsKey) != null &&
                    !System.getenv(socketTimeoutInMsKey).isEmpty()) ?
                    Integer.parseInt(System.getenv(socketTimeoutInMsKey)) : socketTimeoutInMsDefaultValue;
        } else {
            socketTimeoutInMs = drDroidSocketTimeoutInMs;
        }

        if (drDroidAsyncMaxWaitTimeInMs == 0) {
            asyncMaxWaitTimeInMs = (System.getenv(asyncMaxWaitTimeInMsKey) != null &&
                    !System.getenv(asyncMaxWaitTimeInMsKey).isEmpty()) ?
                    Integer.parseInt(System.getenv(asyncMaxWaitTimeInMsKey)) : asyncMaxWaitTimeInMsDefaultValue;
        } else {
            asyncMaxWaitTimeInMs = drDroidAsyncMaxWaitTimeInMs;
        }

        if (drDroidAsyncBatchSize == 0) {
            asyncBatchSize = (System.getenv(asyncBatchSizeKey) != null &&
                    !System.getenv(asyncBatchSizeKey).isEmpty()) ?
                    Integer.parseInt(System.getenv(asyncBatchSizeKey)) : asyncBatchSizeDefaultValue;
        } else {
            asyncBatchSize = drDroidAsyncBatchSize;
        }

        if (drDroidMaxQueueSize == 0) {
            maxQueueSize = (System.getenv(maxQueueSizeKey) != null &&
                    !System.getenv(maxQueueSizeKey).isEmpty()) ?
                    Integer.parseInt(System.getenv(maxQueueSizeKey)) : maxQueueSizeDefaultValue;
        } else {
            maxQueueSize = drDroidMaxQueueSize;
        }

        if (drDroidMessagePerSecond == 0) {
            messagePerSecond = (System.getenv(messagePerSecondKey) != null &&
                    !System.getenv(messagePerSecondKey).isEmpty()) ?
                    Integer.parseInt(System.getenv(messagePerSecondKey)) : messagePerSecondDefaultValue;
        } else {
            messagePerSecond = drDroidMessagePerSecond;
        }
    }


    public static int getConnectionTimeoutInMs() {
        return connectionTimeoutInMs != 0 ? connectionTimeoutInMs : connectionTimeoutInMsDefaultValue;
    }


    public static int getSocketTimeoutInMs() {
        return socketTimeoutInMs != 0 ? socketTimeoutInMs : socketTimeoutInMsDefaultValue;
    }

    public static int getAsyncMaxWaitTimeInMs() {
        return asyncMaxWaitTimeInMs != 0 ? asyncMaxWaitTimeInMs : asyncMaxWaitTimeInMsDefaultValue;
    }

    public static int getAsyncBatchSize() {
        return asyncBatchSize != 0 ? asyncBatchSize : asyncBatchSizeDefaultValue;
    }

    public static int getMaxQueueSize() {
        return maxQueueSize != 0 ? maxQueueSize : maxQueueSizeDefaultValue;
    }

    public static int getMessagePerSecond() {
        return messagePerSecond != 0 ? messagePerSecond : messagePerSecondDefaultValue;
    }

}
