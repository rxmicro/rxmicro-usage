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

package io.rxmicro.examples.validation.client.basic;

import io.rxmicro.examples.validation.client.basic.model.Response;
import io.rxmicro.http.client.HttpClientFactory;
import io.rxmicro.rest.model.HttpMethod;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeThisTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.httpclient.HttpRequestMock;
import io.rxmicro.test.mockito.junit.InitMocks;
import io.rxmicro.validation.UnexpectedResponseException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static io.rxmicro.common.util.Exceptions.getRealThrowable;
import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static io.rxmicro.test.mockito.httpclient.HttpClientMockFactory.prepareHttpClientMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// tag::content[]
@InitMocks
@RxMicroComponentTest(RESTClient.class)
final class RESTClientTest {

    private static final HttpRequestMock HTTP_REQUEST_MOCK = new HttpRequestMock.Builder()
            .setMethod(HttpMethod.GET)
            .setPath("/")
            .build();

    private RESTClient restClient;

    @Alternative
    @Mock
    private HttpClientFactory httpClientFactory;

    private void prepareValidResponse() {
        prepareHttpClientMock(
                httpClientFactory,
                HTTP_REQUEST_MOCK,
                jsonObject("email", "welcome@rxmicro.io") // <1>
        );
    }

    @Test
    @BeforeThisTest(method = "prepareValidResponse")
    void Should_return_received_email() {
        final Response response = restClient.get().join();
        assertEquals("welcome@rxmicro.io", response.getEmail()); // <1>
    }

    private void prepareInvalidResponse() {
        prepareHttpClientMock(
                httpClientFactory,
                HTTP_REQUEST_MOCK,
                jsonObject("email", "rxmicro.io") // <2>
        );
    }

    @Test
    @BeforeThisTest(method = "prepareInvalidResponse")
    void Should_throw_UnexpectedResponseException() {
        final Throwable throwable =
                // <4>
                getRealThrowable(assertThrows(RuntimeException.class, () -> restClient.get().join())); // <2>
        assertEquals(UnexpectedResponseException.class, throwable.getClass()); // <3>
        assertEquals(
                "Response is invalid: " +
                        "Invalid parameter \"email\": Expected a valid email format!", // <3>
                throwable.getMessage()
        );
    }

}
// end::content[]