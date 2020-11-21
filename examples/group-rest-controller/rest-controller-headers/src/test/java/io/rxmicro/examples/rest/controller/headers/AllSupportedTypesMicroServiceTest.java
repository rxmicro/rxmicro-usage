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

package io.rxmicro.examples.rest.controller.headers;

import io.rxmicro.examples.rest.controller.headers.model.Status;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_LENGTH;
import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_TYPE;
import static io.rxmicro.http.HttpStandardHeaderNames.DATE;
import static io.rxmicro.http.HttpStandardHeaderNames.REQUEST_ID;
import static io.rxmicro.http.HttpStandardHeaderNames.SERVER;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RxMicroRestBasedMicroServiceTest(AllSupportedTypesMicroService.class)
final class AllSupportedTypesMicroServiceTest {

    private static final Set<String> STD_HEADERS = Set.of(
            CONTENT_LENGTH.toLowerCase(),
            CONTENT_TYPE.toLowerCase(),
            SERVER.toLowerCase(),
            DATE.toLowerCase(),
            REQUEST_ID.toLowerCase()
    );

    private BlockingHttpClient blockingHttpClient;

    static Stream<HttpHeaders> httpHeadersProvider() {
        return Stream.of(
                HttpHeaders.of(),
                HttpHeaders.of(
                        entry("Status", Status.created),
                        entry("Status-List", List.of(Status.created, Status.approved)),
                        entry("A-Boolean", false),
                        entry("Boolean-List", List.of(true, false, true)),
                        entry("A-Byte", 10),
                        entry("Byte-List", List.of(10, 11, 12)),
                        entry("A-Short", 10_000),
                        entry("Short-List", List.of(10_000, 11_000, 12_000)),
                        entry("A-Integer", 1_000_000_000),
                        entry("Integer-List", List.of(1_000_000_000, 2_000_000_000)),
                        entry("A-Long", 1_000_000_000_000L),
                        entry("Long-List", List.of(1_000_000_000_000L, 2_000_000_000_000L, 3_000_000_000_000L)),
                        entry("Big-Integer", "1000000000000000000000000000000000000000000"),
                        entry("Big-Integer-List", List.of("1000000000000000000000000000000000000000000", "22")),
                        entry("A-Float", "3.14"),
                        entry("Float-List", List.of("3.14", "2.78")),
                        entry("A-Double", "3.14"),
                        entry("Double-List", List.of("3.14", "2.78")),
                        entry("Big-Decimal", "3.14"),
                        entry("Big-Decimal-List", List.of("3.14", "2.78")),
                        entry("Character", "y"),
                        entry("Character-List", List.of("y", "n", "c")),
                        entry("String", "Hello"),
                        entry("String-List", List.of("Hello", "World")),
                        entry("Instant", Instant.parse("2020-02-02T02:20:00.00Z")),
                        entry("Instant-List", List.of(
                                Instant.parse("2020-02-02T02:20:00.00Z"),
                                Instant.parse("2020-03-03T02:20:00.00Z"))
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("httpHeadersProvider")
    void Should_support_all_http_header_types(final HttpHeaders httpHeaders) {
        final ClientHttpResponse response = blockingHttpClient.get("/", httpHeaders);
        assertEquals(200, response.getStatusCode());
        assertEquals(
                normalize(httpHeaders.getEntries()),
                normalize(response.getHeaders().getEntries())
        );
    }

    private List<Map.Entry<String, String>> normalize(final List<Map.Entry<String, String>> entries) {
        return entries.stream()
                .map(e -> entry(e.getKey().toLowerCase(), e.getValue()))
                .sorted(Map.Entry.comparingByKey())
                .filter(e -> !STD_HEADERS.contains(e.getKey())).collect(toList());
    }
}