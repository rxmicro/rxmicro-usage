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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toUnmodifiableMap;

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
public class HashMapOrForeachOnStringWith9ValuesBenchmark {

    private static final List<String> TEST_9_DATA = List.of(
            "first", "second", "third", "fourth", "fifth",
            "sixth", "seventh", "eighth", "ninth"
    );

    private final ArrayList<String> arrayList9 = new ArrayList<>(TEST_9_DATA);

    private final List<String> unmodifiedList9 = TEST_9_DATA;

    private final HashMap<String, String> hashMap9 =
            new HashMap<>(TEST_9_DATA.stream().map(e -> entry(e, e)).collect(toMap(Map.Entry::getKey, Map.Entry::getValue)));

    private final Map<String, String> unmodifiedMap9 =
            TEST_9_DATA.stream().map(e -> entry(e, e)).collect(toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

    @Benchmark
    public void arrayList9Search() {
        for (final String s : arrayList9) {
            if ("first".equals(s)) {
                break;
            }
        }
        for (final String s : arrayList9) {
            if ("fifth".equals(s)) {
                break;
            }
        }
        for (final String s : arrayList9) {
            if ("ninth".equals(s)) {
                break;
            }
        }
        for (final String s : arrayList9) {
            if ("not_found".equals(s)) {
                break;
            }
        }
    }

    @Benchmark
    public void unmodifiedList9Search() {
        for (final String s : unmodifiedList9) {
            if ("first".equals(s)) {
                break;
            }
        }
        for (final String s : unmodifiedList9) {
            if ("fifth".equals(s)) {
                break;
            }
        }
        for (final String s : unmodifiedList9) {
            if ("ninth".equals(s)) {
                break;
            }
        }
        for (final String s : unmodifiedList9) {
            if ("not_found".equals(s)) {
                break;
            }
        }
    }

    @Benchmark
    public void hashMap9Search() {
        hashMap9.get("first");
        hashMap9.get("fifth");
        hashMap9.get("ninth");
        hashMap9.get("not_found");
    }

    @Benchmark
    public void unmodifiedMap9Search() {
        unmodifiedMap9.get("first");
        unmodifiedMap9.get("fifth");
        unmodifiedMap9.get("ninth");
        unmodifiedMap9.get("not_found");
    }
}

