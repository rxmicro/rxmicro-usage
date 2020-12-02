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

package io.rxmicro.examples.rest.controller.extendable.model;

import io.rxmicro.http.client.ClientHttpResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static io.rxmicro.common.util.Formats.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class AbstractTest {

    private final Set<String> processedHeaders = new HashSet<>(List.of(
            "content-length",
            "date",
            "server",
            "request-id",
            "content-type"
    ));

    protected final void assertCustomHeader(final ClientHttpResponse response,
                                            final String headerName) {
        processedHeaders.add(headerName.toLowerCase(Locale.ENGLISH));
        assertEquals(headerName, response.getHeaders().getValue(headerName));
    }

    protected final void assertNoOtherHeaders(final ClientHttpResponse response) {
        for (final Map.Entry<String, String> entry : response.getHeaders().getEntries()) {
            if (!processedHeaders.contains(entry.getKey().toLowerCase(Locale.ENGLISH))) {
                fail(format("Redundant header: ?=?", entry.getKey(), entry.getValue()));
            }
        }
    }
}
