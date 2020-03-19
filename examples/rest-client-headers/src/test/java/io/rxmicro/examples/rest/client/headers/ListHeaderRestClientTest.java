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

import io.rxmicro.examples.rest.client.headers.model.ListHeaderResponse;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.client.HttpClientFactory;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeIterationMethodSource;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.httpclient.HttpRequestMock;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mock;

import java.util.List;

import static io.rxmicro.examples.rest.client.headers.model.Status.approved;
import static io.rxmicro.examples.rest.client.headers.model.Status.created;
import static io.rxmicro.examples.rest.client.headers.model.Status.rejected;
import static io.rxmicro.rest.model.HttpMethod.GET;
import static io.rxmicro.test.mockito.httpclient.HttpClientMockFactory.prepareHttpClientMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;

// tag::content[]
@InitMocks
@RxMicroComponentTest(ListHeaderRestClient.class)
final class ListHeaderRestClientTest {

    private static final HttpRequestMock HTTP_REQUEST_MOCK = new HttpRequestMock.Builder()
            .setMethod(GET)
            .setPath("/")
            .build();

    private ListHeaderRestClient restClient;

    @Mock(answer = RETURNS_DEEP_STUBS)
    @Alternative
    private HttpClientFactory httpClientFactory;

    private void stringValuesSeparatedByComma() {
        prepareHttpClientMock(
                httpClientFactory,
                HTTP_REQUEST_MOCK,
                HttpHeaders.of(
                        "Supported-Statuses", "created|approved|rejected"
                ),
                true
        );
    }

    private void repeatingHeaders() {
        prepareHttpClientMock(
                httpClientFactory,
                HTTP_REQUEST_MOCK,
                HttpHeaders.of(
                        "Supported-Statuses", "created",
                        "Supported-Statuses", "approved",
                        "Supported-Statuses", "rejected"
                ),
                true
        );
    }

    private void comboValues() {
        prepareHttpClientMock(
                httpClientFactory,
                HTTP_REQUEST_MOCK,
                HttpHeaders.of(
                        "Supported-Statuses", "created|approved",
                        "Supported-Statuses", "rejected"
                ),
                true
        );
    }

    @ParameterizedTest
    @BeforeIterationMethodSource(methods = {
            "stringValuesSeparatedByComma",
            "repeatingHeaders",
            "comboValues"
    })
    void Should_process_HTTP_headers(final String method) {
        final ListHeaderResponse response = restClient.get().join();

        assertEquals(
                List.of(created, approved, rejected),
                response.getSupportedStatuses()
        );
    }
}
// end::content[]