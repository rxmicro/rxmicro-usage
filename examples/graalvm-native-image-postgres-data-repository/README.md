https://github.com/oracle/graal/blob/master/substratevm/LIMITATIONS.md

# Start DB

```shell script
docker run -it --rm -p 5432:5432 -e POSTGRES_DB=db -e POSTGRES_USER=rxmicro -e POSTGRES_HOST_AUTH_METHOD=trust postgres:9.6.17-alpine
```

# Build native image

```shell script
native-image -H:+ReportExceptionStackTraces -H:+TraceClassInitialization --verbose -jar target/PostgresMicroService.jar
```