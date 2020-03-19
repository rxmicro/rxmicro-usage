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

package io.rxmicro.bencmark;

import io.rxmicro.bencmark.iterations.IterationBenchmark;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import static io.rxmicro.common.util.Formats.format;

public final class Launcher {

    public static void launchBenchmark(final Class<?> benchmarkClass) throws RunnerException {
        final Options opts = new OptionsBuilder().include(benchmarkClass.getName())
                .resultFormat(ResultFormatType.TEXT)
                .result(format("benchmarks/results/?.txt", benchmarkClass.getSimpleName()))
                .build();
        new Runner(opts).run();
    }

    public static void main(final String[] args) throws RunnerException {
        //launchBenchmark(RequestIdGeneratorBenchmark.class);
        //launchBenchmark(HelloWorldBenchmark.class);
        //launchBenchmark(InstantiationBenchmark.class);
        //launchBenchmark(ReadWriteFieldBenchmark.class);
        launchBenchmark(IterationBenchmark.class);
    }
}
