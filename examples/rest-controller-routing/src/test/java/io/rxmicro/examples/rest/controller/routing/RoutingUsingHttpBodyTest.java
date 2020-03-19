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

package io.rxmicro.examples.rest.controller.routing;

import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.SystemOut;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
@RxMicroRestBasedMicroServiceTest(RoutingUsingHttpBody.class)
final class RoutingUsingHttpBodyTest {

    private BlockingHttpClient blockingHttpClient;

    private SystemOut systemOut;

    @ParameterizedTest
    @ValueSource(strings = {"GET", "HEAD", "POST", "PUT", "PATCH", "OPTIONS", "DELETE"})
    void Should_route_to_handleRequestsWithoutBody(final String method) {
        blockingHttpClient.send(
                method,
                "/?parameter=test" // <1>
        );
        assertEquals("without body", systemOut.asString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"POST", "PUT", "PATCH"})
    void Should_route_to_handleRequestsWithBody(final String method) {
        blockingHttpClient.send(
                method,
                "/",
                jsonObject("parameter", "test") // <2>
        );
        assertEquals("with body", systemOut.asString());
    }
}
// end::content[]
