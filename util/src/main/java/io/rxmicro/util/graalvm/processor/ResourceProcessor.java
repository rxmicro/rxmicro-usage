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

package io.rxmicro.util.graalvm.processor;

import io.rxmicro.common.ImpossibleException;
import io.rxmicro.common.RxMicroModule;
import io.rxmicro.common.model.MapBuilder;
import io.rxmicro.util.graalvm.Category;
import io.rxmicro.util.graalvm.Example;
import io.rxmicro.util.graalvm.GraalVmProcessor;
import io.rxmicro.util.graalvm.MissingProcessingLogicException;
import io.rxmicro.util.graalvm.RxMicroNativeImageResource;

import java.io.File;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static io.rxmicro.common.RxMicroModule.RX_MICRO_DATA_SQL_R2DBC_POSTGRESQL_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_NETTY_NATIVE_ALL_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_NETTY_NATIVE_LINUX_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_NETTY_NATIVE_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_NETTY_NATIVE_OSX_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_REST_SERVER_NETTY_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_RUNTIME_MODULE;
import static io.rxmicro.json.JsonTypes.asJsonObject;
import static java.util.Map.entry;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

public class ResourceProcessor implements GraalVmProcessor {

    private final Set<String> ignoredResources = Set.of(
            "\\Qjul.properties\\E",
            "\\Qjul.test.properties\\E"
    );

    private final Map<String, List<RxMicroModule>> notStandardModules = Map.ofEntries(
            entry(
                    "\\QMETA-INF/maven/io.rxmicro/rxmicro-runtime/pom.properties\\E",
                    List.of(RX_MICRO_RUNTIME_MODULE)
            ),
            entry(
                    "\\Qmime-types.properties\\E",
                    List.of(RX_MICRO_REST_SERVER_NETTY_MODULE)
            ),
            entry(
                    "\\QMETA-INF/services/io.r2dbc.postgresql.extension.Extension\\E",
                    List.of(RX_MICRO_DATA_SQL_R2DBC_POSTGRESQL_MODULE)
            ),

            entry(
                    "\\QMETA-INF/native/libnetty_transport_native_epoll_x86_64.so\\E",
                    List.of(RX_MICRO_NETTY_NATIVE_LINUX_MODULE, RX_MICRO_NETTY_NATIVE_MODULE, RX_MICRO_NETTY_NATIVE_ALL_MODULE)
            ),
            entry(
                    "\\QMETA-INF/native/libnetty_transport_native_kqueue_x86_64.jnilib\\E",
                    List.of(RX_MICRO_NETTY_NATIVE_OSX_MODULE, RX_MICRO_NETTY_NATIVE_MODULE, RX_MICRO_NETTY_NATIVE_ALL_MODULE)
            )
    );

    @Override
    public List<RxMicroNativeImageResource> process(final File rxMicroHome,
                                                    final List<Map.Entry<Example, Object>> data) {
        final Set<String> resources = data.stream()
                .map(o -> asJsonObject(o.getValue()).get("pattern").toString())
                .filter(v -> !ignoredResources.contains(v))
                .collect(toSet());
        final Map<RxMicroModule, Set<String>> resourceMap = new EnumMap<>(RxMicroModule.class);
        for (final String resource : resources) {
            final List<RxMicroModule> rxMicroModules = notStandardModules.get(resource);
            if (rxMicroModules != null) {
                for (final RxMicroModule rxMicroModule : rxMicroModules) {
                    resourceMap.computeIfAbsent(rxMicroModule, m -> new TreeSet<>()).add(resource);
                }
            } else if (resource.startsWith("\\QMETA-INF/services/io.rxmicro.")) {
                final String resourcePath = resource.substring(2, resource.length() - 2);
                final File[] rxMicroProjects = requireNonNull(rxMicroHome.listFiles((dir, name) -> name.startsWith("rxmicro-")));
                boolean found = false;
                for (final File rxMicroProject : rxMicroProjects) {
                    if (new File(rxMicroProject.getAbsolutePath() + "/src/main/resources/" + resourcePath).exists()) {
                        resourceMap
                                .computeIfAbsent(
                                        RxMicroModule.of(rxMicroProject.getName().replace('-', '.')).orElseThrow(),
                                        m -> new TreeSet<>()
                                )
                                .add(resource);
                        found = true;
                    }
                }
                if (!found) {
                    throw new ImpossibleException("'?' resource not found!", resource);
                }
            } else {
                throw new MissingProcessingLogicException("Unsupported resource: '?'!", resource);
            }
        }
        addHardcodedStructuresIfNotDefined(resourceMap, resources);
        return resourceMap.entrySet().stream()
                .map(e -> new RxMicroNativeImageResource(
                                e.getKey(),
                                Category.resource,
                                new MapBuilder<String, Object>()
                                        .put("resources", new MapBuilder<String, Object>()
                                                .put("includes", e.getValue().stream()
                                                        .map(i -> Map.of("pattern", i))
                                                        .collect(Collectors.toList())
                                                )
                                                .build())
                                        .put("bundles", List.of())
                                        .build()
                        )
                )
                .collect(Collectors.toList());
    }

    private void addHardcodedStructuresIfNotDefined(final Map<RxMicroModule, Set<String>> resourceMap,
                                                    final Set<String> resources) {
        for (final Map.Entry<String, List<RxMicroModule>> entry : notStandardModules.entrySet()) {
            if (!resources.contains(entry.getKey())) {
                for (final RxMicroModule rxMicroModule : entry.getValue()) {
                    resourceMap.computeIfAbsent(rxMicroModule, m -> new TreeSet<>()).add(entry.getKey());
                }
            }
        }
    }
}

