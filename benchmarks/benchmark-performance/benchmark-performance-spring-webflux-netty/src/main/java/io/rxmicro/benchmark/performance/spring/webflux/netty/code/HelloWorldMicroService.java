package io.rxmicro.benchmark.performance.spring.webflux.netty.code;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.CompletableFuture;

@Controller
@SpringBootApplication
public class HelloWorldMicroService {

    @GetMapping("/")
    @ResponseBody
    public CompletableFuture<Response> get() {
        return CompletableFuture.completedFuture(new Response("Hello World"));
    }
}
