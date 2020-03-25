package io.rxmicro.benchmark.performance.micronaut.netty.code;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

import java.util.concurrent.CompletableFuture;

@Controller("/")
public class HelloWorldMicroService {

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public CompletableFuture<Response> get() {
        return CompletableFuture.completedFuture(new Response("Hello World"));
    }
}
