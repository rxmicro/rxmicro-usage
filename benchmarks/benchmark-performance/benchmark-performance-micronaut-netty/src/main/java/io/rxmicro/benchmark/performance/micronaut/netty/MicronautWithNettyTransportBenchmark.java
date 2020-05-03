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

package io.rxmicro.benchmark.performance.micronaut.netty;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;
import io.rxmicro.benchmark.performance.AbstractPerformanceBenchmark;
import io.rxmicro.benchmark.performance.micronaut.netty.code.HelloWorldMicroService;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.RunnerException;

public class MicronautWithNettyTransportBenchmark extends AbstractPerformanceBenchmark {

    private ApplicationContext applicationContext;

    @Setup
    public void setup() {
        applicationContext = Micronaut.run(HelloWorldMicroService.class);
        setupHttpClient();
    }

    @Benchmark
    public void get() {
        sendGetRequest();
    }

    @TearDown
    public void tearDown() {
        applicationContext.stop();
    }

    public static void main(final String[] args) throws RunnerException {
        launchBenchmark(MicronautWithNettyTransportBenchmark.class);
    }
}
