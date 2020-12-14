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

package io.rxmicro.examples.data.r2dbc.postgresql.expected.updated.rows.count;

import io.rxmicro.data.sql.model.InvalidDatabaseStateException;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig;
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.junit.RxMicroComponentTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@RxMicroComponentTest(DataRepository.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class DataRepositoryTest {

    @Container
    private static final GenericContainer<?> POSTGRESQL_TEST_DB =
            new GenericContainer<>("rxmicro/postgres-test-db")
                    .withExposedPorts(5432);

    @WithConfig
    private static final PostgreSQLConfig CONFIG = new PostgreSQLConfig()
            .setDatabase("rxmicro")
            .setUser("rxmicro")
            .setPassword("password")
            .setMaxSize(3);

    @BeforeAll
    static void beforeAll() {
        POSTGRESQL_TEST_DB.start();
        CONFIG
                .setHost(POSTGRESQL_TEST_DB.getContainerIpAddress())
                .setPort(POSTGRESQL_TEST_DB.getFirstMappedPort());
    }

    private DataRepository dataRepository;

    @Test
    @Order(1)
    void update01_should_throw_InvalidDatabaseStateException() {
        final InvalidDatabaseStateException exception = assertThrows(InvalidDatabaseStateException.class, () ->
                dataRepository.update01("firstName", "lastName", "email").block());
        assertEquals(
                "Last DML operation failed: Expected 1 updated row count, but actual is 0 for the SQL: " +
                        "'UPDATE account SET first_name = $1, last_name = $2 WHERE email = $3' with params [firstName, lastName, email]!",
                exception.getMessage()
        );
    }

    @Test
    @Order(2)
    void update02_should_throw_InvalidDatabaseStateException() {
        final InvalidDatabaseStateException exception = assertThrows(InvalidDatabaseStateException.class, () ->
                dataRepository.update02().block());
        assertEquals(
                "Last DML operation failed: Expected 1 updated row count, but actual is 0 for the SQL: " +
                        "'UPDATE account SET first_name = 'firstName', last_name = 'lastName' WHERE email = 'email''!",
                exception.getMessage()
        );
    }

    @Test
    @Order(3)
    void update03_should_throw_InvalidDatabaseStateException() {
        final InvalidDatabaseStateException exception = assertThrows(InvalidDatabaseStateException.class, () ->
                dataRepository.update03("firstName", "lastName", "email").block());
        assertEquals(
                "Last DML operation failed: Expected 1 updated row count, but actual is 0 for the SQL: " +
                        "'UPDATE account SET first_name = $1, last_name = $2 WHERE email = $3 " +
                        "RETURNING id, email, first_name, last_name, balance, role' with params [firstName, lastName, email]!",
                exception.getMessage()
        );
    }

    @Test
    @Order(4)
    void update04_should_throw_InvalidDatabaseStateException() {
        final InvalidDatabaseStateException exception = assertThrows(InvalidDatabaseStateException.class, () ->
                dataRepository.update02().block());
        assertEquals(
                "Last DML operation failed: Expected 1 updated row count, but actual is 0 for the SQL: " +
                        "'UPDATE account SET first_name = 'firstName', last_name = 'lastName' WHERE email = 'email''!",
                exception.getMessage()
        );
    }
}