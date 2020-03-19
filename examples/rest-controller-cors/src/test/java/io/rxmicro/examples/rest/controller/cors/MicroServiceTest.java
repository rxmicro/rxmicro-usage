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

package io.rxmicro.examples.rest.controller.cors;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.SystemOut;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.api.Test;

import static io.rxmicro.http.HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS;
import static io.rxmicro.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import static io.rxmicro.http.HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD;
import static io.rxmicro.http.HttpHeaders.ORIGIN;
import static io.rxmicro.http.HttpHeaders.VARY;
import static io.rxmicro.rest.model.HttpMethod.PATCH;
import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
@RxMicroRestBasedMicroServiceTest(MicroService.class)
final class MicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    private SystemOut systemOut;

    @Test
    void Should_handle_PATCH_request() {
        blockingHttpClient.patch("/");

        assertEquals("handle", systemOut.asString());
    }

    // <1>
    @Test
    void Should_support_CORS_Options_request() {
        final ClientHttpResponse response = blockingHttpClient.options(
                "/",
                HttpHeaders.of(
                        ORIGIN, "test.rxmicro.io",
                        ACCESS_CONTROL_REQUEST_METHOD, PATCH
                )
        );

        assertEquals(ORIGIN, response.headers().getValue(VARY));
        assertEquals("*", response.headers().getValue(ACCESS_CONTROL_ALLOW_ORIGIN));
        assertEquals(PATCH.name(), response.headers().getValue(ACCESS_CONTROL_ALLOW_METHODS));
    }
}
// end::content[]