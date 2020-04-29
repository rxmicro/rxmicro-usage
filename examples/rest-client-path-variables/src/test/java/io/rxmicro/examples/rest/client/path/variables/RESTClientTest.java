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

package io.rxmicro.examples.rest.client.path.variables;

import io.rxmicro.examples.rest.client.path.variables.model.Request;
import io.rxmicro.http.client.HttpClientFactory;
import io.rxmicro.rest.model.HttpMethod;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeTest;
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
@RxMicroComponentTest(RESTClient.class)
final class RESTClientTest {

    private RESTClient restClient;

    @Mock(answer = RETURNS_DEEP_STUBS)
    @Alternative
    private HttpClientFactory httpClientFactory;

    static Stream<Consumer<RESTClient>> clientMethodsProvider() {
        return Stream.of(
                client -> client.consume(new Request("CATEGORY", "TYPE", "SUB-TYPE")).join(),
                client -> client.consume("CATEGORY", "TYPE", "SUB-TYPE").join()
        );
    }

    private void prepare() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(HttpMethod.GET)
                        .setPath("/CATEGORY/TYPE-SUB-TYPE") // <1>
                        .build(),
                true
        );
    }

    @ParameterizedTest
    @MethodSource("clientMethodsProvider")
    @BeforeTest(method = "prepare")
    void Should_return_message_Hello_World(final Consumer<RESTClient> clientMethod) {
        assertDoesNotThrow(() -> clientMethod.accept(restClient));
    }
}
// end::content[]