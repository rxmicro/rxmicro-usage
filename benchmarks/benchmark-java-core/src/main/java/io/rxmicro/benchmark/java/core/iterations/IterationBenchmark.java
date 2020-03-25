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

package io.rxmicro.benchmark.java.core.iterations;

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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.stream.Collectors.toList;

// tag::content[]
@SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "UseBulkOperation"})
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
public class IterationBenchmark {

    private static final int SIZE = 20;

    private final List<Integer> list = IntStream.range(0, SIZE).boxed().collect(toList());

    @Benchmark
    @SuppressWarnings({"ForLoopReplaceableByForEach"})
    public void usingIndex() {
        final List<Integer> copy = new ArrayList<>(SIZE);
        for (int i = 0; i < list.size(); i++) {
            copy.add(list.get(i));
        }
    }

    @Benchmark
    public void usingIterator() {
        final List<Integer> copy = new ArrayList<>(SIZE);
        for (Integer integer : list) {
            copy.add(integer);
        }
    }

    @SuppressWarnings("Convert2MethodRef")
    @Benchmark
    public void usingForEachLambda() {
        final List<Integer> copy = new ArrayList<>(SIZE);
        list.forEach(v -> copy.add(v));
    }

    @Benchmark
    public void usingForEachMethodRef() {
        final List<Integer> copy = new ArrayList<>(SIZE);
        list.forEach(copy::add);
    }
}
// end::content[]
