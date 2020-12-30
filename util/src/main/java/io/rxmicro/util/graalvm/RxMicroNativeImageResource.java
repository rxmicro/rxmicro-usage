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

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;

public final class RxMicroNativeImageResource {

    private final RxMicroModule module;

    private final Category category;

    private final List<Map.Entry<String, Object>> jsonModels;

    public RxMicroNativeImageResource(final RxMicroModule module,
                                      final Category category,
                                      final Map<String, List<Object>> jsonModels) {
        this.module = module;
        this.category = category;
        this.jsonModels = jsonModels.entrySet().stream().map(e -> entry(e.getKey(), (Object) e.getValue())).collect(toList());
    }

    public RxMicroNativeImageResource(final RxMicroModule module,
                                      final Category category,
                                      final Object jsonModels) {
        this.module = module;
        this.category = category;
        this.jsonModels = List.of(entry(category.getFileName(), jsonModels));
    }

    public RxMicroModule getModule() {
        return module;
    }

    public Category getCategory() {
        return category;
    }

    public List<Map.Entry<String, Object>> getJsonModels() {
        return jsonModels;
    }
}

