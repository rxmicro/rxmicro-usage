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

package io.rxmicro.benchmark.performance.vertx.netty;

import io.rxmicro.benchmark.performance.AbstractPerformanceBenchmark;
import io.rxmicro.benchmark.performance.vertx.netty.code.HelloWorldVerticle;
import io.vertx.core.Vertx;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.RunnerException;

import java.util.concurrent.CountDownLatch;

public class VertxWithNettyTransportBenchmark extends AbstractPerformanceBenchmark {

    private Vertx vertx;

    @Setup
    public void setup() throws InterruptedException {
        vertx = Vertx.vertx();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        vertx.deployVerticle(new HelloWorldVerticle(), event -> countDownLatch.countDown());
        countDownLatch.await();
        setupHttpClient();
    }

    @Benchmark
    public void get() {
        sendGetRequest();
    }

    @TearDown
    public void tearDown() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        vertx.close(event -> countDownLatch.countDown());
        countDownLatch.await();
    }

    public static void main(final String[] args) throws RunnerException {
        launchBenchmark(VertxWithNettyTransportBenchmark.class);
    }
}
