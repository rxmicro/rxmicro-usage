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

import io.rxmicro.common.RxMicroModule;
import io.rxmicro.util.graalvm.Category;
import io.rxmicro.util.graalvm.Example;
import io.rxmicro.util.graalvm.GraalVmProcessor;
import io.rxmicro.util.graalvm.MissingProcessingLogicException;
import io.rxmicro.util.graalvm.RxMicroNativeImageResource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static io.rxmicro.json.JsonTypes.asJsonObject;
import static io.rxmicro.json.JsonTypes.asJsonString;

public class JniProcessor implements GraalVmProcessor {
    @Override
    public List<RxMicroNativeImageResource> process(final File rxMicroHome,
                                                    final List<Map.Entry<Example, Object>> data) {
        final Map<String, List<Object>> jsonModelMap = new TreeMap<>();
        final Set<Map<String, Object>> processed = new HashSet<>();
        for (final Map.Entry<Example, Object> entry : data) {
            final Map<String, Object> jsonObject = asJsonObject(entry.getValue());
            if (processed.add(jsonObject)) {
                final String name = asJsonString(jsonObject.get("name"));
                if (name.startsWith("io.rxmicro.examples.")) {
                    // ignore
                } else if (name.startsWith("java.")) {
                    jsonModelMap.computeIfAbsent("java-jni.config", k -> new ArrayList<>()).add(jsonObject);
                } else if (name.startsWith("sun.")) {
                    jsonModelMap.computeIfAbsent("sun-jni.config", k -> new ArrayList<>()).add(jsonObject);
                } else {
                    throw new MissingProcessingLogicException("Unsupported class name: '?'", name);
                }
            }
        }
        return List.of(new RxMicroNativeImageResource(
                RxMicroModule.RX_MICRO_RUNTIME_MODULE,
                Category.jni,
                jsonModelMap
        ));
    }
}

