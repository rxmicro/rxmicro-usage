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

package io.rxmicro.examples.config.annotations.supplier;

import io.rxmicro.http.HttpStandardHeaderNames;
import io.rxmicro.test.ClientHttpResponse;
import io.rxmicro.rest.server.ServerInstance;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.rxmicro.rest.server.RxMicro.startRestServer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RxMicroIntegrationTest
final class MicroService_UnitTest {

    private static ServerInstance serverInstance;

    @BeforeAll
    static void beforeAll() {
        System.setProperty("Custom-Static-Header-Value", "System-Value");
        serverInstance = startRestServer(MicroService.class);
    }

    private BlockingHttpClient blockingHttpClient;

    @Test
    void Should_support_custom_static_headers() {
        final ClientHttpResponse response = blockingHttpClient.get("/");

        assertTrue(
                response.getHeaders().getValue(HttpStandardHeaderNames.SERVER).contains("rx-micro-netty-rest-server"),
                "Invalid `Server` header: " + response.getHeaders().getValue(HttpStandardHeaderNames.SERVER)
        );
        assertNull(
                response.getHeaders().getValue(HttpStandardHeaderNames.DATE),
                "Header `Date` must be absent"
        );
        assertEquals("System-Value", response.getHeaders().getValue("Custom-Static-Header"));
    }

    @AfterAll
    static void afterAll() throws InterruptedException {
        serverInstance.shutdownAndWait();
    }
}