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

package io.rxmicro.util.graalvm.processor.reflection;

import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.kqueue.KQueueSocketChannel;
import io.rxmicro.common.CheckedWrapperException;
import io.rxmicro.common.model.MapBuilder;
import io.rxmicro.config.Config;
import io.rxmicro.config.ConfigException;
import io.rxmicro.json.JsonArrayBuilder;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

public final class HardcodedStructures {

    public static final Map<String, Object> EPOLL_EVENT_LOOP_GROUP = new MapBuilder<String, Object>()
            .put("name", EpollEventLoopGroup.class.getName())
            .put("methods", new JsonArrayBuilder()
                    .add(
                            new MapBuilder<String, Object>()
                                    .put("name", "<init>")
                                    .put("parameterTypes", new JsonArrayBuilder()
                                            .add("int")
                                            .add(ThreadFactory.class.getName())
                                            .build()
                                    )
                                    .build()
                    )
                    .build())
            .build();

    public static final Map<String, Object> EPOLL_SERVER_SOCKET_CHANNEL = new MapBuilder<String, Object>()
            .put("name", EpollServerSocketChannel.class.getName())
            .put("methods", new JsonArrayBuilder()
                    .add(
                            new MapBuilder<String, Object>()
                                    .put("name", "<init>")
                                    .put("parameterTypes", List.of())
                                    .build()
                    )
                    .build())
            .build();

    public static final Map<String, Object> EPOLL_SOCKET_CHANNEL = new MapBuilder<String, Object>()
            .put("name", EpollSocketChannel.class.getName())
            .put("methods", new JsonArrayBuilder()
                    .add(
                            new MapBuilder<String, Object>()
                                    .put("name", "<init>")
                                    .put("parameterTypes", List.of())
                                    .build()
                    )
                    .build())
            .build();

    public static final Map<String, Object> K_QUEUE_EVENT_LOOP_GROUP = new MapBuilder<String, Object>()
            .put("name", KQueueEventLoopGroup.class.getName())
            .put("methods", new JsonArrayBuilder()
                    .add(
                            new MapBuilder<String, Object>()
                                    .put("name", "<init>")
                                    .put("parameterTypes", new JsonArrayBuilder()
                                            .add("int")
                                            .add(ThreadFactory.class.getName())
                                            .build()
                                    )
                                    .build()
                    )
                    .build())
            .build();

    public static final Map<String, Object> K_QUEUE_SERVER_SOCKET_CHANNEL = new MapBuilder<String, Object>()
            .put("name", KQueueServerSocketChannel.class.getName())
            .put("methods", new JsonArrayBuilder()
                    .add(
                            new MapBuilder<String, Object>()
                                    .put("name", "<init>")
                                    .put("parameterTypes", List.of())
                                    .build()
                    )
                    .build())
            .build();

    public static final Map<String, Object> K_QUEUE_SOCKET_CHANNEL = new MapBuilder<String, Object>()
            .put("name", KQueueSocketChannel.class.getName())
            .put("methods", new JsonArrayBuilder()
                    .add(
                            new MapBuilder<String, Object>()
                                    .put("name", "<init>")
                                    .put("parameterTypes", List.of())
                                    .build()
                    )
                    .build())
            .build();

    public static Map<String, Object> buildNormalizedConfigJsonObject(final String name) {
        try {
            final Class<?> configClass = Class.forName(name);
            if (Config.class.isAssignableFrom(configClass)) {
                final MapBuilder<String, Object> jsonObjectBuilder = new MapBuilder<String, Object>()
                        .put("name", name)
                        .put("allDeclaredMethods", true);
                if (!Modifier.isAbstract(configClass.getModifiers())) {
                    jsonObjectBuilder.put("methods", new JsonArrayBuilder()
                            .add(new MapBuilder<String, Object>()
                                    .put("name", "<init>")
                                    .put("parameterTypes", List.of())
                                    .build()
                            )
                            .build()
                    );
                }
                return jsonObjectBuilder.build();
            } else {
                throw new ConfigException("This is not a config class: ?", name);
            }
        } catch (final ClassNotFoundException exception) {
            throw new CheckedWrapperException(exception);
        }
    }

    private HardcodedStructures() {
    }
}

