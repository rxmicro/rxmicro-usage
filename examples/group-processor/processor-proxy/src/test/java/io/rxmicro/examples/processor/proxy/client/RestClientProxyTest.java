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

package io.rxmicro.examples.processor.proxy.client;

import io.rxmicro.config.Configs;
import io.rxmicro.config.WaitFor;
import io.rxmicro.examples.processor.proxy.model.Request;
import io.rxmicro.examples.processor.proxy.model.Response;
import io.rxmicro.examples.processor.proxy.server.RestControllerProxy;
import io.rxmicro.logger.LoggerImplProviderFactory;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.model.HttpCallFailedException;
import io.rxmicro.rest.server.HttpServerConfig;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.RxMicro;
import io.rxmicro.rest.server.ServerInstance;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.concurrent.CompletionException;

import static io.rxmicro.common.util.Exceptions.getRealThrowable;
import static io.rxmicro.json.JsonHelper.readJsonObject;
import static io.rxmicro.rest.client.RestClientFactory.getRestClient;
import static io.rxmicro.test.HttpServers.getRandomFreePort;
import static io.rxmicro.test.json.JsonFactory.jsonErrorObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RxMicroIntegrationTest
final class RestClientProxyTest {

    private static final int RANDOM_PORT = getRandomFreePort();

    static ServerInstance serverInstance;

    static {
        System.setProperty("logger.io.rxmicro.rest.server.level", "DEBUG");
        System.setProperty("logger.io.rxmicro.examples.processor.proxy.client.level", "DEBUG");
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

    @Test
    @Order(1)
    void Should_return_success_response() {
        final Request request = new Request("header", "param");
        final Response response = getRestClient(RestClientProxy.class).put(request).join();

        assertEquals("header", response.getHeader());
        assertEquals("param", response.getParam());
    }

    @Test
    @Order(2)
    void Should_return_failed_response() {
        final Request request = new Request("header", "error");
        final CompletionException exception =
                assertThrows(CompletionException.class, () -> getRestClient(RestClientProxy.class).put(request).join());

        final HttpCallFailedException httpCallFailedException = (HttpCallFailedException) getRealThrowable(exception);
        assertEquals(
                jsonErrorObject("custom error"),
                readJsonObject(httpCallFailedException.getBodyAsString())
        );
    }

    @AfterAll
    static void afterAll() {
        serverInstance.shutdown();
    }
}