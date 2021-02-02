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

package io.rxmicro.examples.rest.controller.params;

import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.ClientHttpResponse;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static io.rxmicro.examples.rest.controller.params.model.Status.approved;
import static io.rxmicro.examples.rest.controller.params.model.Status.created;
import static io.rxmicro.test.json.JsonFactory.jsonArray;
import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
@RxMicroRestBasedMicroServiceTest(ComplexModelMicroService.class)
final class ComplexModelMicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    @Test
    void Should_support_complex_models() {
        final Object jsonObject = jsonObject(
                "integer_parameter", 1,
                "enum_parameter", created,
                "enums_parameter", jsonArray(created, approved),
                "nested_model_parameter", jsonObject(
                        "string_parameter", "test",
                        "big_decimal_parameter", new BigDecimal("3.14"),
                        "instant_parameter", Instant.now()
                ),
                "nested_models_parameter", jsonArray(
                        jsonObject(
                                "string_parameter", "test1",
                                "big_decimal_parameter", new BigDecimal("1.1"),
                                "instant_parameter", Instant.now()
                        ),
                        jsonObject(
                                "string_parameter", "test2",
                                "big_decimal_parameter", new BigDecimal("1.2"),
                                "instant_parameter", Instant.now()
                        )
                )
        );
        final ClientHttpResponse response = blockingHttpClient.post("/", jsonObject);

        assertEquals(jsonObject, response.getBody());
    }
}
// end::content[]
