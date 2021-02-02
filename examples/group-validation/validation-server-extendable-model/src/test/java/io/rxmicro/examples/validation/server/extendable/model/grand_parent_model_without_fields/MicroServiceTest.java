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

package io.rxmicro.examples.validation.server.extendable.model.grand_parent_model_without_fields;

import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.ClientHttpResponse;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.rxmicro.test.json.JsonFactory.jsonErrorObject;
import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@RxMicroRestBasedMicroServiceTest(MicroService.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class MicroServiceTest {

    private final Map<String, Object> put1Request = new LinkedHashMap<>(jsonObject(
            "parentParameter", "PARENT",
            "childParameter", "CHILD"
    ));

    private final Map<String, Object> put2Request = new LinkedHashMap<>(jsonObject(
            "parentParameter", "PARENT"
    ));

    private BlockingHttpClient blockingHttpClient;

    @BeforeEach
    void beforeEach() {
        System.clearProperty("RETURN_INVALID_RESPONSE_FIELD");
    }

    @Order(1)
    @Test
    void put1_should_return_valid_response() {
        final ClientHttpResponse response = blockingHttpClient.put("/1", put1Request);

        assertEquals(put1Request, response.getBody());
        assertEquals(200, response.getStatusCode());
    }

    @Order(2)
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "parentParameter;    null;   Parameter \"parentParameter\" is required!",
            "parentParameter;    test;   Invalid parameter \"parentParameter\": Expected an uppercase string, but actual is 'test'!",

            "childParameter;    null;   Parameter \"childParameter\" is required!",
            "childParameter;    test;   Invalid parameter \"childParameter\": Expected an uppercase string, but actual is 'test'!"
    })
    void put1_should_return_request_validation_error(final String parameterName,
                                                     final String parameterValue,
                                                     final String expectedErrorMessage) {
        final String value = "null".equals(parameterValue) ? null : parameterValue;
        put1Request.put(parameterName, value);
        final ClientHttpResponse response = blockingHttpClient.put("/1", put1Request);

        assertEquals(jsonErrorObject(expectedErrorMessage), response.getBody());
        assertEquals(400, response.getStatusCode());
    }

    @Order(3)
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "parentParameter;    null;   Parameter \"parentParameter\" is required!",
            "parentParameter;    test;   Invalid parameter \"parentParameter\": Expected an uppercase string, but actual is 'test'!",

            "childParameter;    null;   Parameter \"childParameter\" is required!",
            "childParameter;    test;   Invalid parameter \"childParameter\": Expected an uppercase string, but actual is 'test'!"
    })
    void put1_should_return_response_validation_error(final String parameterName,
                                                      final String parameterValue,
                                                      final String expectedErrorMessage) {
        System.setProperty("RETURN_INVALID_RESPONSE_FIELD", parameterName + "=" + parameterValue);
        final ClientHttpResponse response = blockingHttpClient.put("/1", put1Request);

        assertEquals(jsonErrorObject("Response is invalid: " + expectedErrorMessage), response.getBody());
        assertEquals(500, response.getStatusCode());
    }

    @Order(4)
    @Test
    void put2_should_return_valid_response() {
        final ClientHttpResponse response = blockingHttpClient.put("/2", put2Request);

        assertEquals(put2Request, response.getBody());
        assertEquals(200, response.getStatusCode());
    }

    @Order(5)
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "parentParameter;    null;   Parameter \"parentParameter\" is required!",
            "parentParameter;    test;   Invalid parameter \"parentParameter\": Expected an uppercase string, but actual is 'test'!"
    })
    void put2_should_return_request_validation_error(final String parameterName,
                                                     final String parameterValue,
                                                     final String expectedErrorMessage) {
        final String value = "null".equals(parameterValue) ? null : parameterValue;
        put2Request.put(parameterName, value);
        final ClientHttpResponse response = blockingHttpClient.put("/2", put2Request);

        assertEquals(jsonErrorObject(expectedErrorMessage), response.getBody());
        assertEquals(400, response.getStatusCode());
    }

    @Order(6)
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "parentParameter;    null;   Parameter \"parentParameter\" is required!",
            "parentParameter;    test;   Invalid parameter \"parentParameter\": Expected an uppercase string, but actual is 'test'!"
    })
    void put2_should_return_response_validation_error(final String parameterName,
                                                      final String parameterValue,
                                                      final String expectedErrorMessage) {
        System.setProperty("RETURN_INVALID_RESPONSE_FIELD", parameterName + "=" + parameterValue);
        final ClientHttpResponse response = blockingHttpClient.put("/2", put2Request);

        assertEquals(jsonErrorObject("Response is invalid: " + expectedErrorMessage), response.getBody());
        assertEquals(500, response.getStatusCode());
    }
}