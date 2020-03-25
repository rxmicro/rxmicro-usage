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

package io.rxmicro.benchmark.java.core.fields;

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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

// tag::content[]
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
public class ReadWriteFieldBenchmark {

    private final CustomClass customClass = new CustomClass("text");

    private final Map<Class<?>, Map<String, Field>> cache = new HashMap<>();

    public ReadWriteFieldBenchmark() {
        try {
            final Field field = CustomClass.class.getDeclaredField("value");
            if (!field.canAccess(customClass)) {
                field.setAccessible(true);
            }
            cache.put(CustomClass.class, new HashMap<>(Map.of("value", field)));
        } catch (final NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Benchmark
    public void readDirectField() {
        var v = customClass.value;
    }

    @Benchmark
    public void writeDirectField() {
        customClass.value = "string";
    }

    @Benchmark
    public void readUsingGetter() {
        var v = customClass.getValue();
    }

    @Benchmark
    public void writeUsingSetter() {
        customClass.setValue("string");
    }

    @Benchmark // read field value using reflection with field search at the cache
    public void readUsingReflection() throws IllegalAccessException {
        var v = cache.get(CustomClass.class).get("value").get(customClass);
    }

    @Benchmark // write field value using reflection with field search at the cache
    public void writeUsingReflection() throws IllegalAccessException {
        cache.get(CustomClass.class).get("value").set(customClass, "string");
    }
}
// end::content[]
