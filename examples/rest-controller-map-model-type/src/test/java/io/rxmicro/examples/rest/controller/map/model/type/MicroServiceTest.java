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

package io.rxmicro.examples.rest.controller.map.model.type;

import io.rxmicro.examples.rest.controller.map.model.type.model.Status;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Stream;

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
                        entry("1", jsonObject(
                                entry("enumData", jsonObject(
                                        "1", Status.created,
                                        "2", Status.approved
                                )),
                                entry("stringData", jsonObject(
                                        "1", "text1",
                                        "2", "text2"
                                )),
                                entry("integerData", jsonObject(
                                        "1", 1,
                                        "2", 2
                                )),
                                entry("booleanData", jsonObject(
                                        "1", true,
                                        "2", false
                                )),
                                entry("bigDecimalData", jsonObject(
                                        "1", new BigDecimal("3.14"),
                                        "2", new BigDecimal("2,718")
                                ))
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