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

package io.rxmicro.examples.unnamed.module.rest.controller.generator;

import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.rxmicro.test.json.JsonFactory.jsonErrorObject;
import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RxMicroRestBasedMicroServiceTest(MicroService.class)
final class MicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    @ParameterizedTest
    @CsvSource({
            "/,                     Response is invalid: Parameter \"email\" is required!",
            "/?email=,              Response is invalid: Parameter \"email\" is required!",
            "/?email=rxmicro,       Response is invalid: Invalid parameter \"email\": Expected a valid email format!",
            "/?email=rxmicro.io,    Response is invalid: Invalid parameter \"email\": Expected a valid email format!",
            "/?email=@rxmicro.io,   Response is invalid: Invalid parameter \"email\": Expected a valid email format!",
            "/?email=rxmicro.io@,   Response is invalid: Invalid parameter \"email\": Expected a valid email format!",
            "/?email=@.rxmicro.io,  Response is invalid: Invalid parameter \"email\": Expected a valid email format!",
            "/?email=rxmicro.io@.,  Response is invalid: Invalid parameter \"email\": Expected a valid email format!"
    })
    void Should_return_invalid_request_status(final String path,
                                              final String expectedErrorMessage) {
        final ClientHttpResponse response = blockingHttpClient.put(path);

        assertEquals(jsonErrorObject(expectedErrorMessage), response.body());
        assertEquals(500, response.statusCode());
    }

    @Test
    void Should_handle_request() {
        final ClientHttpResponse response = blockingHttpClient.put("/?email=welcome@rxmicro.io");

        assertEquals(
                jsonObject("email", "welcome@rxmicro.io"),
                response.body()
        );
        assertEquals(200, response.statusCode());
    }
}