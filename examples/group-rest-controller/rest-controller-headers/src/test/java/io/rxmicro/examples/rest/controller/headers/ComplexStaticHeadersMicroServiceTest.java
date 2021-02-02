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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RxMicroRestBasedMicroServiceTest(ComplexStaticHeadersMicroService.class)
final class ComplexStaticHeadersMicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    @Test
    void Should_return_parent_headers_only() {
        final ClientHttpResponse response = blockingHttpClient.get("/get1");

        assertEquals(
                List.of("new-Parent-Header1-value"),
                response.getHeaders().getValues("Parent-Header1")
        );
        assertEquals(
                List.of("new-Parent-Header2-value", "new-Parent-Header2-value"),
                response.getHeaders().getValues("Parent-Header2")
        );
    }

    @Test
    void Should_return_child_and_parent_headers() {
        final ClientHttpResponse response = blockingHttpClient.get("/get2");

        assertEquals(
                List.of("new-Parent-Header1-value"),
                response.getHeaders().getValues("Parent-Header1")
        );
        assertEquals(
                List.of("new-Parent-Header2-value", "new-Parent-Header2-value"),
                response.getHeaders().getValues("Parent-Header2")
        );
        assertEquals(
                List.of("new-Child-Header1-value"),
                response.getHeaders().getValues("Child-Header1")
        );
        assertEquals(
                List.of("new-Child-Header2-value", "new-Child-Header2-value"),
                response.getHeaders().getValues("Child-Header2")
        );
    }

    @Test
    void Should_return_overridden_parent_headers() {
        final ClientHttpResponse response = blockingHttpClient.get("/get3");

        assertEquals(
                List.of("new-Child-Parent-Header1-value"),
                response.getHeaders().getValues("Parent-Header1")
        );
        assertEquals(
                List.of("new-Child-Parent-Header2-value"),
                response.getHeaders().getValues("Parent-Header2")
        );
    }

    @Test
    void Should_return_extended_parent_headers() {
        final ClientHttpResponse response = blockingHttpClient.get("/get4");

        assertEquals(
                List.of("new-Parent-Header1-value", "new-Child-Parent-Header1-value"),
                response.getHeaders().getValues("Parent-Header1")
        );
        assertEquals(
                List.of("new-Parent-Header2-value", "new-Parent-Header2-value", "new-Child-Parent-Header2-value"),
                response.getHeaders().getValues("Parent-Header2")
        );
    }
}