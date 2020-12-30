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

package io.rxmicro.util.graalvm;

import io.rxmicro.common.InvalidStateException;
import io.rxmicro.config.ConfigException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static io.rxmicro.json.JsonHelper.readJsonArray;
import static io.rxmicro.json.JsonHelper.readJsonObject;
import static io.rxmicro.json.JsonTypes.asJsonArray;
import static io.rxmicro.json.JsonTypes.asJsonObject;
import static java.util.Map.entry;

public class NativeConfigs {

    private final List<Map.Entry<Example, Object>> jni;

    private final List<Map.Entry<Example, Object>> proxy;

    private final List<Map.Entry<Example, Object>> reflect;

    private final List<Map.Entry<Example, Object>> resource;

    private NativeConfigs(final List<Map.Entry<Example, Object>> jni,
                          final List<Map.Entry<Example, Object>> proxy,
                          final List<Map.Entry<Example, Object>> reflect,
                          final List<Map.Entry<Example, Object>> resource) {
        this.jni = jni;
        this.proxy = proxy;
        this.reflect = reflect;
        this.resource = resource;
    }

    public List<Map.Entry<Example, Object>> getJni() {
        return jni;
    }

    public List<Map.Entry<Example, Object>> getProxy() {
        return proxy;
    }

    public List<Map.Entry<Example, Object>> getReflect() {
        return reflect;
    }

    public List<Map.Entry<Example, Object>> getResource() {
        return resource;
    }

    public static NativeConfigs createNativeConfigs(final File groupGraalvmRootDir) throws IOException {
        final File[] projects = groupGraalvmRootDir.listFiles(File::isDirectory);
        if (projects == null) {
            throw new ConfigException("? is not group-graalvm root directory", groupGraalvmRootDir);
        }
        final Map<Category, List<Map.Entry<Example, File>>> fileMap = new EnumMap<>(Category.class);
        for (final File projectDir : projects) {
            final File graalDir = new File(projectDir, ".graal");
            if (!graalDir.exists()) {
                throw new InvalidStateException("Required '?' dir not found!", graalDir.getAbsolutePath());
            }
            final File[] files = graalDir.listFiles();
            if (files == null) {
                throw new InvalidStateException("'?' dir is empty!", graalDir.getAbsolutePath());
            }
            for (final File file : files) {
                final Example example = Example.of(projectDir.getName());
                fileMap.computeIfAbsent(Category.of(file.getName()), c -> new ArrayList<>()).add(entry(example, file));
            }
        }
        return merge(fileMap);
    }

    private static NativeConfigs merge(final Map<Category, List<Map.Entry<Example, File>>> fileMap) throws IOException {
        final List<Map.Entry<Example, Object>> jni = new ArrayList<>();
        final List<Map.Entry<Example, Object>> proxy = new ArrayList<>();
        final List<Map.Entry<Example, Object>> reflect = new ArrayList<>();
        final List<Map.Entry<Example, Object>> resource = new ArrayList<>();
        List<Map.Entry<Example, Object>> list;
        for (final Map.Entry<Category, List<Map.Entry<Example, File>>> entry : fileMap.entrySet()) {
            if (entry.getKey() == Category.resource) {
                for (final Map.Entry<Example, File> fileEntry : entry.getValue()) {
                    final List<Object> items = asJsonArray(
                            asJsonObject(
                                    readJsonObject(Files.readString(fileEntry.getValue().toPath()))
                                            .get("resources")
                            ).get("includes")
                    );
                    for (final Object item : items) {
                        resource.add(entry(fileEntry.getKey(), item));
                    }
                }
            } else {
                if (entry.getKey() == Category.jni) {
                    list = jni;
                } else if (entry.getKey() == Category.proxy) {
                    list = proxy;
                } else /*if (entry.getKey() == Category.reflect)*/ {
                    list = reflect;
                }
                for (final Map.Entry<Example, File> fileEntry : entry.getValue()) {
                    final List<Object> items = readJsonArray(Files.readString(fileEntry.getValue().toPath()));
                    for (final Object item : items) {
                        list.add(entry(fileEntry.getKey(), item));
                    }
                }
            }
        }
        return new NativeConfigs(jni, proxy, reflect, resource);
    }
}

