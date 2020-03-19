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

package io.rxmicro.examples.rest.controller.handlers;

import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

// tag::content[]
@RxMicroRestBasedMicroServiceTest(RestControllerWithoutBody.class)
final class RestControllerWithoutBodyTest {

    private BlockingHttpClient blockingHttpClient;

    @ParameterizedTest
    @ValueSource(strings = {
            "/void/void1",
            "/void/void2",
            "/void/void3",

            "/jse/completedFuture1",
            "/jse/completedFuture2",
            "/jse/completedFuture3",

            "/jse/completionStage1",
            "/jse/completionStage2",
            "/jse/completionStage3",

            "/spring-reactor/mono1",
            "/spring-reactor/mono2",
            "/spring-reactor/mono3",

            "/rxjava3/completable1",
            "/rxjava3/completable2",
            "/rxjava3/completable3"
    })
    void Should_support_HTTP_responses_without_body(final String urlPath) {
        final ClientHttpResponse response = blockingHttpClient.get(urlPath);

        assertTrue(response.isBodyEmpty(), "Body not empty: " + response.body()); // <1>
    }
}
// end::content[]
