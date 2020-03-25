# Build:
# docker build -t rxmicro/native-hello-world .
#
# Run:
# docker run -it --rm --name rxmicro-native-hello-world -p 8080:8080 -e PRINT_ENV=true rxmicro/native-hello-world
# docker run -it --rm --name rxmicro-native-hello-world -p 8080:8080 -e PRINT_ENV=true rxmicro/native-hello-world sh
#
# Push:
# docker push rxmicro/native-hello-world
#
# Test
# curl -X GET -v "http://localhost:8080/"
#
# Documentation links
# Java:     https://docs.oracle.com/en/java/javase/11/tools/java.html#GUID-3B1CE181-CD30-4178-9602-230B800D4FAE
# Docker:   https://docs.docker.com/engine/reference/run/
# ----------------------------------------------------------------------------------------------------------------
FROM rxmicro/alpine-glibc

ADD ./HelloWorldMicroService /HelloWorldMicroService
CMD [ "/HelloWorldMicroService" ]