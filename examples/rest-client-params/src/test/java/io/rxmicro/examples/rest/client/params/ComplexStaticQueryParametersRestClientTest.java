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

import io.rxmicro.http.QueryParams;
import io.rxmicro.http.client.HttpClientFactory;
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

@InitMocks
@RxMicroComponentTest(ComplexStaticQueryParametersRestClient.class)
final class ComplexStaticQueryParametersRestClientTest {

    private ComplexStaticQueryParametersRestClient restClient;

    @Mock(answer = RETURNS_DEEP_STUBS)
    @Alternative
    private HttpClientFactory httpClientFactory;

    private void prepareParentParamsOnly() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(GET)
                        .setPath("/get1")
                        .setQueryParameters(QueryParams.of(
                                "parent-param1", "new-parent-param1-value",
                                "parent-param2", "new-parent-param2-value",
                                "parent-param2", "new-parent-param2-value"
                        ))
                        .build(),
                true
        );
    }

    @Test
    @BeforeThisTest(method = "prepareParentParamsOnly")
    void Should_return_parent_params_only() {
        assertDoesNotThrow(() -> restClient.get1().join());
    }

    private void prepareParentAndChildParams() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(GET)
                        .setPath("/get2")
                        .setQueryParameters(QueryParams.of(
                                "parent-param1", "new-parent-param1-value",
                                "parent-param2", "new-parent-param2-value",
                                "parent-param2", "new-parent-param2-value",

                                "child-param1", "new-child-param1-value",
                                "child-param2", "new-child-param2-value",
                                "child-param2", "new-child-param2-value"
                        ))
                        .build(),
                true
        );
    }

    @Test
    @BeforeThisTest(method = "prepareParentAndChildParams")
    void Should_return_child_and_parent_params() {
        assertDoesNotThrow(() -> restClient.get2().join());
    }

    private void prepareOverriddenParentParams() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(GET)
                        .setPath("/get3")
                        .setQueryParameters(QueryParams.of(
                                "parent-param1", "new-child-parent-param1-value",
                                "parent-param2", "new-child-parent-param2-value"
                        ))
                        .build(),
                true
        );
    }

    @Test
    @BeforeThisTest(method = "prepareOverriddenParentParams")
    void Should_return_overridden_parent_params() {
        assertDoesNotThrow(() -> restClient.get3().join());
    }

    private void prepareExtendedParentParams() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(GET)
                        .setPath("/get4")
                        .setQueryParameters(QueryParams.of(
                                "parent-param1", "new-parent-param1-value",
                                "parent-param1", "new-child-parent-param1-value",

                                "parent-param2", "new-parent-param2-value",
                                "parent-param2", "new-parent-param2-value",
                                "parent-param2", "new-child-parent-param2-value"
                        ))
                        .build(),
                true
        );
    }

    @Test
    @BeforeThisTest(method = "prepareExtendedParentParams")
    void Should_return_extended_parent_params() {
        assertDoesNotThrow(() -> restClient.get4().join());
    }
}