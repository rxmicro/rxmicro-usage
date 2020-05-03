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

package io.rxmicro.examples.graalvm.nativeimage.quick.start;

import io.rxmicro.config.WaitFor;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.TestedProcessBuilder;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.io.File;
import java.io.IOException;

import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

@EnabledIfEnvironmentVariable(named = "GRAALVM_HOME", matches = ".*?")
// tag::content[]
@RxMicroIntegrationTest
final class HelloWorldMicroService_UsingNativeImage_IT {

    static Process process;

    @BeforeAll
    static void beforeAll() throws IOException {
        process = new TestedProcessBuilder()
                .setCommandWithArgs("./HelloWorldMicroService")
                .setWorkingDir(new File("."))
                .build();
        new WaitFor("wait-for localhost:8080").start();
    }

    private BlockingHttpClient blockingHttpClient;

    @Test
    void Should_return_Hello_World() {
        final ClientHttpResponse response = blockingHttpClient.get("/");

        assertEquals(jsonObject("message", "Hello World!"), response.getBody());
        assertEquals(200, response.getStatusCode());
    }

    @AfterAll
    static void afterAll() {
        process.destroyForcibly();
    }
}
// end::content[]
