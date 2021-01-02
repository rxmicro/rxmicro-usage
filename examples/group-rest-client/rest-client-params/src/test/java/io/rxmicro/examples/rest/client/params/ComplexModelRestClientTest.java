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

import io.rxmicro.examples.rest.client.params.model.ComplexRequest;
import io.rxmicro.examples.rest.client.params.model.ComplexResponse;
import io.rxmicro.examples.rest.client.params.model.Status;
import io.rxmicro.examples.rest.client.params.model.nested.NestedModel;
import io.rxmicro.rest.client.detail.HttpClientFactory;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeThisTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.httpclient.HttpRequestMock;
import io.rxmicro.test.mockito.httpclient.HttpResponseMock;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static io.rxmicro.examples.rest.client.params.model.Status.approved;
import static io.rxmicro.examples.rest.client.params.model.Status.created;
import static io.rxmicro.rest.model.HttpMethod.POST;
import static io.rxmicro.test.mockito.httpclient.HttpClientMockFactory.prepareHttpClientMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;

@SuppressWarnings("FieldCanBeLocal")
// tag::content[]
@InitMocks
@RxMicroComponentTest(ComplexModelRestClient.class)
final class ComplexModelRestClientTest {

    private final Integer integerParameter = 1;

    private final Status enumParameter = created;

    private final List<Status> enumsParameter = List.of(created, approved);

    private final NestedModel nestedModelParameter =
            new NestedModel("test", new BigDecimal("3.14"), Instant.now());

    private final List<NestedModel> nestedModelsParameter = List.of(
            new NestedModel("test1", new BigDecimal("1.1"), Instant.now()),
            new NestedModel("test2", new BigDecimal("1.2"), Instant.now())
    );

    private ComplexModelRestClient restClient;

    @Mock(answer = RETURNS_DEEP_STUBS)
    @Alternative
    private HttpClientFactory httpClientFactory;

    void prepare() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(POST)
                        .setPath("/")
                        .setAnyBody()
                        .build(),
                new HttpResponseMock.Builder()
                        .setReturnRequestBody()
                        .build(),
                true
        );
    }

    @Test
    @BeforeThisTest(method = "prepare")
    void Should_support_complex_requests_and_responses() {
        final ComplexResponse response = assertDoesNotThrow(() ->
                restClient.post(new ComplexRequest(
                        integerParameter,
                        enumParameter,
                        enumsParameter,
                        nestedModelParameter,
                        nestedModelsParameter
                )).join());

        assertEquals(integerParameter, response.getIntegerParameter());
        assertEquals(enumParameter, response.getEnumParameter());
        assertEquals(enumsParameter, response.getEnumsParameter());
        assertEquals(nestedModelParameter, response.getNestedModelParameter());
        assertEquals(nestedModelsParameter, response.getNestedModelsParameter());
    }
}
// end::content[]