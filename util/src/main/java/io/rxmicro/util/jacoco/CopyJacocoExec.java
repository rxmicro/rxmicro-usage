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

package io.rxmicro.util.jacoco;

import io.rxmicro.common.InvalidStateException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static io.rxmicro.common.util.Formats.format;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public final class CopyJacocoExec {

    private static final String RX_MICRO_HOME = "RX_MICRO_HOME";

    private static final String RX_MICRO_HOME_VALUE = Optional.ofNullable(System.getenv(RX_MICRO_HOME)).orElseThrow(() -> {
        throw new InvalidStateException("System variable '?' not defined", RX_MICRO_HOME);
    });

    private static final File CACHE = new File(format("?/rxmicro-usage/.cache", RX_MICRO_HOME_VALUE));

    private static final File REPORT = new File(format("?/rxmicro/rxmicro-annotation-processor/target", RX_MICRO_HOME_VALUE));

    private static void findJacoco(final File dir) throws IOException {
        final File[] files = dir.listFiles();
        if (files != null) {
            for (final File file : files) {
                if (file.isDirectory()) {
                    findJacoco(file);
                } else if ("jacoco.exec".equals(file.getName())) {
                    copyJacoco(file);
                }
            }
        }
    }

    private static void copyJacoco(final File file) throws IOException {
        final File parent = file.getParentFile().getParentFile();
        Files.copy(file.toPath(), Paths.get(CACHE.getAbsolutePath() + "/" + parent.getName() + "-" + file.getName()), REPLACE_EXISTING);
        Files.copy(file.toPath(), Paths.get(REPORT.getAbsolutePath() + "/" + parent.getName() + "-" + file.getName()), REPLACE_EXISTING);
    }

    public static void main(final String[] args) throws IOException {
        if (!CACHE.exists()) {
            if (!CACHE.mkdirs()) {
                throw new IOException("Can't mk dirs: " + CACHE);
            }
        }
        findJacoco(new File(format("?/rxmicro-usage", RX_MICRO_HOME_VALUE)).getAbsoluteFile());
        System.out.println("Completed");
    }
}
