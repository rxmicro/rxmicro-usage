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

package io.rxmicro.util.doc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.ToIntFunction;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.util.CommonSettings.RX_MICRO_WORKSPACE_HOME;
import static io.rxmicro.util.doc.WordUtils.getWords;

public final class WordStatistics {

    private static final File DOC_ROOT = new File(format("?/rxmicro-usage/documentation/src/main/asciidoc/_fragment/", RX_MICRO_WORKSPACE_HOME));

    private static void findAllLines(final File dir, final List<String> allLines) throws IOException {
        for (final File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                findAllLines(file, allLines);
            } else {
                allLines.addAll(Files.readAllLines(file.toPath()));
            }
        }
    }

    public static void main(final String[] args) throws IOException {
        final List<String> lines = new ArrayList<>();
        findAllLines(DOC_ROOT, lines);
        final Map<String, Integer> map = new HashMap<>();
        for (final String line : lines) {
            for (final String word : getWords(line)) {
                map.merge(word, 1, Integer::sum);
            }
        }
        map.entrySet().stream()
                .sorted(Comparator.comparingInt((ToIntFunction<Map.Entry<String, Integer>>) Map.Entry::getValue)
                        .thenComparing(Map.Entry::getKey))
                .forEach(e -> System.out.println(e.getKey() + " = " + e.getValue()));
    }
}
