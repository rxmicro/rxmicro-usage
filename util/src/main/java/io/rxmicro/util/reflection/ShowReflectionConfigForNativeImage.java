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

package io.rxmicro.util.reflection;

import io.rxmicro.config.Config;
import io.rxmicro.config.SecretsConfig;
import io.rxmicro.data.mongo.MongoConfig;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.server.HttpServerConfig;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.netty.NettyRestServerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Strings.unCapitalize;

public final class ShowReflectionConfigForNativeImage {

    private static void printReadAllPropertiesCodeFragment(final Class<? extends Config> configClass) {
        final String nameSpace = unCapitalize(configClass.getSimpleName());
        System.out.println("final " + configClass.getSimpleName() + " " + nameSpace + " = getConfig(" + configClass.getSimpleName() + ".class);");
        for (final Method method : configClass.getMethods()) {
            if (method.getDeclaringClass() != Object.class && method.getDeclaringClass() != Config.class) {
                if (method.getName().startsWith("get") && method.getParameterCount() == 0) {
                    final String property = unCapitalize(method.getName().substring(3));
                    System.out.println(
                            "System.out.println(\"" +
                                    configClass.getName() + "." + property + " = \" + " + nameSpace + "." + method.getName() + "());"
                    );
                }
                if (method.getName().startsWith("is") && method.getParameterCount() == 0) {
                    final String property = unCapitalize(method.getName().substring(2));
                    System.out.println(
                            "System.out.println(\"" +
                                    configClass.getName() + "." + property + " = \" + " + nameSpace + "." + method.getName() + "());"
                    );
                }
            }
        }
    }

    private static void printDefaultConfigExample(final Class<? extends Config> configClass)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        final Object instance = configClass.getConstructor().newInstance();
        final String nameSpace = Config.getDefaultNameSpace(configClass);
        for (final Method method : configClass.getMethods()) {
            if (method.getDeclaringClass() != Object.class && method.getDeclaringClass() != Config.class) {
                if (method.getName().startsWith("get") && method.getParameterCount() == 0) {
                    final String property = unCapitalize(method.getName().substring(3));
                    System.out.println(format("entry(\"?.?\", \"?\"),", nameSpace, property, method.invoke(instance)));
                }
                if (method.getName().startsWith("is") && method.getParameterCount() == 0) {
                    final String property = unCapitalize(method.getName().substring(2));
                    System.out.println(format("entry(\"?.?\", \"?\"),", nameSpace, property, method.invoke(instance)));
                }
            }
        }
    }

    public static void main(final String[] args)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final List<Class<? extends Config>> configClasses = List.of(
                SecretsConfig.class,
                MongoConfig.class,
                PostgreSQLConfig.class,
                RestClientConfig.class,
                HttpServerConfig.class,
                RestServerConfig.class,
                NettyRestServerConfig.class
        );
        for (final Class<? extends Config> configClass : configClasses) {
            printReadAllPropertiesCodeFragment(configClass);
            System.out.println();
            printDefaultConfigExample(configClass);
            System.out.println();
        }
    }
}
