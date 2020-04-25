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

package io.rxmicro.examples.rest.client.params;

import io.rxmicro.examples.rest.client.params.model.Request;
import io.rxmicro.examples.rest.client.params.model.Response;
import io.rxmicro.http.QueryParams;
import io.rxmicro.http.client.HttpClientFactory;
import io.rxmicro.rest.model.HttpMethod;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.httpclient.HttpRequestMock;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static io.rxmicro.test.mockito.httpclient.HttpClientMockFactory.prepareHttpClientMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;

@SuppressWarnings("ConstantConditions")
// tag::content[]
@InitMocks
@RxMicroComponentTest(SimpleUsageRestClient.class)
final class SimpleUsageRestClientTest {

    private static final String ENDPOINT_VERSION = "v1";

    private static final Boolean USE_PROXY = true;

    private static final QueryParams QUERY_PARAMETERS = QueryParams.of(
            "endpoint_version", ENDPOINT_VERSION,
            "use-Proxy", USE_PROXY
    );

    private static final Object BODY = jsonObject(
            "endpoint_version", ENDPOINT_VERSION,
            "use-Proxy", USE_PROXY
    );

    private SimpleUsageRestClient restClient;

    @Mock(answer = RETURNS_DEEP_STUBS)
    @Alternative
    private HttpClientFactory httpClientFactory;

    private void prepare1() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(HttpMethod.GET)
                        .setPath("/get1")
                        .setQueryParameters(QUERY_PARAMETERS)
                        .build(),
                BODY,
                true
        );
    }

    @Test
    @BeforeTest(method = "prepare1")
    void Should_use_handler_with_request_class() {
        final Response response =
                restClient.get1(new Request(ENDPOINT_VERSION, USE_PROXY)).join();

        assertEquals(ENDPOINT_VERSION, response.getEndpointVersion());
        assertEquals(USE_PROXY, response.getUseProxy());
    }

    private void prepare2() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(HttpMethod.GET)
                        .setPath("/get2")
                        .setQueryParameters(QUERY_PARAMETERS)
                        .build(),
                BODY,
                true
        );
    }

    @Test
    @BeforeTest(method = "prepare2")
    void Should_use_handler_with_request_params() {
        final Response response =
                restClient.get2(ENDPOINT_VERSION, USE_PROXY).join();

        assertEquals(ENDPOINT_VERSION, response.getEndpointVersion());
        assertEquals(USE_PROXY, response.getUseProxy());
    }
}
// end::content[]