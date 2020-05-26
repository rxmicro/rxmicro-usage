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

package io.rxmicro.examples.rest.client.expressions;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.ProtocolSchema;
import io.rxmicro.http.client.HttpClientFactory;
import io.rxmicro.rest.model.HttpMethod;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.junit.BeforeThisTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.httpclient.HttpRequestMock;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static io.rxmicro.test.mockito.httpclient.HttpClientMockFactory.prepareHttpClientMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;

// tag::content[]
@InitMocks
@RxMicroComponentTest(RESTClient.class)
final class RESTClientTest {

    @WithConfig("custom")
    private static final CustomHttpClientConfig config =
            new CustomHttpClientConfig()
                    .setHost("rxmicro.io")
                    .setPort(8443)
                    .setSchema(ProtocolSchema.HTTPS)
                    .setUseProxy(false)
                    .setMode(CustomHttpClientConfig.Mode.TEST);

    private RESTClient restClient;

    @Mock(answer = RETURNS_DEEP_STUBS)
    @Alternative
    private HttpClientFactory httpClientFactory;

    private void prepare() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(HttpMethod.PUT)
                        .setPath("/")
                        .setHeaders(HttpHeaders.of(
                                "Use-Proxy", "false",
                                "Debug", "Use-Proxy=false, Mode=TEST",
                                "Endpoint", "Schema=HTTPS, Host=rxmicro.io, Port=8443"
                        ))
                        .build(),
                true
        );
    }

    @Test
    @BeforeThisTest(method = "prepare")
    void Should_support_expressions() {
        assertDoesNotThrow(() -> restClient.put().join());
    }
}
// end::content[]