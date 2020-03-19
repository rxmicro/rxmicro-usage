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

package io.rxmicro.bencmark.rest.server;

import io.rxmicro.rest.server.local.component.impl.FasterButUnSafeRequestIdGenerator;
import io.rxmicro.rest.server.local.component.impl.SafeButSlowerRequestIdGenerator;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

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
@OutputTimeUnit(NANOSECONDS)
@Threads(4)
public class RequestIdGeneratorBenchmark {

    private FasterButUnSafeRequestIdGenerator fasterButUnSafeRequestIdGenerator;

    private SafeButSlowerRequestIdGenerator safeButSlowerRequestIdGenerator;

    @Setup
    public void setup() {
        safeButSlowerRequestIdGenerator = new SafeButSlowerRequestIdGenerator();
        fasterButUnSafeRequestIdGenerator = new FasterButUnSafeRequestIdGenerator();
    }

    @Benchmark
    public void safeButSlower() {
        safeButSlowerRequestIdGenerator.getNextId();
    }

    @Benchmark
    public void fasterButUnSafe() {
        fasterButUnSafeRequestIdGenerator.getNextId();
    }
}
// end::content[]
