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

package io.rxmicro.benchmark.reactive.lib;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@SuppressWarnings("ResultOfMethodCallIgnored")
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
public class ReactiveLibAsyncBenchmark {

    private static final String DATA = "data";

    @Benchmark
    public void completableFuture() {
        CompletableFuture.supplyAsync(() -> DATA).join();
    }

    @Benchmark
    public void mono() {
        Mono.defer(() -> Mono.just(DATA)).block();
    }

    @Benchmark
    public void flux() {
        Flux.defer(() -> Flux.just(DATA)).blockFirst();
    }

    @Benchmark
    public void single() {
        Single.defer(() -> Single.just(DATA)).blockingGet();
    }

    @Benchmark
    public void maybe() {
        Maybe.defer(() -> Maybe.just(DATA)).blockingGet();
    }

    @Benchmark
    public void completable() {
        Completable.defer(Completable::complete).blockingAwait();
    }

    @Benchmark
    public void flowable() {
        Flowable.defer(()-> Flowable.just(DATA)).blockingFirst();
    }
}
