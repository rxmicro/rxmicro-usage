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

package io.rxmicro.examples.data.r2dbc.postgresql.select.custom;

import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig;
import io.rxmicro.examples.data.r2dbc.postgresql.select.custom.model.Account;
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.junit.RxMicroComponentTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static io.rxmicro.common.util.ExCollections.orderedMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@RxMicroComponentTest(CustomSelectRepository.class)
final class CustomSelectRepositoryTest {

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

    private CustomSelectRepository dataRepository;

    // tag::content[]
    @Test
    void findAll() {
        final List<EntityFieldMap> entityFieldMaps = dataRepository.findAll(
                "SELECT email, first_name, last_name FROM account WHERE id = 1"
        ).join();
        assertEquals(
                List.of(
                        orderedMap(
                                "email", "richard.hendricks@piedpiper.com",
                                "first_name", "Richard",
                                "last_name", "Hendricks"
                        )
                ),
                entityFieldMaps
        );
    }

    @Test
    void findAccount() {
        final Optional<Account> optionalAccount = dataRepository.findAccount(
                "SELECT * FROM account WHERE first_name = $1",
                "Richard"
        ).join();
        assertEquals(
                Optional.of(
                        new Account(
                                1L,
                                "richard.hendricks@piedpiper.com",
                                "Richard",
                                "Hendricks",
                                new BigDecimal("70000.00")
                        )
                ),
                optionalAccount
        );
    }

    @Test
    void findFirstAndLastName() {
        final Optional<Account> optionalAccount = dataRepository.findFirstAndLastName(
                "SELECT first_name, last_name FROM account WHERE first_name = ?",
                "Richard"
        ).join();
        assertEquals(
                Optional.of(new Account("Richard", "Hendricks")),
                optionalAccount
        );
    }

    @Test
    void findLastAndFirstName() {
        final Optional<Account> optionalAccount = dataRepository.findLastAndFirstName(
                "SELECT last_name, first_name FROM account WHERE first_name = ?",
                "Richard"
        ).join();
        assertEquals(
                Optional.of(new Account("Richard", "Hendricks")),
                optionalAccount
        );
    }
    // end::content[]

    @AfterAll
    static void afterAll() {
        POSTGRESQL_TEST_DB.stop();
    }
}