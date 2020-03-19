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

package io.rxmicro.examples.data.r2dbc.postgresql.concurrent.isolation.level;

import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig;
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.junit.RxMicroComponentTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@RxMicroComponentTest(BusinessService.class)
final class BusinessServiceTest {

    @Container
    private static GenericContainer<?> postgresqlTestDb =
            new GenericContainer<>("rxmicro/postgres-test-db")
                    .withExposedPorts(5432);

    @WithConfig
    private static PostgreSQLConfig config = new PostgreSQLConfig()
            .setDatabase("rxmicro")
            .setUser("rxmicro")
            .setPassword("password");

    @BeforeAll
    static void beforeAll() {
        postgresqlTestDb.start();
        config
                .setHost(postgresqlTestDb.getContainerIpAddress())
                .setPort(postgresqlTestDb.getFirstMappedPort());
    }

    private BusinessService businessService;

    static Stream<Function<BusinessService, String>> emailProvider() {
        return Stream.of(
                businessService -> businessService.testUsingReactor().block(),
                businessService -> businessService.testUsingRxJava().blockingGet(),
                businessService -> businessService.testUsingCompletableFuture().join()
        );
    }

    @ParameterizedTest
    @MethodSource("emailProvider")
    void verify(final Function<BusinessService, String> provider) {
        assertEquals("richard.hendricks@piedpiper.com", provider.apply(businessService));
    }

    @AfterAll
    static void afterAll() {
        postgresqlTestDb.stop();
    }
}