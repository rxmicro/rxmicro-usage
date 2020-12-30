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

import static io.rxmicro.common.util.Formats.format;

public enum Category {

    jni("JNIConfigurationResources"),

    proxy("DynamicProxyConfigurationResources"),

    reflect("ReflectionConfigurationResources"),

    resource("ResourceConfigurationResources");

    private final String instruction;

    Category(final String instruction) {
        this.instruction = instruction;
    }

    public String getFileName() {
        return format("?-config.json", name());
    }

    public String getInstruction() {
        return instruction;
    }

    public static Category of(final String fileName) {
        for (final Category category : Category.values()) {
            if (category.getFileName().equals(fileName)) {
                return category;
            }
        }
        throw new InvalidStateException("Category is undefined for '?' file!", fileName);
    }
}

