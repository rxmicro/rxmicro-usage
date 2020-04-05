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

package io.rxmicro.util.doc;

import io.rxmicro.common.RxMicroException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.rxmicro.common.util.ExCollectors.toOrderedMap;
import static io.rxmicro.common.util.Formats.format;
import static java.util.Map.entry;
import static java.util.stream.Collectors.joining;

public final class WordCount {

    private static final double FORMULA = 67.7 / 1_000.;

    private static final List<Map.Entry<String, String>> SECTION_ORDER = List.of(
            entry("common", "<shared-fragments>"),
            entry("introduction", "Introduction"),
            entry("microservice", "What microservice is?"),
            entry("quick-start", "Quick Start"),
            entry("core", "Core Concepts"),
            entry("rest-controller", "REST Controller"),
            entry("rest-client", "REST Client"),
            entry("validation", "Validation"),
            entry("project-documentation", "REST-based microservice documentation"),
            entry("data-postgresql", "Postgre SQL Data Repositories"),
            entry("data-mongo", "Mongo Data Repositories"),
            entry("cdi", "Contexts and Dependency Injection"),
            entry("java-integration", "Java EcoSystem Integration"),
            entry("testing", "Testing"),
            entry("appendices", "Appendices")
    );

    private static final String RX_MICRO_HOME = "RX_MICRO_HOME";

    private static final String RX_MICRO_HOME_VALUE = Optional.ofNullable(System.getenv(RX_MICRO_HOME)).orElseThrow(() -> {
        throw new RxMicroException("System variable '?' not defined", RX_MICRO_HOME);
    });

    private static final File DOC_ROOT = new File(format("?/rxmicro-usage/documentation/src/main/asciidoc/_fragment/", RX_MICRO_HOME_VALUE));

    private static Map<String, List<File>> resolveDocSections() {
        final Map<String, List<File>> map = new LinkedHashMap<>();
        final File[] files = DOC_ROOT.listFiles();
        if (files == null) {
            throw new IllegalStateException("Folder not found: " + DOC_ROOT.getAbsolutePath());
        }
        for (final File file : files) {
            if (file.isFile()) {
                final String name = file.getName().replace(".adoc", "");
                if (!"___fragment-settings".equals(name)) {
                    final List<File> adocFiles = findAdocFiles(name, true);
                    map.put(name, adocFiles);
                }
            }
        }
        map.put("common", findAdocFiles("common", false));
        return map;
    }

    private static List<File> findAdocFiles(final String name,
                                            final boolean withOwner) {
        final List<File> files = new ArrayList<>();
        if (withOwner) {
            files.add(new File(DOC_ROOT, name + ".adoc"));
        }
        final List<File> dirs = getSearchDirs(name);
        addFilesFromDirs(dirs, files);
        return files;
    }

    private static List<File> getSearchDirs(final String name) {
        if ("common".equals(name)) {
            return List.of(
                    new File(DOC_ROOT.getAbsolutePath() + "/___notes"),
                    new File(DOC_ROOT.getAbsolutePath() + "/___shared")
            );
        } else if ("rest-controller".equals(name)) {
            return List.of(
                    new File(DOC_ROOT.getAbsolutePath() + "/_rest"),
                    new File(DOC_ROOT.getAbsolutePath() + "/_" + name)
            );
        } else if ("microservice".equals(name)) {
            return List.of();
        } else {
            return List.of(new File(DOC_ROOT.getAbsolutePath() + "/_" + name));
        }
    }

    private static void addFilesFromDirs(final List<File> dirs,
                                         final List<File> files) {
        for (final File dir : dirs) {
            for (final File file : Objects.requireNonNull(dir.listFiles(), "Dir not found: " + dir.getAbsolutePath())) {
                if (file.isDirectory()) {
                    addFilesFromDirs(List.of(file), files);
                } else {
                    files.add(file);
                }
            }
        }
    }

    private static void displayStatisticsPerSection(final Map<String, List<File>> sections) {
        final Map<String, Map.Entry<List<File>, List<String>>> map = sections.entrySet().stream()
                .map(e -> entry(e.getKey(), convertToWordList(e.getValue())))
                .collect(toOrderedMap(Map.Entry::getKey, Map.Entry::getValue));
        int totalWords = 0;
        int totalChars = 0;
        int total = 0;
        for (final Map.Entry<String, String> section : SECTION_ORDER) {
            final Map.Entry<List<File>, List<String>> entry = map.remove(section.getKey());
            final List<String> value = entry.getValue();
            final int words = value.size();
            final int chars = value.stream().mapToInt(String::length).sum();
            final int sum = (int) (chars * FORMULA);
            System.out.println(format(
                    "[?]:\n\t? words, (? characters) -> ?\n\tFiles:\n\t\t?",
                    section.getValue(),
                    words,
                    chars,
                    sum,
                    entry.getKey().stream()
                            .map(f -> f.getAbsolutePath().replace(DOC_ROOT.getAbsolutePath() + "/", ""))
                            .collect(joining("\n\t\t"))
                    )
            );
            totalChars += chars;
            totalWords += words;
            total += sum;
            System.out.println("---------------------------------------------------------------------------------------------------------");
        }

        if (!map.isEmpty()) {
            throw new IllegalStateException("map is not empty:" + map.keySet());
        }

        System.out.println(format("[Total]:\n\t? words, (? characters) -> ?.", totalWords, totalChars, total));
    }

    private static Map.Entry<List<File>, List<String>> convertToWordList(final List<File> list) {
        final List<String> lines = new ArrayList<>();
        for (final File file : list) {
            try {
                lines.addAll(Files.readAllLines(file.toPath()));
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
        return entry(list, lines.stream().flatMap(l -> getWords(l).stream()).collect(Collectors.toList()));
    }

    private static List<String> getWords(final String line) {
        final List<String> words = new ArrayList<>();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            final char ch = line.charAt(i);
            if (" ;:.,/?|~`!@#$%^&*()_+=-{}[]<>\\\"'\n\t\r".indexOf(ch) != -1) {
                if (sb.length() > 0) {
                    addWord(sb.toString(), words);
                    sb.delete(0, sb.length());
                }
            } else {
                sb.append(ch);
            }
        }
        return words;
    }

    private static void addWord(final String word,
                                final List<String> words) {
        for (int i = 0; i < word.length(); i++) {
            final char ch = word.charAt(i);
            if (ch > 127) {
                words.add(word);
                return;
            }
        }
    }

    public static void main(final String[] args) {
        final Map<String, List<File>> sections = resolveDocSections();
        displayStatisticsPerSection(sections);
    }
}
