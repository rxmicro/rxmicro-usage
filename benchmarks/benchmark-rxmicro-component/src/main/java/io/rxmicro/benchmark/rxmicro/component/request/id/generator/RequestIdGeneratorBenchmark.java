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

package io.rxmicro.benchmark.rxmicro.component.request.id.generator;

import io.rxmicro.rest.server.PredefinedRequestIdGeneratorProvider;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.feature.RequestIdGenerator;
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

    private RequestIdGenerator uuid128Bits;

    private RequestIdGenerator random96Bits;

    private RequestIdGenerator partlyRandom96Bits;

    private RequestIdGenerator deterministic96Bits;

    @Setup
    public void setup() {
        final RestServerConfig config = new RestServerConfig();
        uuid128Bits = PredefinedRequestIdGeneratorProvider.UUID_128_BITS.getRequestIdGenerator(config);
        random96Bits = PredefinedRequestIdGeneratorProvider.RANDOM_96_BITS.getRequestIdGenerator(config);
        partlyRandom96Bits = PredefinedRequestIdGeneratorProvider.PARTLY_RANDOM_96_BITS.getRequestIdGenerator(config);
        deterministic96Bits = PredefinedRequestIdGeneratorProvider.DETERMINISTIC_96_BITS.getRequestIdGenerator(config);
    }

    @Benchmark
    public void uuid128Bits() {
        uuid128Bits.getNextId();
    }

    @Benchmark
    public void random96Bits() {
        random96Bits.getNextId();
    }

    @Benchmark
    public void partlyRandom96Bits() {
        partlyRandom96Bits.getNextId();
    }

    @Benchmark
    public void deterministic96Bits() {
        deterministic96Bits.getNextId();
    }
}
// end::content[]

