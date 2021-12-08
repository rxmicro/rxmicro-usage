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

package io.rxmicro.benchmark.performance.rxmicro.epoll;

import io.netty.util.ResourceLeakDetector;
import io.rxmicro.benchmark.performance.AbstractPerformanceBenchmark;
import io.rxmicro.benchmark.performance.rxmicro.epoll.code.HelloWorldMicroService;
import io.rxmicro.rest.server.ServerInstance;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.RunnerException;

import static io.rxmicro.rest.server.RxMicroRestServer.startRestServer;

public class RxMicroWithEpollTransportBenchmark extends AbstractPerformanceBenchmark {

    static {
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED);
    }

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
        launchBenchmark(RxMicroWithEpollTransportBenchmark.class);
    }
}
