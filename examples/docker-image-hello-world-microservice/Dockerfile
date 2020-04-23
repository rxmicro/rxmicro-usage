# Build:
# docker build -t rxmicro/simple-hello-world .
#
# Run:
# docker run -it --rm --name rxmicro-simple-hello-world -p 8080:8080 rxmicro/simple-hello-world
# docker run -it --rm --name rxmicro-simple-hello-world -p 8080:8080 rxmicro/simple-hello-world sh
#
# Push:
# docker push rxmicro/simple-hello-world
#
# Test
# curl -X GET -v "http://localhost:8080/"
#
# Documentation links
# Java:     https://docs.oracle.com/en/java/javase/11/tools/java.html#GUID-3B1CE181-CD30-4178-9602-230B800D4FAE
# Docker:   https://docs.docker.com/engine/reference/run/
# ----------------------------------------------------------------------------------------------------------------
FROM rxmicro/jre:11.0.7-alpine

ARG WORKDIR=/opt/rxmicro
COPY ./src/main/sh/start.sh ${WORKDIR}/
COPY ./target/*.jar ${WORKDIR}/
COPY ./target/lib/ ${WORKDIR}/lib/

WORKDIR ${WORKDIR}
EXPOSE 8080
ENV PATH ${WORKDIR}:$PATH
ENV MICROSERVICE_NAME simple-hello-world

RUN chmod +x start.sh
CMD [ "start.sh" ]