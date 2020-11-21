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

package io.rxmicro.examples.rest.controller.complex.model;

import io.rxmicro.examples.rest.controller.complex.model.model.Status;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.json.JsonNumber;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Stream;

import static io.rxmicro.test.json.JsonFactory.jsonArray;
import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RxMicroRestBasedMicroServiceTest(MicroService.class)
final class MicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    static Stream<Map<String, Object>> parametersProvider() {
        return Stream.of(
                jsonObject(),
                jsonObject(
                        entry("status", Status.created),
                        entry("statusList", jsonArray(Status.created, Status.approved)),
                        entry("aBoolean", true),
                        entry("booleanList", jsonArray(true, false, true)),
                        entry("aByte", 10),
                        entry("byteList", jsonArray(10, 11, 12)),
                        entry("aShort", 10000),
                        entry("shortList", jsonArray(10000, 11000, 12000)),
                        entry("aInteger", 1000000000),
                        entry("integerList", jsonArray(1000000000, 2000000000)),
                        entry("aLong", 1000000000000L),
                        entry("longList", jsonArray(1000000000000L, 2000000000000L, 3000000000000L)),
                        entry("bigInteger", new JsonNumber("1000000000000000000000000000000000000000000")),
                        entry("bigIntegerList", jsonArray(
                                new JsonNumber("1000000000000000000000000000000000000000000"),
                                new JsonNumber("23"))
                        ),
                        entry("aFloat", 3.14f),
                        entry("floatList", jsonArray(3.14f, 2.78f)),
                        entry("aDouble", 3.14),
                        entry("doubleList", jsonArray(3.14, 2.78)),
                        entry("bigDecimal", new BigDecimal("3.14")),
                        entry("bigDecimalList", jsonArray(new BigDecimal("3.14"), new BigDecimal("2.78"))),
                        entry("character", "y"),
                        entry("characterList", jsonArray("y", "n", "c")),
                        entry("string", "Hello"),
                        entry("stringList", jsonArray("Hello", "World")),
                        entry("instant", Instant.parse("2020-02-02T02:20:00.00Z")),
                        entry("instantList", jsonArray(
                                Instant.parse("2020-02-02T02:20:00.00Z"),
                                Instant.parse("2020-03-03T02:20:00.00Z")
                        )),
                        entry("nested", jsonObject("value", "string")),
                        entry("nestedList", jsonArray(
                                jsonObject("value", "string1"),
                                jsonObject("value", "string2")
                        ))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("parametersProvider")
    void Should_support_all_parameter_types(final Map<String, Object> json) {
        final ClientHttpResponse response = blockingHttpClient.post("/", json);

        assertEquals(200, response.getStatusCode());
        assertEquals(
                json,
                response.getBody()
        );
    }
}