# Using JRE

## Start

```shell script
java --add-exports java.base/jdk.internal.misc=ALL-UNNAMED -jar target/HelloWorldMicroService.jar

Server started at 0.0.0.0:8080 using NETTY transport in 500 millis.
```

## Start with min Heap

```shell script
java -Xmx3M -Xms3M --add-exports java.base/jdk.internal.misc=ALL-UNNAMED -jar target/HelloWorldMicroService.jar

Server started at 0.0.0.0:8080 using NETTY transport in 800 millis.
```

## Show the memory footprint

```shell script
/usr/bin/time -f "\nmaxRSS\t%MkB" java -Xmx3M -Xms3M --add-exports java.base/jdk.internal.misc=ALL-UNNAMED -jar target/HelloWorldMicroService.jar

maxRSS 65Mb
```

--------------------------------------------------------------------------------------------------------------------------------------------
# Using native image

## Start

```shell script
./HelloWorldMicroService

Server started at 0.0.0.0:8080 using NETTY transport in 5 millis.
```

## Show the memory footprint

```shell script
/usr/bin/time -f "\nmaxRSS\t%MkB" ./HelloWorldMicroService

maxRSS 17Mb
```

# Using agent

```shell script
$GRAALVM_HOME/bin/java -agentlib:native-image-agent=config-output-dir=.graal -jar target/HelloWorldMicroService.jar
```