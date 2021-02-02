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

import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.ClientHttpResponse;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
@RxMicroRestBasedMicroServiceTest(RestControllerWithBody.class)
final class RestControllerWithBodyTest {

    private BlockingHttpClient blockingHttpClient;

    @ParameterizedTest
    @ValueSource(strings = {
            "/jse/completedFuture1",
            "/jse/completedFuture2",
            "/jse/completedFuture3",
            "/jse/completedFuture4",
            "/jse/completedFuture5",
            "/jse/completedFuture6",

            "/jse/completionStage1",
            "/jse/completionStage2",
            "/jse/completionStage3",
            "/jse/completionStage4",
            "/jse/completionStage5",
            "/jse/completionStage6",

            "/spring-reactor/mono1",
            "/spring-reactor/mono2",
            "/spring-reactor/mono3",

            "/rxjava3/single1",
            "/rxjava3/single2",
            "/rxjava3/single3",

            "/rxjava3/maybe1",
            "/rxjava3/maybe2",
            "/rxjava3/maybe3"
    })
    void Should_support_HTTP_responses_with_body(final String urlPath) {
        final ClientHttpResponse response = blockingHttpClient.get(urlPath);

        assertEquals(
                jsonObject("message", "Hello World!"), // <1>
                response.getBody()
        );
    }
}
// end::content[]
