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

package io.rxmicro.examples.unnamed.module.rest.client.generator;

import io.rxmicro.examples.unnamed.module.rest.client.generator.model.Request;
import io.rxmicro.examples.unnamed.module.rest.client.generator.model.Response;
import io.rxmicro.http.QueryParams;
import io.rxmicro.http.client.HttpClientFactory;
import io.rxmicro.http.error.ValidationException;
import io.rxmicro.rest.model.HttpMethod;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.httpclient.HttpRequestMock;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;

import java.util.concurrent.CompletableFuture;

import static io.rxmicro.common.util.Exceptions.getRealThrowable;
import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static io.rxmicro.test.mockito.httpclient.HttpClientMockFactory.prepareHttpClientMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@InitMocks
@RxMicroComponentTest(RESTClient.class)
final class RESTClientTest {

    private RESTClient restClient;

    @Alternative
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpClientFactory httpClientFactory;

    @Test
    void Should_return_invalid_request_error_signal() {
        final CompletableFuture<Response> responseCompletableFuture =
                assertDoesNotThrow(() -> restClient.get(new Request("rxmicro.io")));
        final Throwable throwable = getRealThrowable(assertThrows(RuntimeException.class, responseCompletableFuture::join));

        assertEquals(ValidationException.class, throwable.getClass());
        assertEquals(
                "Invalid parameter \"email\": Expected a valid email format!",
                throwable.getMessage()
        );
    }

    private void prepareInvalidResponse() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(HttpMethod.GET)
                        .setPath("/")
                        .setQueryParameters(QueryParams.of("email", "welcome@rxmicro.io"))
                        .build(),
                jsonObject("email", "rxmicro.io")
        );
    }

    @Test
    @BeforeTest(method = "prepareInvalidResponse")
    void Should_does_not_validate_response() {
        final Response response = restClient.get(new Request("welcome@rxmicro.io")).join();

        assertEquals("rxmicro.io", response.getEmail());
    }
}