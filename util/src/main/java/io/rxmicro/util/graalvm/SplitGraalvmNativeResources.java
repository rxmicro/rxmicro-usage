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

import io.rxmicro.common.RxMicroModule;
import io.rxmicro.util.graalvm.json.JsonWriter;
import io.rxmicro.util.graalvm.processor.JniProcessor;
import io.rxmicro.util.graalvm.processor.ProxyProcessor;
import io.rxmicro.util.graalvm.processor.ReflectionProcessor;
import io.rxmicro.util.graalvm.processor.ResourceProcessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.rxmicro.common.RxMicroModule.RX_MICRO_SLF4J_PROXY_MODULE;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.util.CommonSettings.RX_MICRO_WORKSPACE_HOME;
import static io.rxmicro.util.graalvm.NativeConfigs.createNativeConfigs;
import static java.lang.System.lineSeparator;
import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.delete;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.walkFileTree;
import static java.nio.file.Files.writeString;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.groupingBy;

public final class SplitGraalvmNativeResources {

    public static void main(final String[] args) throws IOException {
        final File groupGraalvmRootDir = new File(format("?/rxmicro-usage/examples/group-graalvm", RX_MICRO_WORKSPACE_HOME));
        final NativeConfigs nativeConfigs = createNativeConfigs(groupGraalvmRootDir);
        final File rxMicroHome = new File(format("?/rxmicro", RX_MICRO_WORKSPACE_HOME));

        final List<RxMicroNativeImageResource> nativeImageResources = new ArrayList<>();
        nativeImageResources.addAll(new ResourceProcessor().process(rxMicroHome, nativeConfigs.getResource()));
        nativeImageResources.addAll(new ReflectionProcessor().process(rxMicroHome, nativeConfigs.getReflect()));
        nativeImageResources.addAll(new JniProcessor().process(rxMicroHome, nativeConfigs.getJni()));
        nativeImageResources.addAll(new ProxyProcessor().process(rxMicroHome, nativeConfigs.getProxy()));

        clearPreviousResourceFiles(rxMicroHome);
        final Map<RxMicroModule, List<RxMicroNativeImageResource>> nativeImageResourceMap = aggregate(nativeImageResources);
        generateResourceFiles(rxMicroHome, nativeImageResourceMap);

        System.out.println("Completed");
    }

    private static Map<RxMicroModule, List<RxMicroNativeImageResource>> aggregate(final List<RxMicroNativeImageResource> resources) {
        return resources.stream().collect(groupingBy(RxMicroNativeImageResource::getModule));
    }

    private static void clearPreviousResourceFiles(final File rxMicroHome) throws IOException {
        for (final RxMicroModule rxMicroModule : RxMicroModule.values()) {
            final File nativeImageDir = new File(
                    rxMicroHome.getAbsolutePath() + "/" + rxMicroModule.getDirectory() +
                            "/src/main/resources/META-INF/native-image"
            );
            clearDirectory(nativeImageDir);
            // delete META-INF if empty
            final File metaInf = nativeImageDir.getParentFile();
            if (metaInf.exists() && requireNonNull(metaInf.list()).length == 0) {
                delete(metaInf.toPath());
            }
        }
    }

    private static void generateResourceFiles(final File rxMicroHome,
                                              final Map<RxMicroModule, List<RxMicroNativeImageResource>> nativeImageResourceMap) throws IOException {
        final String propertyHeader = Files.readString(Paths.get(format("?/rxmicro-usage/util/src/main/resources/native-image-header.txt", RX_MICRO_WORKSPACE_HOME)));
        for (final Map.Entry<RxMicroModule, List<RxMicroNativeImageResource>> entry : nativeImageResourceMap.entrySet()) {
            final RxMicroModule rxMicroModule = entry.getKey();
            final File nativeImageDir = new File(
                    rxMicroHome.getAbsolutePath() + "/" + rxMicroModule.getDirectory() +
                            "/src/main/resources/META-INF/native-image"
            );
            final Path rootConfigDir = createDirectories(Paths.get(nativeImageDir.getAbsolutePath(), "io.rxmicro", rxMicroModule.getDirectory()));
            final List<String> propValues = new ArrayList<>();
            for (final RxMicroNativeImageResource rxMicroNativeImageResource : entry.getValue()) {
                for (final Map.Entry<String, Object> model : rxMicroNativeImageResource.getJsonModels()) {
                    final String content = JsonWriter.toJsonString(model.getValue());
                    writeString(rootConfigDir.resolve(model.getKey() + ".json"), content + lineSeparator());
                    propValues.add(format("-H:?=${.}/?", rxMicroNativeImageResource.getCategory().getInstruction(), model.getKey() + ".json"));
                }
            }
            writeString(
                    rootConfigDir.resolve("native-image.properties"),
                    propertyHeader + "Args =  " + String.join(" \\\n        ", propValues) + lineSeparator()
            );
            System.out.println(format("'?' updated!", rxMicroModule.getName()));
        }
        addCustomSettings(rxMicroHome, propertyHeader);
    }

    private static void addCustomSettings(final File rxMicroHome,
                                          final String propertyHeader) throws IOException {
        //Add --initialize-at-build-time=org.slf4j.LoggerFactory to RX_MICRO_SLF4J_PROXY_MODULE
        {
            final File nativeImageDir = new File(
                    rxMicroHome.getAbsolutePath() + "/" + RX_MICRO_SLF4J_PROXY_MODULE.getDirectory() +
                            "/src/main/resources/META-INF/native-image"
            );
            final Path rootConfigDir = createDirectories(Paths.get(nativeImageDir.getAbsolutePath(), "io.rxmicro", RX_MICRO_SLF4J_PROXY_MODULE.getDirectory()));
            writeString(
                    rootConfigDir.resolve("native-image.properties"),
                    propertyHeader + "Args =  --initialize-at-build-time=org.slf4j.LoggerFactory" + lineSeparator()
            );
        }
    }

    private static void clearDirectory(final File nativeImageDir) throws IOException {
        if (exists(nativeImageDir.toPath())) {
            walkFileTree(nativeImageDir.toPath(), new SimpleFileVisitor<>() {

                @Override
                public FileVisitResult visitFile(final Path file,
                                                 final BasicFileAttributes attrs) throws IOException {
                    delete(file);
                    return CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(final Path dir,
                                                          final IOException exc) throws IOException {
                    delete(dir);
                    return CONTINUE;
                }
            });
        }
    }
}

