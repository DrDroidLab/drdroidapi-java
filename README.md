# Doctor Droid - Java SDK for custom events
This is a library that will allow developers to push custom stateful events to Doctor Droid Platform.
Read more [here](https://kenobi.drdroid.io/docs).

## Install the SDK
### Compile SDK locally to your gradle project
#### Add the following to your settings.gradle:
```agsl
include ':drdroidapi-java'
project(':drdroidapi-java').projectDir = new File('<path to lib>/drdroidapi-java')
```

#### Add the following to your build.gradle:
```agsl
implementation project(":drdroidapi-java")
```

Build your java project

## Setup the configuration
You will need to setup local environment variables wherever your application is running that needs to send the events. You will get these from Doctor Droid platform, but for demo, setup by running the following:
```
export ORG_NAME=DRD
export DRDROID_HOSTNAME=http://apidemo1.drdroid.io:8080
```

## Start sending events
After the configurations are done, you can import the module in your java classes and use APIs exposed by the sdk.

### Dr Droid SDK client
```agsl
To initialise DrDroidClient, add the following cliennt config:
- service port (ex- 8080) <int>
- service name (ex- "sample_service_name") <String>
```

```agsl
To send event to DrDroid, use the following interface:
clien.send(String workflowName, String state, Map<String, ?> keyValuePairs);
```
## View your workflows
Once your events have been published, you can view the workflow these events are creating and how it resembles the actual business flow for your customers or internal processes. Check out this URL - [http://demo1.drdroid.io](http://demo1.drdroid.io)

Visit [Doctor Droid website](https://drdroid.io?utm_param=github-py) for getting early access and the [integration documentation](https://kenobi.drdroid.io?utm_param=github-py) for some use-cases it can solve.

For any queries/feedback, reach out at [mohit.goyal@drdroid.io](mailto:mohit.goyal@drdroid.io).
