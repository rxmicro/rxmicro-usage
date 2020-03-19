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
import io.rxmicro.http.client.HttpClientFactory;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.httpclient.HttpRequestMock;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static io.rxmicro.rest.model.HttpMethod.GET;
import static io.rxmicro.test.mockito.httpclient.HttpClientMockFactory.prepareHttpClientMock;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;

@InitMocks
@RxMicroComponentTest(ComplexStaticHeadersRestClient.class)
final class ComplexStaticHeadersRestClientTest {

    private ComplexStaticHeadersRestClient restClient;

    @Mock(answer = RETURNS_DEEP_STUBS)
    @Alternative
    private HttpClientFactory httpClientFactory;

    private void prepareParentHeadersOnly() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(GET)
                        .setPath("/get1")
                        .setHeaders(HttpHeaders.of(
                                "Parent-Header1", "new-Parent-Header1-value",
                                "Parent-Header2", "new-Parent-Header2-value",
                                "Parent-Header2", "new-Parent-Header2-value"
                        ))
                        .build(),
                true
        );
    }

    @Test
    @BeforeTest(method = "prepareParentHeadersOnly")
    void Should_return_parent_headers_only() {
        restClient.get1().join();
    }

    private void prepareParentAndChildHeaders() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(GET)
                        .setPath("/get2")
                        .setHeaders(HttpHeaders.of(
                                "Parent-Header1", "new-Parent-Header1-value",
                                "Parent-Header2", "new-Parent-Header2-value",
                                "Parent-Header2", "new-Parent-Header2-value",

                                "Child-Header1", "new-Child-Header1-value",
                                "Child-Header2", "new-Child-Header2-value",
                                "Child-Header2", "new-Child-Header2-value"
                        ))
                        .build(),
                true
        );
    }

    @Test
    @BeforeTest(method = "prepareParentAndChildHeaders")
    void Should_return_child_and_parent_headers() {
        restClient.get2().join();
    }

    private void prepareOverriddenParentHeaders() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(GET)
                        .setPath("/get3")
                        .setHeaders(HttpHeaders.of(
                                "Parent-Header1", "new-Child-Parent-Header1-value",
                                "Parent-Header2", "new-Child-Parent-Header2-value"
                        ))
                        .build(),
                true
        );
    }

    @Test
    @BeforeTest(method = "prepareOverriddenParentHeaders")
    void Should_return_overridden_parent_headers() {
        restClient.get3().join();
    }

    private void prepareExtendedParentHeaders() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(GET)
                        .setPath("/get4")
                        .setHeaders(HttpHeaders.of(
                                "Parent-Header1", "new-Parent-Header1-value",
                                "Parent-Header1", "new-Child-Parent-Header1-value",

                                "Parent-Header2", "new-Parent-Header2-value",
                                "Parent-Header2", "new-Parent-Header2-value",
                                "Parent-Header2", "new-Child-Parent-Header2-value"
                        ))
                        .build(),
                true
        );
    }

    @Test
    @BeforeTest(method = "prepareExtendedParentHeaders")
    void Should_return_extended_parent_headers() {
        restClient.get4().join();
    }
}