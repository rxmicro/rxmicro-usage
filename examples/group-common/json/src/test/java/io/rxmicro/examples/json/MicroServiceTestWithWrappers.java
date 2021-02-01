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

package io.rxmicro.examples.json;

import io.rxmicro.json.wrapper.JsonArray;
import io.rxmicro.json.wrapper.JsonObject;
import io.rxmicro.json.wrapper.JsonWrappers;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.ClientHttpResponse;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// tag::content[]
@RxMicroRestBasedMicroServiceTest(MicroService.class)
final class MicroServiceTestWithWrappers {

    private BlockingHttpClient blockingHttpClient;

    static Stream<Function<ClientHttpResponse, JsonObject>> toJsonObjectWrapperConverterProvider() {
        return Stream.of(
                // <1>
                response -> JsonWrappers.toJsonObject(response.getBody()),
                // <2>
                response -> JsonWrappers.readJsonObject(response.getBodyAsString(UTF_8))
        );
    }

    @ParameterizedTest
    @MethodSource("toJsonObjectWrapperConverterProvider")
    void Should_return_Response(final Function<ClientHttpResponse, JsonObject> converter) {
        final ClientHttpResponse response = blockingHttpClient.get("/");

        final JsonObject actualBody = converter.apply(response);

        assertEquals(
                new JsonObject()
                        .set("integer", 20),
                actualBody.getJsonObject("child")
        );
        assertEquals(
                new JsonArray()
                        .add(25).add(50).add(75).add(100),
                actualBody.getJsonArray("values")
        );
        assertEquals(
                "text",
                actualBody.getString("string")
        );
        assertEquals(
                10,
                actualBody.getNumber("integer").intValueExact()
        );
        assertEquals(
                new BigDecimal("0.1234"),
                actualBody.getNumber("decimal").bigDecimalValueExact()
        );
        assertTrue(
                actualBody.getBoolean("logical")
        );
    }
}
// end::content[]