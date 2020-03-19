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

package io.rxmicro.examples.rest.controller.request.id;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.SystemOut;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.api.Test;

import static io.rxmicro.http.HttpHeaders.REQUEST_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
@RxMicroRestBasedMicroServiceTest(MicroService.class)
final class MicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    private SystemOut systemOut;

    @Test
    void Should_generate_RequestId_automatically() {
        final ClientHttpResponse response = blockingHttpClient.get("/");
        assertEquals("TestRequestId", systemOut.asString()); // <1>
        assertEquals("TestRequestId", response.headers().getValue(REQUEST_ID));  // <2>
    }

    @Test
    void Should_use_provided_RequestId() {
        final ClientHttpResponse response =
                blockingHttpClient.get("/", HttpHeaders.of(REQUEST_ID, "Qwerty")); // <3>

        assertEquals("Qwerty", systemOut.asString()); // <4>
        assertEquals("Qwerty", response.headers().getValue(REQUEST_ID));  // <5>
    }
}
// end::content[]
