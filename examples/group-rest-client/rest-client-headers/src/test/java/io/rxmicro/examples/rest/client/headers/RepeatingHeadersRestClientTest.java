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

import io.rxmicro.examples.rest.client.headers.model.Status;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.client.HttpClientFactory;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeThisTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.httpclient.HttpRequestMock;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static io.rxmicro.examples.rest.client.headers.model.Status.approved;
import static io.rxmicro.examples.rest.client.headers.model.Status.created;
import static io.rxmicro.examples.rest.client.headers.model.Status.rejected;
import static io.rxmicro.rest.model.HttpMethod.PUT;
import static io.rxmicro.test.mockito.httpclient.HttpClientMockFactory.prepareHttpClientMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;

// tag::content[]
@InitMocks
@RxMicroComponentTest(RepeatingHeadersRestClient.class)
final class RepeatingHeadersRestClientTest {

    private RepeatingHeadersRestClient restClient;

    @Mock(answer = RETURNS_DEEP_STUBS)
    @Alternative
    private HttpClientFactory httpClientFactory;

    private void prepare() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(PUT)
                        .setPath("/")
                        .setHeaders(HttpHeaders.of(
                                "Single-Header", "created|approved|rejected", // <1>
                                "Repeating-Header", "created",
                                "Repeating-Header", "approved",               // <2>
                                "Repeating-Header", "rejected"
                        ))
                        .build(),
                true
        );
    }

    @Test
    @BeforeThisTest(method = "prepare")
    void Should_support_repeating_headers() {
        final List<Status> headers = List.of(created, approved, rejected);
        assertDoesNotThrow(() -> restClient.put(headers, headers).join());
    }
}
// end::content[]