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

package io.rxmicro.examples.testing.integration.docker;

import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
// <1>
@RxMicroIntegrationTest
// <2>
@Testcontainers
final class HelloWorldMicroService_IT {

    // <3>
    @Container
    private static final DockerComposeContainer<?> compose =
            new DockerComposeContainer<>(new File("docker-compose.yml").getAbsoluteFile())
                    .withLocalCompose(true)
                    .withPull(false)
                    .withTailChildContainers(true)
                    .waitingFor("rxmicro-hello-world", Wait.forHttp("/http-health-check"));

    private BlockingHttpClient blockingHttpClient;

    @Test
    void Should_return_Hello_World() {
        final ClientHttpResponse response = blockingHttpClient.get("/");

        assertEquals(jsonObject("message", "Hello World!"), response.body()); // <4>
        assertEquals(200, response.statusCode());
    }
}
// end::content[]
