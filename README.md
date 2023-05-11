# Doctor Droid - Java SDK for custom ingestionEvents

This is a library that will allow developers to push custom stateful ingestionEvents to Doctor Droid Platform.
Read more [here](https://kenobi.drdroid.io/docs).

## Install the SDK

### Compile SDK locally to your gradle project

#### Add the following to your build.gradle:

```agsl
implementation group: 'io.drdroid', name: 'api-java', version: '2.0.0'
```

Build your java project

## Setup the configuration

You will need to setup local environment variables wherever your application is running that needs to send the
ingestionEvents. You will get these from Doctor Droid platform, but for demo, setup by running the following:

```
export DRDROID_AUTH_TOKEN=Bearer <API Auth Token>
export DRDROID_HOSTNAME=https://ingest.drdroid.io
export DRDROID_SERVICE_NAME=<service name>
```

## Start sending Ingestion Events

After the configurations are done, you can import the module in your java classes and use APIs exposed by the sdk.

### Dr Droid SDK client

```agsl
To initialise DrDroidClient, add the following client config:
- api token <String>
- sink url <String>
- service name (ex- "sample_service_name") <String>
```

```agsl
To send ingestionEvent to DrDroid, use the following interface:
client.send(String state, Map<String, ?> keyValuePairs);
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
