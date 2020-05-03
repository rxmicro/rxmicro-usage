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

package io.rxmicro.benchmark.performance;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

import static java.lang.String.format;
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
public abstract class AbstractPerformanceBenchmark {

    private static final String RESULT_FOLDER = format(
            "%s/rxmicro-usage/benchmarks/_results/",
            Optional.ofNullable(System.getenv("RX_MICRO_HOME")).orElseThrow(() -> {
                throw new IllegalStateException("System variable 'RX_MICRO_HOME' not defined");
            })
    );

    private HttpClient httpClient;

    private HttpRequest request;

    public static void launchBenchmark(final Class<?> benchmarkClass) throws RunnerException {
        final Options opts = new OptionsBuilder().include(benchmarkClass.getName())
                .resultFormat(ResultFormatType.TEXT)
                .result(format("%sperformance-%s.txt", RESULT_FOLDER, benchmarkClass.getSimpleName()))
                .build();
        new Runner(opts).run();
    }

    protected final void setupHttpClient() {
        httpClient = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/"))
                .timeout(Duration.ofSeconds(10))
                .version(HTTP_1_1)
                .GET()
                .build();
    }

    protected final void sendGetRequest() {
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding()).join();
    }
}
