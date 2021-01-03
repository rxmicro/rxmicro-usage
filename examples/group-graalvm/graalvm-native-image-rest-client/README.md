# Launch scripts

--------------------------------------------------------------------------------------------------------------------------------------------
https://github.com/oracle/graal/blob/master/substratevm/LIMITATIONS.md
--------------------------------------------------------------------------------------------------------------------------------------------
## Using java

```shell script
java -jar target/RestJdkClientLauncher.jar
```

```shell script
java -jar target/RestNettyClientLauncher.jar
```
--------------------------------------------------------------------------------------------------------------------------------------------
## Using native image agent

```shell script
$GRAALVM_HOME/bin/java -agentlib:native-image-agent=config-output-dir=.graal-jdk -jar target/RestJdkClientLauncher.jar
```

```shell script
$GRAALVM_HOME/bin/java -agentlib:native-image-agent=config-output-dir=.graal-netty -jar target/RestNettyClientLauncher.jar
```
--------------------------------------------------------------------------------------------------------------------------------------------
