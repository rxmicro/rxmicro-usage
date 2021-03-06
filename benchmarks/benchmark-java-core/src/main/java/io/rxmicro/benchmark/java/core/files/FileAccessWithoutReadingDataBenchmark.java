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

package io.rxmicro.benchmark.java.core.files;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Warmup(iterations = 2)
@Measurement(iterations = 3)
@Fork(value = 2, jvmArgsAppend = "-server")
@BenchmarkMode({
        Mode.Throughput,
        Mode.AverageTime,
        Mode.SingleShotTime
})
@State(Scope.Benchmark)
@OutputTimeUnit(MILLISECONDS)
@Threads(1)
public class FileAccessWithoutReadingDataBenchmark {

    private Path notFoundTempPath;

    private File notFoundTempFile;

    private Path tempPath;

    private File tempFile;

    @Setup
    public void setup() throws IOException {
        tempPath = Files.createTempFile("test", "file");
        tempFile = tempPath.toFile();

        notFoundTempPath = Paths.get(System.getProperty("java.io.tmpdir") + "/not_found.rxmicro");
        notFoundTempFile = notFoundTempPath.toFile();
        Files.writeString(tempPath, "Hello world");
    }

    @Benchmark
    public void Path_exists_found() {
        Files.exists(tempPath);
    }

    @Benchmark
    public void Path_exists_not_found() {
        Files.exists(notFoundTempPath);
    }

    @Benchmark
    public void File_exists_found() {
        tempFile.exists();
    }

    @Benchmark
    public void File_exists_not_found() {
        notFoundTempFile.exists();
    }

    @Benchmark
    public void Path_size() throws IOException {
        Files.size(tempPath);
    }

    @Benchmark
    public void File_size() {
        tempFile.length();
    }

    @TearDown
    public void clear() throws IOException {
        Files.deleteIfExists(tempPath);
    }
}

