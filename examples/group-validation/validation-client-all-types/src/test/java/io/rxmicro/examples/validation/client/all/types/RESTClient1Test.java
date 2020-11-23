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

package io.rxmicro.examples.validation.client.all.types;

import io.rxmicro.examples.validation.client.all.types.model.Request;
import io.rxmicro.http.client.HttpClientFactory;
import io.rxmicro.http.error.ValidationException;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;

@InitMocks
@RxMicroComponentTest(RESTClient1.class)
final class RESTClient1Test {

    @WithConfig
    private static RestClientConfig CONFIG = new RestClientConfig()
            .setEnableAdditionalValidations(true);

    private RESTClient1 restClient1;

    @Mock(answer = RETURNS_DEEP_STUBS)
    @Alternative
    private HttpClientFactory httpClientFactory;

    @Test
    void put1_should_throw_ValidationException() {
        assertThrowsValidationException(() -> restClient1.put1("rxmicro.io"));
    }

    @Test
    void put2_should_throw_ValidationException() {
        assertThrowsValidationException(() -> restClient1.put2(new Request("rxmicro.io")));
    }

    @Test
    void put4_should_throw_ValidationException() {
        assertThrowsValidationException(() -> restClient1.put4("rxmicro.io"));
    }

    @Test
    void put5_should_throw_ValidationException() {
        assertThrowsValidationException(() -> restClient1.put5(new Request("rxmicro.io")));
    }

    @Test
    void put7_should_throw_ValidationException() {
        assertThrowsValidationException(() -> restClient1.put7("rxmicro.io"));
    }

    @Test
    void put8_should_throw_ValidationException() {
        assertThrowsValidationException(() -> restClient1.put8(new Request("rxmicro.io")));
    }

    @Test
    void put10_should_throw_ValidationException() {
        assertThrowsValidationException(() -> restClient1.put10("rxmicro.io"));
    }

    @Test
    void put11_should_throw_ValidationException() {
        assertThrowsValidationException(() -> restClient1.put11(new Request("rxmicro.io")));
    }

    @Test
    void put13_should_throw_ValidationException() {
        assertThrowsValidationException(() -> restClient1.put13("rxmicro.io"));
    }

    @Test
    void put14_should_throw_ValidationException() {
        assertThrowsValidationException(() -> restClient1.put14(new Request("rxmicro.io")));
    }

    @Test
    void put16_should_throw_ValidationException() {
        assertThrowsValidationException(() -> restClient1.put16("rxmicro.io"));
    }

    @Test
    void put17_should_throw_ValidationException() {
        assertThrowsValidationException(() -> restClient1.put17(new Request("rxmicro.io")));
    }

    private void assertThrowsValidationException(final Executable executable) {
        final ValidationException exception =
                assertThrows(ValidationException.class, executable);
        assertEquals("Invalid parameter \"email\": Expected a valid email format!", exception.getMessage());
    }

}