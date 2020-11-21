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

package io.rxmicro.examples.rest.client.handlers;

import io.rxmicro.examples.rest.client.handlers.model.Request;
import io.rxmicro.http.client.HttpClientFactory;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeThisTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.httpclient.HttpRequestMock;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static io.rxmicro.test.mockito.httpclient.HttpClientMockFactory.prepareHttpClientMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;

// tag::content[]
@InitMocks
@RxMicroComponentTest(RestClientWithoutBody.class)
final class RestClientWithoutBodyTest {

    private RestClientWithoutBody restClient;

    @Mock(answer = RETURNS_DEEP_STUBS)
    @Alternative
    private HttpClientFactory httpClientFactory;

    static Stream<Consumer<RestClientWithoutBody>> clientMethodsProvider() {
        return Stream.of(
                client -> client.completedFuture1().join(),
                client -> client.completedFuture2(new Request("param")).join(),
                client -> client.completedFuture3("param").join(),

                client -> client.completionStage1().toCompletableFuture().join(),
                client -> client.completionStage2(
                        new Request("param")).toCompletableFuture().join(),
                client -> client.completionStage3("param").toCompletableFuture().join(),

                client -> client.mono1().block(),
                client -> client.mono2(new Request("param")).block(),
                client -> client.mono3("param").block(),

                client -> client.completable1().blockingAwait(),
                client -> client.completable2(new Request("param")).blockingAwait(),
                client -> client.completable3("param").blockingAwait()
        );
    }

    private void prepare() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder().setAnyRequest().build(),
                true
        );
    }

    @ParameterizedTest
    @MethodSource("clientMethodsProvider")
    @BeforeThisTest(method = "prepare")
    void Should_be_invoked_successfully(final Consumer<RestClientWithoutBody> clientMethod) {
        assertDoesNotThrow(() -> clientMethod.accept(restClient));
    }
}
// end::content[]