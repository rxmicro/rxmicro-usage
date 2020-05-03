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

import java.lang.reflect.Method;

public final class ShowReflectionConfigForNativeImage {

    public static void main(final String[] args) {
        final Class<? extends Config> configClass = SecretsConfig.class;

        System.out.println("Config class is: " + configClass + System.lineSeparator());

        for (final Method method : configClass.getMethods()) {
            if (method.getName().startsWith("set") && method.getParameterCount() == 1) {
                System.out.println("{");
                System.out.println("\t\"name\": \"" + method.getName() + "\",");
                System.out.println("\t\"parameterTypes\": [");
                System.out.println("\t\t\"" + method.getParameterTypes()[0].getName() + "\"");
                System.out.println("\t]");
                System.out.println("},");
            }
        }
    }
}
