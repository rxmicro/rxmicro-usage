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

import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.kqueue.KQueueSocketChannel;
import io.rxmicro.common.RxMicroModule;
import io.rxmicro.common.model.MapBuilder;
import io.rxmicro.json.JsonArrayBuilder;
import io.rxmicro.logger.jul.SystemConsoleHandler;
import io.rxmicro.util.graalvm.Category;
import io.rxmicro.util.graalvm.Example;
import io.rxmicro.util.graalvm.GraalVmProcessor;
import io.rxmicro.util.graalvm.MissingProcessingLogicException;
import io.rxmicro.util.graalvm.RxMicroNativeImageResource;
import io.rxmicro.util.graalvm.processor.reflection.JsonConfigMerger;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static io.rxmicro.common.RxMicroModule.RX_MICRO_DATA_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_DATA_MONGO_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_DATA_SQL_R2DBC_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_DATA_SQL_R2DBC_POSTGRESQL_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_NETTY_NATIVE_ALL_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_NETTY_NATIVE_LINUX_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_NETTY_NATIVE_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_NETTY_NATIVE_OSX_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_NETTY_RUNTIME_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_REST_CLIENT_JDK_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_REST_CLIENT_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_REST_CLIENT_NETTY_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_REST_SERVER_MODULE;
import static io.rxmicro.common.RxMicroModule.RX_MICRO_REST_SERVER_NETTY_MODULE;
import static io.rxmicro.json.JsonTypes.asJsonObject;
import static io.rxmicro.json.JsonTypes.asJsonString;
import static io.rxmicro.util.graalvm.processor.reflection.HardcodedStructures.EPOLL_EVENT_LOOP_GROUP;
import static io.rxmicro.util.graalvm.processor.reflection.HardcodedStructures.EPOLL_SERVER_SOCKET_CHANNEL;
import static io.rxmicro.util.graalvm.processor.reflection.HardcodedStructures.EPOLL_SOCKET_CHANNEL;
import static io.rxmicro.util.graalvm.processor.reflection.HardcodedStructures.K_QUEUE_EVENT_LOOP_GROUP;
import static io.rxmicro.util.graalvm.processor.reflection.HardcodedStructures.K_QUEUE_SERVER_SOCKET_CHANNEL;
import static io.rxmicro.util.graalvm.processor.reflection.HardcodedStructures.K_QUEUE_SOCKET_CHANNEL;
import static io.rxmicro.util.graalvm.processor.reflection.HardcodedStructures.buildNormalizedConfigJsonObject;
import static java.util.Map.entry;

public class ReflectionProcessor implements GraalVmProcessor {

    private final Map<String, List<RxMicroModule>> customClassMapping = Map.ofEntries(
            entry(
                    Epoll.class.getName(),
                    List.of(RX_MICRO_NETTY_RUNTIME_MODULE)
            ),
            entry(
                    KQueue.class.getName(),
                    List.of(RX_MICRO_NETTY_RUNTIME_MODULE)
            ),
            entry(
                    "io.netty.util.internal.NativeLibraryUtil",
                    List.of(
                            RX_MICRO_NETTY_NATIVE_LINUX_MODULE, RX_MICRO_NETTY_NATIVE_OSX_MODULE,
                            RX_MICRO_NETTY_NATIVE_MODULE, RX_MICRO_NETTY_NATIVE_ALL_MODULE
                    )
            )
    );

    @Override
    public List<RxMicroNativeImageResource> process(final File rxMicroHome,
                                                    final List<Map.Entry<Example, Object>> data) {
        final Map<RxMicroModule, Map<String, List<Object>>> jsonModelMap = new EnumMap<>(RxMicroModule.class);
        final Set<Map<String, Object>> processedJsonObject = new HashSet<>();
        final Set<String> processedClasses = new HashSet<>();
        for (final Map.Entry<Example, Object> entry : new JsonConfigMerger().normalizeStructures(data)) {
            final Map<String, Object> jsonObject = asJsonObject(entry.getValue());
            if (processedJsonObject.add(jsonObject)) {
                final String name = asJsonString(jsonObject.get("name"));
                if (!name.startsWith("io.rxmicro.examples.")) {
                    if (processedClasses.add(name)) {
                        resolve(jsonModelMap, entry, jsonObject, name);
                    } else {
                        throw new MissingProcessingLogicException("Structure of '?' class is not unique!", name);
                    }
                }
            }
        }
        addHardcodedStructuresIfNotDefined(jsonModelMap, processedClasses);
        return jsonModelMap.entrySet().stream()
                .map(e -> new RxMicroNativeImageResource(e.getKey(), Category.reflect, e.getValue()))
                .collect(Collectors.toList());
    }

    private void resolve(final Map<RxMicroModule, Map<String, List<Object>>> jsonModelMap,
                         final Map.Entry<Example, Object> entry,
                         final Map<String, Object> jsonObject,
                         final String name) {
        final List<RxMicroModule> rxMicroModules = findRxMicroModules(name);
        if (!rxMicroModules.isEmpty()) {
            for (final RxMicroModule rxMicroModule : rxMicroModules) {
                resolveRxMicroClass(jsonModelMap, jsonObject, name, rxMicroModule);
            }
        } else if (name.startsWith("com.mongodb.")) {
            put(jsonModelMap, RX_MICRO_DATA_MONGO_MODULE, "mongo-reflection-config", jsonObject);
        } else if (name.startsWith("io.r2dbc.postgresql.")) {
            put(jsonModelMap, RX_MICRO_DATA_SQL_R2DBC_POSTGRESQL_MODULE, "r2dbc-reflection-config", jsonObject);
        } else if (name.startsWith("io.r2dbc.")) {
            put(jsonModelMap, RX_MICRO_DATA_SQL_R2DBC_MODULE, "r2dbc-reflection-config", jsonObject);
        } else if (name.startsWith("io.netty.buffer.")) {
            put(jsonModelMap, RX_MICRO_NETTY_RUNTIME_MODULE, "netty-buffer-reflection-config", jsonObject);
        } else if (name.startsWith("io.netty.channel.")) {
            put(jsonModelMap, RX_MICRO_NETTY_RUNTIME_MODULE, "netty-transport-reflection-config", jsonObject);
        } else if (name.startsWith("io.netty.util.")) {
            put(jsonModelMap, RX_MICRO_NETTY_RUNTIME_MODULE, "netty-common-reflection-config", jsonObject);
        } else if (name.startsWith("jdk.internal.")) {
            put(jsonModelMap, RX_MICRO_NETTY_RUNTIME_MODULE, "jdk-internal-reflection-config", jsonObject);
        } else {
            resolveBasedOnExampleName(jsonModelMap, entry, jsonObject, name);
        }
    }

    private void resolveRxMicroClass(final Map<RxMicroModule, Map<String, List<Object>>> jsonModelMap,
                                     final Map<String, Object> jsonObject,
                                     final String name,
                                     final RxMicroModule rxMicroModule) {
        if (SystemConsoleHandler.class.getName().equals(name)) {
            put(jsonModelMap, rxMicroModule, "rx-micro-reflection-config", new MapBuilder<String, Object>()
                    .put("name", name)
                    .put("methods", new JsonArrayBuilder()
                            .add(new MapBuilder<String, Object>()
                                    .put("name", "<init>")
                                    .put("parameterTypes", List.of())
                                    .build()
                            )
                            .build()
                    )
                    .build());
        } else if (name.endsWith("Config")) {
            final Map<String, Object> normalizedConfigJsonObject = buildNormalizedConfigJsonObject(name);
            put(jsonModelMap, rxMicroModule, "rx-micro-reflection-config", normalizedConfigJsonObject);
        } else if (name.startsWith("io.netty.channel.")) {
            put(jsonModelMap, rxMicroModule, "netty-transport-reflection-config", jsonObject);
        } else if (name.startsWith("io.netty.util.internal.NativeLibraryUtil")) {
            put(jsonModelMap, rxMicroModule, "reflection-config", jsonObject);
        } else {
            put(jsonModelMap, rxMicroModule, "rx-micro-reflection-config", jsonObject);
        }
    }

    private void resolveBasedOnExampleName(final Map<RxMicroModule, Map<String, List<Object>>> jsonModelMap,
                                           final Map.Entry<Example, Object> entry,
                                           final Map<String, Object> jsonObject,
                                           final String name) {
        if (entry.getKey() == Example.mongo) {
            resolveMongoDataRepositoryModule(jsonModelMap, jsonObject, name);
        } else if (entry.getKey() == Example.postgres) {
            resolvePostgresDataRepositoryModule(jsonModelMap, jsonObject, name);
        } else if (entry.getKey() == Example.rest_client_jdk) {
            resolveJDKRestClientModule(jsonModelMap, jsonObject, name);
        } else if (entry.getKey() == Example.rest_client_netty) {
            resolveNettyRestClientModule(jsonModelMap, jsonObject, name);
        } else if (entry.getKey() == Example.rest_server) {
            resolveNettyRestServerModule(jsonModelMap, jsonObject, name);
        } else {
            throw new MissingProcessingLogicException("Unsupported class name: '?'", name);
        }
    }

    private void resolveMongoDataRepositoryModule(final Map<RxMicroModule, Map<String, List<Object>>> jsonModelMap,
                                                  final Map<String, Object> jsonObject,
                                                  final String name) {
        if (name.startsWith("java.")) {
            put(jsonModelMap, RX_MICRO_DATA_MONGO_MODULE, "java-reflection-config", jsonObject);
        } else if (name.startsWith("sun.")) {
            put(jsonModelMap, RX_MICRO_DATA_MONGO_MODULE, "sun-reflection-config", jsonObject);
        } else if (name.startsWith("io.netty.handler.codec.")) {
            put(jsonModelMap, RX_MICRO_DATA_MONGO_MODULE, "netty-handler-reflection-config", jsonObject);
        } else if (name.startsWith("rxmicro.")) {
            put(jsonModelMap, RX_MICRO_DATA_MODULE, "rx-micro-reflection-config", jsonObject);
        } else if (name.startsWith("org.slf4j.")) {
            put(jsonModelMap, RX_MICRO_DATA_MONGO_MODULE, "slf4j-reflection-config", jsonObject);
        } else {
            throw new MissingProcessingLogicException("Unsupported class name: '?'", name);
        }
    }

    private void resolvePostgresDataRepositoryModule(final Map<RxMicroModule, Map<String, List<Object>>> jsonModelMap,
                                                     final Map<String, Object> jsonObject,
                                                     final String name) {
        if (name.startsWith("java.")) {
            put(jsonModelMap, RX_MICRO_DATA_SQL_R2DBC_POSTGRESQL_MODULE, "java-reflection-config", jsonObject);
        } else if (name.startsWith("sun.")) {
            put(jsonModelMap, RX_MICRO_DATA_SQL_R2DBC_POSTGRESQL_MODULE, "sun-reflection-config", jsonObject);
        } else if (name.startsWith("io.netty.handler.codec.")) {
            put(jsonModelMap, RX_MICRO_DATA_SQL_R2DBC_POSTGRESQL_MODULE, "netty-handler-reflection-config", jsonObject);
        } else if (name.startsWith("reactor.netty.")) {
            put(jsonModelMap, RX_MICRO_DATA_SQL_R2DBC_POSTGRESQL_MODULE, "reactor-netty-reflection-config", jsonObject);
        } else if (name.startsWith("rxmicro.")) {
            put(jsonModelMap, RX_MICRO_DATA_MODULE, "rx-micro-reflection-config", jsonObject);
        } else {
            throw new MissingProcessingLogicException("Unsupported class name: '?'", name);
        }
    }

    private void resolveJDKRestClientModule(final Map<RxMicroModule, Map<String, List<Object>>> jsonModelMap,
                                            final Map<String, Object> jsonObject,
                                            final String name) {
        if (name.startsWith("java.") || "byte[]".equals(name)) {
            put(jsonModelMap, RX_MICRO_REST_CLIENT_JDK_MODULE, "java-reflection-config", jsonObject);
        } else if (name.startsWith("javax.")) {
            put(jsonModelMap, RX_MICRO_REST_CLIENT_JDK_MODULE, "javax-reflection-config", jsonObject);
        } else if (name.startsWith("sun.") || name.startsWith("com.sun.")) {
            put(jsonModelMap, RX_MICRO_REST_CLIENT_JDK_MODULE, "sun-reflection-config", jsonObject);
        } else if (name.startsWith("rxmicro.")) {
            put(jsonModelMap, RX_MICRO_REST_CLIENT_MODULE, "rx-micro-reflection-config", jsonObject);
        } else {
            throw new MissingProcessingLogicException("Unsupported class name: '?'", name);
        }
    }

    private void resolveNettyRestClientModule(final Map<RxMicroModule, Map<String, List<Object>>> jsonModelMap,
                                              final Map<String, Object> jsonObject,
                                              final String name) {
        if (name.startsWith("rxmicro.")) {
            put(jsonModelMap, RX_MICRO_REST_CLIENT_MODULE, "rx-micro-reflection-config", jsonObject);
        } else if (name.startsWith("java.") || "byte[]".equals(name)) {
            put(jsonModelMap, RX_MICRO_REST_CLIENT_NETTY_MODULE, "java-reflection-config", jsonObject);
        } else if (name.startsWith("javax.")) {
            put(jsonModelMap, RX_MICRO_REST_CLIENT_NETTY_MODULE, "javax-reflection-config", jsonObject);
        } else if (name.startsWith("sun.") || name.startsWith("com.sun.")) {
            put(jsonModelMap, RX_MICRO_REST_CLIENT_NETTY_MODULE, "sun-reflection-config", jsonObject);
        } else if (name.startsWith("io.netty.handler.codec.http.")) {
            put(jsonModelMap, RX_MICRO_REST_CLIENT_NETTY_MODULE, "netty-codec-http-reflection-config", jsonObject);
        } else if (name.startsWith("io.netty.handler.codec.")) {
            put(jsonModelMap, RX_MICRO_REST_CLIENT_NETTY_MODULE, "netty-codec-reflection-config", jsonObject);
        } else if (name.startsWith("io.netty.handler.ssl.")) {
            put(jsonModelMap, RX_MICRO_REST_CLIENT_NETTY_MODULE, "netty-ssl-reflection-config", jsonObject);
        } else if (name.startsWith("io.netty.handler.timeout.")) {
            put(jsonModelMap, RX_MICRO_REST_CLIENT_NETTY_MODULE, "netty-timeout-reflection-config", jsonObject);
        } else if (name.startsWith("io.netty.resolver.dns.")) {
            put(jsonModelMap, RX_MICRO_REST_CLIENT_NETTY_MODULE, "netty-resolver-dns-reflection-config", jsonObject);
        } else if (name.startsWith("reactor.netty.")) {
            put(jsonModelMap, RX_MICRO_REST_CLIENT_NETTY_MODULE, "reactor-netty-reflection-config", jsonObject);
        } else {
            throw new MissingProcessingLogicException("Unsupported class name: '?'", name);
        }
    }

    private void resolveNettyRestServerModule(final Map<RxMicroModule, Map<String, List<Object>>> jsonModelMap,
                                              final Map<String, Object> jsonObject,
                                              final String name) {
        if (name.startsWith("java.")) {
            put(jsonModelMap, RX_MICRO_REST_SERVER_NETTY_MODULE, "java-reflection-config", jsonObject);
        } else if (name.startsWith("sun.")) {
            put(jsonModelMap, RX_MICRO_REST_SERVER_NETTY_MODULE, "sun-reflection-config", jsonObject);
        } else if (name.startsWith("rxmicro.")) {
            put(jsonModelMap, RX_MICRO_REST_SERVER_MODULE, "rx-micro-reflection-config", jsonObject);
        } else if (name.startsWith("io.netty.bootstrap.ServerBootstrap")) {
            put(jsonModelMap, RX_MICRO_REST_SERVER_NETTY_MODULE, "netty-bootstrap-reflection-config", jsonObject);
        } else if (name.startsWith("io.netty.handler.codec.http.")) {
            put(jsonModelMap, RX_MICRO_REST_SERVER_NETTY_MODULE, "netty-codec-http-reflection-config", jsonObject);
        } else if (name.startsWith("io.netty.handler.codec.")) {
            put(jsonModelMap, RX_MICRO_REST_SERVER_NETTY_MODULE, "netty-codec-reflection-config", jsonObject);
        } else {
            throw new MissingProcessingLogicException("Unsupported class name: '?'", name);
        }
    }

    private List<RxMicroModule> findRxMicroModules(final String className) {
        return Optional.ofNullable(customClassMapping.get(className)).orElseGet(() -> {
            final Set<RxMicroModule> orderedRxMicroModules = new TreeSet<>((o1, o2) -> o2.getRootPackage().compareTo(o1.getRootPackage()));
            orderedRxMicroModules.addAll(List.of(RxMicroModule.values()));
            for (final RxMicroModule rxMicroModule : orderedRxMicroModules) {
                if (className.startsWith(rxMicroModule.getRootPackage())) {
                    return List.of(rxMicroModule);
                }
            }
            return List.of();
        });
    }

    private void addHardcodedStructuresIfNotDefined(final Map<RxMicroModule, Map<String, List<Object>>> jsonModelMap,
                                                    final Set<String> processedClasses) {
        // EPoll
        if (!processedClasses.contains(EpollEventLoopGroup.class.getName())) {
            final Map<String, Object> jsonObject = EPOLL_EVENT_LOOP_GROUP;
            put(jsonModelMap, RX_MICRO_NETTY_NATIVE_LINUX_MODULE, "reflection-config", jsonObject);
            put(jsonModelMap, RX_MICRO_NETTY_NATIVE_MODULE, "reflection-config", jsonObject);
            put(jsonModelMap, RX_MICRO_NETTY_NATIVE_ALL_MODULE, "reflection-config", jsonObject);
        }
        if (!processedClasses.contains(EpollServerSocketChannel.class.getName())) {
            final Map<String, Object> jsonObject = EPOLL_SERVER_SOCKET_CHANNEL;
            put(jsonModelMap, RX_MICRO_NETTY_NATIVE_LINUX_MODULE, "reflection-config", jsonObject);
            put(jsonModelMap, RX_MICRO_NETTY_NATIVE_MODULE, "reflection-config", jsonObject);
            put(jsonModelMap, RX_MICRO_NETTY_NATIVE_ALL_MODULE, "reflection-config", jsonObject);
        }
        if (!processedClasses.contains(EpollSocketChannel.class.getName())) {
            final Map<String, Object> jsonObject = EPOLL_SOCKET_CHANNEL;
            put(jsonModelMap, RX_MICRO_NETTY_NATIVE_LINUX_MODULE, "reflection-config", jsonObject);
            put(jsonModelMap, RX_MICRO_NETTY_NATIVE_MODULE, "reflection-config", jsonObject);
            put(jsonModelMap, RX_MICRO_NETTY_NATIVE_ALL_MODULE, "reflection-config", jsonObject);
        }
        // KQueue
        if (!processedClasses.contains(KQueueEventLoopGroup.class.getName())) {
            final Map<String, Object> jsonObject = K_QUEUE_EVENT_LOOP_GROUP;
            put(jsonModelMap, RX_MICRO_NETTY_NATIVE_OSX_MODULE, "reflection-config", jsonObject);
            put(jsonModelMap, RX_MICRO_NETTY_NATIVE_MODULE, "reflection-config", jsonObject);
            put(jsonModelMap, RX_MICRO_NETTY_NATIVE_ALL_MODULE, "reflection-config", jsonObject);
        }
        if (!processedClasses.contains(KQueueServerSocketChannel.class.getName())) {
            final Map<String, Object> jsonObject = K_QUEUE_SERVER_SOCKET_CHANNEL;
            put(jsonModelMap, RX_MICRO_NETTY_NATIVE_OSX_MODULE, "reflection-config", jsonObject);
            put(jsonModelMap, RX_MICRO_NETTY_NATIVE_MODULE, "reflection-config", jsonObject);
            put(jsonModelMap, RX_MICRO_NETTY_NATIVE_ALL_MODULE, "reflection-config", jsonObject);
        }
        if (!processedClasses.contains(KQueueSocketChannel.class.getName())) {
            final Map<String, Object> jsonObject = K_QUEUE_SOCKET_CHANNEL;
            put(jsonModelMap, RX_MICRO_NETTY_NATIVE_OSX_MODULE, "reflection-config", jsonObject);
            put(jsonModelMap, RX_MICRO_NETTY_NATIVE_MODULE, "reflection-config", jsonObject);
            put(jsonModelMap, RX_MICRO_NETTY_NATIVE_ALL_MODULE, "reflection-config", jsonObject);
        }
    }

    private void put(final Map<RxMicroModule, Map<String, List<Object>>> jsonModelMap,
                     final RxMicroModule rxMicroModule,
                     final String resourceName,
                     final Map<String, Object> jsonObject) {
        jsonModelMap.computeIfAbsent(rxMicroModule, v -> new TreeMap<>())
                .computeIfAbsent(resourceName, v -> new ArrayList<>())
                .add(jsonObject);
    }
}

