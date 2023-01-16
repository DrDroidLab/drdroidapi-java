# drdroidapi-java

## Add SDK locally to your gradle project
```agsl
Add the following to your settings.gradle:
include ':drdroidapi-java'
project(':drdroidapi-java').projectDir = new File('/Users/mohit.goyal/DrDroidLab/drdroidapi-java')
```

```agsl
Add the following to your build.gradle:
implementation project(":drdroidapi-java")
```

In the project directory, you can run:

#### `./graldew clean build`


## Dr Droid SDK client
```agsl
To initialise DrDroidClient, add the following cliennt config:
- sink/collector url (ex- "http://localhost:8000") <String>
- applocation port (ex- 8080) <int>
- service name (ex- "test") <String>
```

```agsl
To send event to DrDroid, use the following interface:
clien.send(String workflowName, String state, Map<String, ?> keyValuePairs);
```

###### For feedback/queries reach out to engineering@drdroid.io