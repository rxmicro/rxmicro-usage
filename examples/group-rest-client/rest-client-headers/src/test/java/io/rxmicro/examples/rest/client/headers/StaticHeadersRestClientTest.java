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

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.client.detail.HttpClientFactory;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeThisTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.httpclient.HttpRequestMock;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static io.rxmicro.rest.model.HttpMethod.GET;
import static io.rxmicro.test.mockito.httpclient.HttpClientMockFactory.prepareHttpClientMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;

// tag::content[]
@InitMocks
@RxMicroComponentTest(StaticHeadersRestClient.class)
final class StaticHeadersRestClientTest {

    private StaticHeadersRestClient restClient;

    @Mock(answer = RETURNS_DEEP_STUBS)
    @Alternative
    private HttpClientFactory httpClientFactory;

    private void prepareParentHeaderOnly() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(GET)
                        .setPath("/get1")
                        .setHeaders(HttpHeaders.of(
                                "Mode", "Demo"
                        ))
                        .build(),
                true
        );
    }

    @Test
    @BeforeThisTest(method = "prepareParentHeaderOnly")
    void Should_use_parent_header_only() {
        assertDoesNotThrow(() -> restClient.get1().join());
    }

    private void prepareParentAndChildHeaders() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(GET)
                        .setPath("/get2")
                        .setHeaders(HttpHeaders.of(
                                "Mode", "Demo",
                                "Debug", "true"
                        ))
                        .build(),
                true
        );
    }

    @Test
    @BeforeThisTest(method = "prepareParentAndChildHeaders")
    void Should_use_parent_and_child_headers() {
        assertDoesNotThrow(() -> restClient.get2().join());
    }
}
// end::content[]