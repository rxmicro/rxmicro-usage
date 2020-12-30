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

import java.math.BigDecimal;

import static io.rxmicro.common.util.Strings.escapeString;

public class JsonToStringBuilder {

    private static final String NEW_LINE = "\n";

    private static final String TABULATION = " ".repeat(2);

    private final StringBuilder sb = new StringBuilder(100);

    public JsonToStringBuilder beginObject() {
        sb.append('{');
        return this;
    }

    public JsonToStringBuilder endObject() {
        sb.append('}');
        return this;
    }

    public JsonToStringBuilder beginArray() {
        sb.append('[');
        return this;
    }

    public JsonToStringBuilder endArray() {
        sb.append(']');
        return this;
    }

    public JsonToStringBuilder nameSeparator() {
        sb.append(':').append(' ');
        return this;
    }

    public JsonToStringBuilder valueSeparator() {
        sb.append(',').append(NEW_LINE);
        return this;
    }

    public JsonToStringBuilder tab(final int count) {
        if (count > 0) {
            sb.append(TABULATION.repeat(count));
        }
        return this;
    }

    public JsonToStringBuilder newLine() {
        sb.append(NEW_LINE);
        return this;
    }

    public JsonToStringBuilder string(final String value) {
        sb.append('"');
        escapeString(sb, value);
        sb.append('"');
        return this;
    }

    public JsonToStringBuilder number(final Object value) {
        if (value instanceof BigDecimal) {
            sb.append(((BigDecimal) value).toPlainString());
        } else {
            sb.append(value.toString());
        }
        return this;
    }

    public JsonToStringBuilder bool(final Boolean value) {
        sb.append(value ? "true" : "false");
        return this;
    }

    public JsonToStringBuilder nullValue() {
        sb.append("null");
        return this;
    }

    final String build() {
        return sb.toString();
    }

    public final String toString() {
        return build();
    }
}

