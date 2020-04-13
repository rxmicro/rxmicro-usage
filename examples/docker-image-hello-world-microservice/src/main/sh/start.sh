#!/bin/sh
#
#
#
export RX_MICRO_NETTY_OPTIONS="--add-opens=java.base/jdk.internal.misc=io.netty.common"
export RX_MICRO_NETTY_OPTIONS="$RX_MICRO_NETTY_OPTIONS --add-opens=java.base/java.nio=io.netty.common"
export RX_MICRO_NETTY_OPTIONS="$RX_MICRO_NETTY_OPTIONS -Dio.netty.tryReflectionSetAccessible=true"

java -p lib:. $RX_MICRO_NETTY_OPTIONS -m examples.docker.image.hello.world.microservice/io.rxmicro.examples.docker.image.hello.world.microservice.HelloWorldMicroService
