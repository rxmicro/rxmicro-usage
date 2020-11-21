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

package io.rxmicro.examples.monitoring.heathcheck;

import io.rxmicro.config.Configs;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.rest.server.HttpServerConfig;
import io.rxmicro.rest.server.ServerInstance;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.BlockingHttpClientSettings;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.rxmicro.rest.server.RxMicro.startRestServer;
import static io.rxmicro.test.HttpServers.getRandomFreePort;
import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RxMicroIntegrationTest
final class MicroServiceTest {

    private static final int SERVER_PORT = getRandomFreePort();

    private static ServerInstance serverInstance;

    @BeforeAll
    static void beforeAll() {
        new Configs.Builder()
                .withConfigs(new HttpServerConfig()
                        .setPort(SERVER_PORT))
                .build();
        serverInstance = startRestServer(MicroService.class);
    }

    @BlockingHttpClientSettings(randomPortProvider = "SERVER_PORT")
    private BlockingHttpClient blockingHttpClient;

    @Test
    void Should_handle_request() {
        final ClientHttpResponse response = blockingHttpClient.get("/base/test");

        assertEquals(jsonObject("message", "Hello World!"), response.getBody());
        assertEquals(200, response.getStatusCode());
    }

    @Test
    void Should_support_health_checks() {
        final ClientHttpResponse response = blockingHttpClient.get("/http-health-check");

        assertTrue(response.isBodyEmpty(), "Body not empty: " + response.getBody());
        assertEquals(200, response.getStatusCode());
    }

    @AfterAll
    static void afterAll() throws InterruptedException {
        serverInstance.shutdownAndWait();
    }
}