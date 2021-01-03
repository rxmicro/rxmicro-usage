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

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.rxmicro.common.model.MapBuilder;
import io.rxmicro.util.graalvm.Example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static io.rxmicro.json.JsonTypes.asJsonArray;
import static io.rxmicro.json.JsonTypes.asJsonObject;
import static io.rxmicro.json.JsonTypes.asJsonString;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toMap;

public final class JsonConfigMerger {

    private final List<String> notUniqueStructures = List.of(
            ChannelInboundHandlerAdapter.class.getName(),
            ChannelInitializer.class.getName(),
            ChannelDuplexHandler.class.getName(),
            ByteToMessageDecoder.class.getName()
    );

    public List<Map.Entry<Example, Object>> normalizeStructures(final List<Map.Entry<Example, Object>> data) {
        final Map<String, Object> mostComplexStructureMap = notUniqueStructures.stream()
                .map(s -> getMostComplexStructureEntry(s, data))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
        final List<Map.Entry<Example, Object>> result = new ArrayList<>(data);
        for (int i = 0; i < result.size(); i++) {
            final Map<String, Object> jsonObject = asJsonObject(result.get(i).getValue());
            final String name = asJsonString(jsonObject.get("name"));
            if (notUniqueStructures.contains(name)) {
                result.set(i, entry(result.get(i).getKey(), mostComplexStructureMap.get(name)));
            }
        }
        return result;
    }

    private Map.Entry<String, Object> getMostComplexStructureEntry(final String className,
                                                                   final List<Map.Entry<Example, Object>> data) {
        final Set<Map<String, Object>> fields = new TreeSet<>(createComparator());
        final Set<Map<String, Object>> methods = new TreeSet<>(createComparator());
        final MapBuilder<String, Object> merged = new MapBuilder<String, Object>(true)
                .put("name", className);
        for (final Map.Entry<Example, Object> entry : data) {
            final Map<String, Object> jsonObject = asJsonObject(entry.getValue());
            if (asJsonString(jsonObject.get("name")).equals(className)) {
                addTo(jsonObject, merged, fields, methods);
            }
        }
        if (!fields.isEmpty()) {
            merged.put("fields", fields);
        }
        if (!methods.isEmpty()) {
            merged.put("methods", methods);
        }
        return entry(className, merged.build());
    }

    private void addTo(final Map<String, Object> jsonObject,
                       final MapBuilder<String, Object> merged,
                       final Set<Map<String, Object>> fields,
                       final Set<Map<String, Object>> methods) {
        for (final Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            final String name = entry.getKey();
            if ("name".equals(name)) {
                // do nothing!
            } else if ("fields".equals(name)) {
                for (final Object field : asJsonArray(entry.getValue())) {
                    addField(fields, asJsonObject(field));
                }
            } else if ("methods".equals(name)) {
                for (final Object method : asJsonArray(entry.getValue())) {
                    addMethod(methods, asJsonObject(method));
                }
            } else {
                merged.put(name, entry.getValue());
            }
        }
    }

    private void addField(final Set<Map<String, Object>> fields,
                          final Map<String, Object> jsonObject) {
        if (!fields.contains(jsonObject)) {
            final Iterator<Map<String, Object>> iterator = fields.iterator();
            while (iterator.hasNext()) {
                final Map<String, Object> field = iterator.next();
                if (isSameName(field, jsonObject)) {
                    if (isOldValueBetter(field, jsonObject)) {
                        return;
                    } else {
                        iterator.remove();
                        break;
                    }
                }
            }
            fields.add(jsonObject);
        }
    }

    private void addMethod(final Set<Map<String, Object>> methods,
                           final Map<String, Object> jsonObject) {
        if (!methods.contains(jsonObject)) {
            final Iterator<Map<String, Object>> iterator = methods.iterator();
            while (iterator.hasNext()) {
                final Map<String, Object> field = iterator.next();
                if (isSameName(field, jsonObject)) {
                    if (isOldValueBetter(field, jsonObject)) {
                        return;
                    } else {
                        iterator.remove();
                        break;
                    }
                }
            }
            methods.add(jsonObject);
        }
    }

    private boolean isOldValueBetter(final Map<String, Object> oldVal,
                                     final Map<String, Object> newValue) {
        if (oldVal.size() > newValue.size()) {
            return true;
        } else if (oldVal.size() == newValue.size()) {
            throw new RuntimeException("TODO");
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    private Comparator<Map<String, Object>> createComparator() {
        return (o1, o2) -> {
            final int res = ((String) o1.get("name")).compareTo((String) o2.get("name"));
            if (res == 0) {
                final List<String> parameterTypes1 = (List<String>) o1.get("parameterTypes");
                final List<String> parameterTypes2 = (List<String>) o2.get("parameterTypes");
                if (parameterTypes1 != null && parameterTypes2 != null) {
                    if (parameterTypes1.size() == parameterTypes2.size()) {
                        for (int i = 0; i < parameterTypes1.size(); i++) {
                            final String item1 = parameterTypes1.get(i);
                            final String item2 = parameterTypes2.get(i);
                            final int res2 = item1.compareTo(item2);
                            if (res2 != 0) {
                                return res2;
                            }
                        }
                        return 0;
                    } else {
                        return parameterTypes1.size() - parameterTypes2.size();
                    }
                } else {
                    return 0;
                }
            } else {
                return res;
            }
        };
    }

    private boolean isSameName(final Map<String, Object> o1,
                               final Map<String, Object> o2) {
        return ((String) o1.get("name")).compareTo((String) o2.get("name")) == 0;
    }
}

