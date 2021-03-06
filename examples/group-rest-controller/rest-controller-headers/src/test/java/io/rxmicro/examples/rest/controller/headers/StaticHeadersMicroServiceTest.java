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

package io.rxmicro.examples.rest.controller.headers;

import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.ClientHttpResponse;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RxMicroRestBasedMicroServiceTest(StaticHeadersMicroService.class)
final class StaticHeadersMicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    // tag::content[]
    @Test
    void Should_use_parent_header_only() {
        final ClientHttpResponse response = blockingHttpClient.get("/get1");

        assertEquals("Demo", response.getHeaders().getValue("Mode"));
    }

    @Test
    void Should_use_parent_and_child_headers() {
        final ClientHttpResponse response = blockingHttpClient.get("/get2");

        assertEquals("Demo", response.getHeaders().getValue("Mode"));
        assertEquals("true", response.getHeaders().getValue("Debug"));
    }
    // end::content[]
}