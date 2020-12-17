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

package io.rxmicro.benchmark.performance.rxmicro.nativeimage;

import io.rxmicro.benchmark.performance.AbstractPerformanceBenchmark;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.RunnerException;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RxMicroNativeImageBenchmark extends AbstractPerformanceBenchmark {

    private Process process;

    @Setup
    public void setup() throws InterruptedException, IOException {
        process = Runtime.getRuntime().exec(
                new String[]{System.getenv("RX_MICRO_WORKSPACE_HOME") +
                        "/rxmicro-usage/benchmarks/benchmark-performance/" +
                        "benchmark-performance-rxmicro-native/HelloWorldMicroService"},
                null,
                new File(".")
        );
        process.waitFor(1, TimeUnit.SECONDS);
        setupHttpClient();
    }

    @Benchmark
    public void get() {
        sendGetRequest();
    }

    @TearDown
    public void tearDown() {
        process.destroyForcibly();
    }

    public static void main(final String[] args) throws RunnerException {
        launchBenchmark(RxMicroNativeImageBenchmark.class);
    }
}
