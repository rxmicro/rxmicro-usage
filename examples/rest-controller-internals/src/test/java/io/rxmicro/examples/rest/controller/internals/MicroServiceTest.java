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

package io.rxmicro.examples.rest.controller.internals;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.api.Test;

import static io.rxmicro.http.HttpVersion.HTTP_1_1;
import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

// tag::content[]
@RxMicroRestBasedMicroServiceTest(MicroService.class)
final class MicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    @Test
    void Should_support_internal_types() {
        final Object body = jsonObject("message", "Hello World!");
        final ClientHttpResponse response = blockingHttpClient.post(
                "/",
                HttpHeaders.of("Custom-Header", "Custom-Value"),
                body
        );

        assertEquals(body, response.body());
        assertEquals(201, response.statusCode());
        assertSame(HTTP_1_1, response.version());
        final HttpHeaders responseHeaders = response.headers();
        final String remoteAddress = responseHeaders.getValue("Remote-Address");
        assertTrue(
                remoteAddress.contains("127.0.0.1"),
                "Invalid Remote-Address: " + remoteAddress
        );
        assertEquals("/", responseHeaders.getValue("Url-Path"));
        assertEquals("POST", responseHeaders.getValue("Method"));
        assertEquals("Custom-Value", responseHeaders.getValue("Custom-Header"));
    }
}
// end::content[]
