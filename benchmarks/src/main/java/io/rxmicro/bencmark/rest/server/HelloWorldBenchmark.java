/*
 * Copyright (c) 2020. https://rxmicro.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.rxmicro.bencmark.rest.server;

import io.rxmicro.bencmark.rest.server.helloWorldSourceCode.HelloWorldMicroService;
import io.rxmicro.rest.server.ServerInstance;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static io.rxmicro.rest.server.RxMicro.startRestServer;
import static java.net.http.HttpClient.Version.HTTP_1_1;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(value = 2, jvmArgsAppend = "-server")
@BenchmarkMode({
        Mode.Throughput,
        Mode.AverageTime,
        Mode.SingleShotTime
})
@State(Scope.Benchmark)
@OutputTimeUnit(MILLISECONDS)
@Threads(1)
public class HelloWorldBenchmark {

    private ServerInstance serverInstance;

    private HttpClient httpClient;

    private HttpRequest request;

    @Setup
    public void setup() {
        serverInstance = startRestServer(HelloWorldMicroService.class);
        httpClient = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/"))
                .timeout(Duration.ofSeconds(10))
                .version(HTTP_1_1)
                .GET()
                .build();
    }

    @Benchmark
    public void showHelloWorld() {
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding()).join();
    }

    @TearDown
    public void tearDown() throws InterruptedException {
        serverInstance.shutdownAndWait();
    }
}
