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

package io.rxmicro.examples.validation.server.custom;

import io.rxmicro.test.ClientHttpResponse;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.SystemOut;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static io.rxmicro.test.json.JsonFactory.jsonErrorObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RxMicroRestBasedMicroServiceTest(MicroService.class)
final class MicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    private SystemOut systemOut;

    // tag::content[]
    @ParameterizedTest
    @CsvSource({
            "/,                     Parameter \"value\" is required!",
            "/?value=,              Parameter \"value\" is required!",
            "/?value=1.23,          Invalid parameter \"value\": Expected a zero value!",
            "/?value=-3.45,         Invalid parameter \"value\": Expected a zero value!"
    })
    void Should_return_invalid_request_status(final String path,
                                              final String expectedErrorMessage) {
        final ClientHttpResponse response = blockingHttpClient.patch(path);

        assertEquals(jsonErrorObject(expectedErrorMessage), response.getBody());
        assertEquals(400, response.getStatusCode());
        assertEquals("", systemOut.asString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "0.0", "0.000", "-0.0"})
    void Should_handle_request(final String value) {
        final ClientHttpResponse response = blockingHttpClient.patch("/?value=" + value);

        assertEquals(new BigDecimal(value).toString(), systemOut.asString());
        assertEquals(200, response.getStatusCode());
    }
    // end::content[]

}
