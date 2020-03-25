https://github.com/oracle/graal/blob/master/substratevm/LIMITATIONS.md


# Using agent

```shell script
java -jar target/ProxyMicroService.jar

$GRAALVM_HOME/bin/java -agentlib:native-image-agent=config-output-dir=.graal -jar target/ProxyMicroService.jar
```

-H:+ReportUnsupportedElementsAtRuntime