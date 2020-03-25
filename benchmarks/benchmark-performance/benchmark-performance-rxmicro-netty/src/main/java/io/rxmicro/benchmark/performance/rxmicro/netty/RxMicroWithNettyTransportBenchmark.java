/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.benchmark.performance.rxmicro.netty;

import io.rxmicro.benchmark.performance.AbstractPerformanceBenchmark;
import io.rxmicro.benchmark.performance.rxmicro.netty.code.HelloWorldMicroService;
import io.rxmicro.rest.server.ServerInstance;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.RunnerException;

import static io.rxmicro.rest.server.RxMicro.startRestServer;

public class RxMicroWithNettyTransportBenchmark extends AbstractPerformanceBenchmark {

    private ServerInstance serverInstance;

    @Setup
    public void setup() {
        serverInstance = startRestServer(HelloWorldMicroService.class);
        setupHttpClient();
    }

    @Benchmark
    public void get() {
        sendGetRequest();
    }

    @TearDown
    public void tearDown() throws InterruptedException {
        serverInstance.shutdownAndWait();
    }

    public static void main(final String[] args) throws RunnerException {
        launchBenchmark(RxMicroWithNettyTransportBenchmark.class);
    }
}
