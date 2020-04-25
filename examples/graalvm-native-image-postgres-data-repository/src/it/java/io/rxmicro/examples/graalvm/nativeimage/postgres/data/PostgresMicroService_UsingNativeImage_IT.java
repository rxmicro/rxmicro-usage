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

package io.rxmicro.examples.graalvm.nativeimage.postgres.data;

import io.rxmicro.config.WaitFor;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.TestedProcessBuilder;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.IOException;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static org.junit.Assert.assertEquals;

/**
 * ./PostgresMicroService postgre-sql.database=rxmicro postgre-sql.user=rxmicro postgre-sql.password=password
 */
@EnabledIfEnvironmentVariable(named = "GRAALVM_HOME", matches = ".*?")
@Testcontainers
@RxMicroIntegrationTest
public final class PostgresMicroService_UsingNativeImage_IT {

    static Process process;

    @Container
    private static final GenericContainer<?> POSTGRESQL_TEST_DB =
            new GenericContainer<>("rxmicro/postgres-test-db")
                    .withExposedPorts(5432);

    @BeforeAll
    static void beforeAll() throws IOException {
        POSTGRESQL_TEST_DB.start();

        process = new TestedProcessBuilder()
                .setCommandWithArgs(
                        "./PostgresMicroService",
                        "postgre-sql.database=rxmicro",
                        "postgre-sql.user=rxmicro",
                        "postgre-sql.password=password",
                        "postgre-sql.host=" + POSTGRESQL_TEST_DB.getContainerIpAddress(),
                        "postgre-sql.port=" + POSTGRESQL_TEST_DB.getFirstMappedPort(),
                        "wait-for",
                        format("?:?", POSTGRESQL_TEST_DB.getContainerIpAddress(), POSTGRESQL_TEST_DB.getFirstMappedPort())
                )
                .setWorkingDir(new File("."))
                .build();
        new WaitFor("wait-for localhost:8080").start();
    }

    private BlockingHttpClient blockingHttpClient;

    @Test
    void Should_return_2_plus_2() {
        final ClientHttpResponse response = blockingHttpClient.get("/");

        assertEquals(jsonObject("result", 4), response.body());
        assertEquals(200, response.statusCode());
    }

    @AfterAll
    static void afterAll() {
        POSTGRESQL_TEST_DB.stop();
        process.destroyForcibly();
    }
}
