#!/bin/sh
#
#
#

java -p lib:. --add-exports=java.base/jdk.internal.misc=io.netty.common -m examples.docker.image.hello.world.microservice/io.rxmicro.examples.docker.image.hello.world.microservice.HelloWorldMicroService
