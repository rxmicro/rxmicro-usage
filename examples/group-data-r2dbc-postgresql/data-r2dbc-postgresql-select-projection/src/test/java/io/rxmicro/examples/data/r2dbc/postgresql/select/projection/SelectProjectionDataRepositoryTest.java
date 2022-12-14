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

package io.rxmicro.examples.data.r2dbc.postgresql.select.projection;

import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig;
import io.rxmicro.examples.data.r2dbc.postgresql.select.projection.model.Account;
import io.rxmicro.examples.data.r2dbc.postgresql.select.projection.model.Role;
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.junit.RxMicroComponentTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@RxMicroComponentTest(SelectProjectionDataRepository.class)
final class SelectProjectionDataRepositoryTest {

    @Container
    private static final GenericContainer<?> POSTGRESQL_TEST_DB =
            new GenericContainer<>("rxmicro/postgres-test-db")
                    .withExposedPorts(5432);

    @WithConfig
    private static final PostgreSQLConfig CONFIG = new PostgreSQLConfig()
            .setDatabase("rxmicro")
            .setUser("rxmicro")
            .setPassword("password");

    @BeforeAll
    static void beforeAll() {
        POSTGRESQL_TEST_DB.start();
        CONFIG
                .setHost(POSTGRESQL_TEST_DB.getHost())
                .setPort(POSTGRESQL_TEST_DB.getFirstMappedPort());
    }

    private SelectProjectionDataRepository dataRepository;

    static Stream<Function<SelectProjectionDataRepository, CompletableFuture<Account>>> repositoryMethodProvider() {
        return Stream.of(
                SelectProjectionDataRepository::findAllColumnsExceptRole1,
                SelectProjectionDataRepository::findAllColumnsExceptRole2,
                SelectProjectionDataRepository::findAllColumnsExceptRole3
        );
    }

    @ParameterizedTest
    @MethodSource("repositoryMethodProvider")
    void findAllColumnsExceptRole(Function<SelectProjectionDataRepository, CompletableFuture<Account>> provider) {
        final Account account = provider.apply(dataRepository).join();
        assertEquals(
                new Account(
                        1L,
                        "richard.hendricks@piedpiper.com",
                        "Richard",
                        "Hendricks",
                        new BigDecimal("70000.00")
                ),
                account
        );
    }

    @Test
    void findAllColumns() {
        final Account account = dataRepository.findAllColumns().join();
        assertEquals(
                new Account(
                        1L,
                        "richard.hendricks@piedpiper.com",
                        "Richard",
                        "Hendricks",
                        new BigDecimal("70000.00"),
                        Role.CEO
                ),
                account
        );
    }

    @Test
    void findFirstAndLastName() {
        final Account account = dataRepository.findFirstAndLastName().join();
        assertEquals(
                new Account(
                        "Richard",
                        "Hendricks"
                ),
                account
        );
    }

    @Test
    void findModifiedColumns() {
        final Account account = dataRepository.findModifiedColumns().join();
        assertEquals(
                new Account(
                        1L,
                        "***@***",
                        "Richard",
                        "HENDRICKS",
                        new BigDecimal("70000.00")
                ),
                account
        );
    }

    @AfterAll
    static void afterAll() {
        POSTGRESQL_TEST_DB.stop();
    }
}