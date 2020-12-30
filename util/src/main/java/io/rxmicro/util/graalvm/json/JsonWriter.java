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

package io.rxmicro.util.graalvm.json;

import io.rxmicro.json.JsonNumber;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonWriter {

    @SuppressWarnings("unchecked")
    public static String toJsonString(final Object value) {
        final JsonToStringBuilder builder = new JsonToStringBuilder();
        if (value instanceof Map) {
            addJsonObject(builder, (Map<String, Object>) value, 1);
        } else if (value instanceof List) {
            addJsonArray(builder, (List<Object>) value, 1);
        } else {
            throw new UnsupportedOperationException();
        }
        return builder.build();
    }

    private static void addJsonObject(final JsonToStringBuilder builder,
                                      final Map<String, Object> jsonObject,
                                      final int indent) {
        if (jsonObject.isEmpty()) {
            builder.beginObject().endObject();
        } else if (jsonObject.size() == 1 && isPrimitiveValue(jsonObject.entrySet().iterator().next().getValue()) && indent > 2) {
            builder.beginObject();
            final Iterator<Map.Entry<String, Object>> iterator = jsonObject.entrySet().iterator();
            final Map.Entry<String, Object> entry = iterator.next();
            final Object value = entry.getValue();
            builder.string(entry.getKey()).nameSeparator();
            addValue(builder, indent, value);
            builder.endObject();
        } else {
            builder.beginObject().newLine();
            final Iterator<Map.Entry<String, Object>> iterator = jsonObject.entrySet().iterator();
            while (iterator.hasNext()) {
                final Map.Entry<String, Object> entry = iterator.next();
                final Object value = entry.getValue();
                builder.tab(indent)
                        .string(entry.getKey()).nameSeparator();
                addValue(builder, indent, value);
                if (iterator.hasNext()) {
                    builder.valueSeparator();
                } else {
                    builder.newLine();
                }
            }
            builder.tab(indent - 1).endObject();
        }
    }

    private static boolean isPrimitiveValue(final Object value) {
        return !(value instanceof Map) && !(value instanceof List);
    }

    private static void addJsonArray(final JsonToStringBuilder builder,
                                     final List<Object> jsonArray,
                                     final int indent) {
        if (jsonArray.isEmpty()) {
            builder.beginArray().endArray();
        } else {
            builder.beginArray().newLine();
            for (int i = 0; i < jsonArray.size(); i++) {
                final Object value = jsonArray.get(i);
                builder.tab(indent);
                addValue(builder, indent, value);
                if (i != jsonArray.size() - 1) {
                    builder.valueSeparator();
                } else {
                    builder.newLine();
                }
            }
            builder.tab(indent - 1).endArray();
        }
    }

    @SuppressWarnings("unchecked")
    private static void addValue(final JsonToStringBuilder builder,
                                 final int indent,
                                 final Object value) {
        if (value == null) {
            builder.nullValue();
        } else if (value instanceof Map) {
            addJsonObject(builder, (Map<String, Object>) value, indent + 1);
        } else if (value instanceof List) {
            addJsonArray(builder, (List<Object>) value, indent + 1);
        } else if (value instanceof Collection) {
            addJsonArray(builder, new ArrayList<>((Collection<Object>) value), indent + 1);
        } else if (value instanceof Boolean) {
            builder.bool((Boolean) value);
        } else if (value instanceof Number || value instanceof JsonNumber) {
            builder.number(value);
        } else {
            // String or any object
            builder.string(value.toString());
        }
    }

    private JsonWriter() {
    }
}

