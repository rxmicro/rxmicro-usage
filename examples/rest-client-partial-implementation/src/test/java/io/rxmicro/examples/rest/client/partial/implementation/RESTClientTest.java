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

package io.rxmicro.examples.rest.client.partial.implementation;

import io.rxmicro.http.client.HttpClient;
import io.rxmicro.http.client.HttpClientFactory;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.httpclient.HttpRequestMock;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static io.rxmicro.rest.model.HttpMethod.GET;
import static io.rxmicro.test.mockito.httpclient.HttpClientMockFactory.getPreparedHttpClientMock;
import static io.rxmicro.test.mockito.httpclient.HttpClientMockFactory.prepareHttpClientMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

// tag::content[]
@InitMocks
@RxMicroComponentTest(RESTClient.class)
final class RESTClientTest {

    private RESTClient restClient;

    @Mock(answer = RETURNS_DEEP_STUBS)
    @Alternative
    private HttpClientFactory httpClientFactory;

    private void prepareGeneratedMethod() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(GET)
                        .setPath("/")
                        .build(),
                true
        );
    }

    @Test
    @BeforeTest(method = "prepareGeneratedMethod")
    void Should_invoke_generated_method() {
        assertDoesNotThrow(() -> restClient.generatedMethod().join());
    }

    private void prepareUserDefinedMethod() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setAnyRequest()
                        .build(),
                true
        );
    }

    @Test
    @BeforeTest(method = "prepareUserDefinedMethod")
    void Should_invoke_user_defined_method() {
        assertDoesNotThrow(() -> restClient.userDefinedMethod().join());

        final HttpClient httpClient = getPreparedHttpClientMock();
        verify(httpClient, never()).sendAsync(anyString(), anyString(), any());
        verify(httpClient, never()).sendAsync(anyString(), anyString(), any(), any());
    }
}
// end::content[]