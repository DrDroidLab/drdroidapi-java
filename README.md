# Doctor Droid - Java SDK for custom ingestionEvents

This is a library that will allow developers to Ingest custom stateful to Doctor Droid Platform.
Read more [here](https://kenobi.drdroid.io/docs).

## Install the SDK

### Compile SDK locally to your gradle project

#### Add the following to your build.gradle:

```agsl
implementation group: 'io.drdroid', name: 'api-java', version: '2.0.2'
```
Build your java project

Maven Link - https://mvnrepository.com/artifact/io.drdroid/api-java

## Setup the configuration

You will need to setup local environment variables wherever your application is running that needs to send the
ingestionEvents. You will get these from Doctor Droid platform, but for demo, setup by running the following:

```
export DRDROID_AUTH_TOKEN=<API Auth Token>
export DRDROID_HOSTNAME=https://ingest.drdroid.io
export DRDROID_SERVICE_NAME=<service name>
```

If you want to explicitly initialize DrDroid client bean, you can initiliaze Dr Droid bean as follows:
```agsl
To initialise DrDroidClient, add the following client config:
DrDroidClient.initDrDroidClient(<api_token>, <sinK_url>, <service_name>)

- api_token <String>
- sink_url <String>
- service_name <String>
```

Additionally, you can control the behavior of DrDroid Client by configuring following variables either directly in the code or through ENV variables. 

```agsl
ClientConfiguration.drDroidConnectionTimeoutInMs (int, default: 1000) - Http call connect timeout between client and collector

ClientConfiguration.drDroidSocketTimeoutInMs (int, default: 1000) - Http call socket timeout between client and collector

ClientConfiguration.drDroidAsyncMaxWaitTimeInMs (int, default: 10000) - Client poll interval

ClientConfiguration.drDroidAsyncBatchSize (int, default: 10) - Max Number of messages published in a single poll 

ClientConfiguration.drDroidMaxQueueSize (int, default: 300) - Max queue size. If number of messages in the buffer cross this threshold, packets will be dropped. 

ClientConfiguration.drDroidMessagePerSecond (int, default: 10) - Expected client throughput 
```

Configuring env variables-
```agsl
export drdroid-connect-timeout = <integer>
export drdroid-socket-timeout = <integer>
export drdroid-async-max-wait-time = <integer>
export drdroid-async-batch-size = <integer>
export drdroid-max-queue-size = <integer>
export drdroid-message-per-second = <integer>
```

Number of threads created for the executor pool is calculated as follows- 

```agsl
float messageSentPerSecondInSingleThread = (float) (1000 * ClientConfiguration.getAsyncBatchSize() / ClientConfiguration.getSocketTimeoutInMs());
int threadsRequied = ClientConfiguration.getMessagePerSecond() / (int) messageSentPerSecondInSingleThread;
```

## Start sending Ingestion Events

After the configurations are done, you can import the module in your java classes and use APIs exposed by the sdk. To send ingestionEvent to DrDroid, use the following interface:

```agsl

DrDroidClient.send(String event_name, Map<String, ?> kvPairs);
```

#### Run the jar from terminal using the following command

```agsl
java -jar <location to jar file>.jar
```

## View your workflows

Once your Ingestion Events have been published, you can view them and create monitors. Check out this
URL - [http://app.drdroid.io](https://app.drdroid.io)

Visit [Doctor Droid website](https://drdroid.io?utm_param=github-py) for getting early access and

For any queries/feedback, reach out at [mohit.goyal@drdroid.io](mailto:mohit.goyal@drdroid.io).
