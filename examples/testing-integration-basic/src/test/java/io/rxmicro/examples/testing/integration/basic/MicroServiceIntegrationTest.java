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

package io.rxmicro.examples.testing.integration.basic;

import io.rxmicro.config.Configs;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.rest.server.HttpServerConfig;
import io.rxmicro.rest.server.ServerInstance;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.BlockingHttpClientSettings;
import io.rxmicro.test.SystemOut;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.rxmicro.rest.server.RxMicro.startRestServer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// tag::content[]
// <1>
@RxMicroIntegrationTest
final class MicroServiceIntegrationTest {

    // <2>
    private static final int PORT = 55555;

    private static ServerInstance serverInstance;

    // <5>
    @BeforeAll
    static void beforeAll() {
        new Configs.Builder()
                .withConfigs(new HttpServerConfig()
                        .setPort(PORT))
                .build(); // <4>
        serverInstance = startRestServer(MicroService.class);// <3>
    }

    // <6>
    @BlockingHttpClientSettings(port = PORT)
    private BlockingHttpClient blockingHttpClient;

    // <7>
    private SystemOut systemOut;

    @Test
    void test() {
        final ClientHttpResponse response = blockingHttpClient.get("/");

        assertEquals(200, response.statusCode()); // <8>
        assertTrue(response.isBodyEmpty(), "Body not empty: " + response.body());// <8>
        assertEquals("handle", systemOut.asString()); // <9>
    }

    @AfterAll
    static void afterAll() throws InterruptedException {
        serverInstance.shutdownAndWait();
    }
}
// end::content[]
