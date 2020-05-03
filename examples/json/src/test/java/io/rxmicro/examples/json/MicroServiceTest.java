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

import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.rxmicro.test.json.JsonFactory.jsonArray;
import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
@RxMicroRestBasedMicroServiceTest(MicroService.class)
final class MicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    @Test
    void Should_return_Response() {
        final ClientHttpResponse response = blockingHttpClient.get("/");

        final Object actualBody = response.getBody(); // <1>
        final Object expectedBody = jsonObject(
                "child", jsonObject("integer", 20), // <4>
                "values", jsonArray(25, 50, 75, 100),   // <5>
                "string", "text",
                "integer", 10,
                "decimal", new BigDecimal("0.1234"),
                "logical", true
        ); // <2>
        assertEquals(expectedBody, actualBody); // <3>
    }
}
// end::content[]