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

package io.rxmicro.examples.rest.client.headers;

import io.rxmicro.examples.rest.client.headers.model.Request;
import io.rxmicro.examples.rest.client.headers.model.Response;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.client.HttpClientFactory;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.httpclient.HttpRequestMock;
import io.rxmicro.test.mockito.httpclient.HttpResponseMock;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

import java.util.function.Function;
import java.util.stream.Stream;

import static io.rxmicro.rest.model.HttpMethod.GET;
import static io.rxmicro.test.mockito.httpclient.HttpClientMockFactory.prepareHttpClientMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;

// tag::content[]
@InitMocks
@RxMicroComponentTest(SimpleUsageRestClient.class)
final class SimpleUsageRestClientTest {

    private static final String ENDPOINT_VERSION = "v1";

    private static final Boolean USE_PROXY = true;

    private SimpleUsageRestClient restClient;

    @Mock(answer = RETURNS_DEEP_STUBS)
    @Alternative
    private HttpClientFactory httpClientFactory;

    static Stream<Function<SimpleUsageRestClient, Response>> restClientExecutableProvider() {
        return Stream.of(
                client -> client.get1(new Request(ENDPOINT_VERSION, USE_PROXY)).join(),
                client -> client.get2(ENDPOINT_VERSION, USE_PROXY).join()
        );
    }

    private void prepare() {
        final HttpHeaders headers = HttpHeaders.of(
                "Endpoint-Version", ENDPOINT_VERSION, // <2>
                "UseProxy", USE_PROXY                 // <3>
        );
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(GET)
                        .setAnyPath()
                        .setHeaders(headers)
                        .build(),
                new HttpResponseMock.Builder()
                        .setHeaders(headers)
                        .build(),
                true
        );
    }

    @ParameterizedTest
    @MethodSource("restClientExecutableProvider")
    @BeforeTest(method = "prepare")
    void Should_process_HTTP_headers(
            final Function<SimpleUsageRestClient, Response> clientMethod) {
        final Response response = clientMethod.apply(restClient); // <1>

        assertEquals(ENDPOINT_VERSION, response.getEndpointVersion()); // <4>
        assertEquals(USE_PROXY, response.getUseProxy());               // <4>
    }
}
// end::content[]