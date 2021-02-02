/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.examples.processor.proxy;

import io.rxmicro.config.Configs;
import io.rxmicro.config.WaitFor;
import io.rxmicro.examples.processor.proxy.server.RestControllerProxy;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.logger.LoggerImplProviderFactory;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.server.HttpServerConfig;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.RxMicro;
import io.rxmicro.rest.server.ServerInstance;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.ClientHttpResponse;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.rxmicro.json.JsonHelper.toJsonString;
import static io.rxmicro.test.HttpServers.getRandomFreePort;
import static io.rxmicro.test.json.JsonFactory.jsonErrorObject;
import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RxMicroRestBasedMicroServiceTest(FrontEndController.class)
final class FrontEndControllerTest {

    private static final int RANDOM_PORT = getRandomFreePort();

    static ServerInstance serverInstance;

    static {
        System.setProperty("logger.io.rxmicro.rest.server.level", "TRACE");
        System.setProperty("logger.io.rxmicro.examples.processor.proxy.client.level", "TRACE");
        LoggerImplProviderFactory.resetLoggerImplFactory();
    }

    @BeforeAll
    static void beforeAll() {
        new Configs.Builder()
                .withConfigs(new HttpServerConfig()
                        .setPort(RANDOM_PORT))
                .withConfigs(new RestServerConfig()
                        .setDevelopmentMode(true)
                        .setEnableAdditionalValidations(true)
                        .setShowRuntimeEnv(true)
                        .setUseFullClassNamesForRouterMappingLogMessages(false))
                .withConfigs(new RestClientConfig()
                        .setPort(RANDOM_PORT)
                        .setEnableAdditionalValidations(true))
                .build();
        serverInstance = RxMicro.startRESTServer(RestControllerProxy.class);
        new WaitFor("localhost:" + RANDOM_PORT).start();
    }

    private BlockingHttpClient blockingHttpClient;

    @Test
    @Order(1)
    void Should_return_success_response() {
        final ClientHttpResponse response =
                blockingHttpClient.put("/", HttpHeaders.of("Header", "header"), jsonObject("param", "param"));

        assertEquals(jsonObject("param", "param"), response.getBody());
        assertEquals("header", response.getHeaders().getValue("Header"));
        assertEquals(200, response.getStatusCode());
    }

    @Test
    @Order(2)
    void Should_return_failed_response() {
        final ClientHttpResponse response =
                blockingHttpClient.put("/", HttpHeaders.of("Header", "header"), jsonObject("param", "error"));

        assertEquals(jsonErrorObject("custom error"), response.getBody());
        assertEquals(toJsonString(jsonErrorObject("custom error"), true), response.getBodyAsUTF8String());
        assertEquals(418, response.getStatusCode());
    }

    @AfterAll
    static void afterAll() {
        serverInstance.shutdown();
    }
}