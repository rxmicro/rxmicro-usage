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

package io.rxmicro.examples.data.r2dbc.postgresql.select.returnmodeltypes;

import io.rxmicro.data.sql.model.EntityFieldList;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig;
import io.rxmicro.examples.data.r2dbc.postgresql.select.returnmodeltypes.model.Account;
import io.rxmicro.examples.data.r2dbc.postgresql.select.returnmodeltypes.model.Role;
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

import static io.rxmicro.common.util.ExCollections.orderedMap;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.examples.data.r2dbc.postgresql.select.returnmodeltypes.model.Role.CEO;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@RxMicroComponentTest(SelectSingleDataRepository.class)
final class SelectSingleDataRepositoryTest {

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

    private SelectSingleDataRepository dataRepository;

    @Test
    void findSingleAccount() {
        final Account account = dataRepository.findSingleAccount().join();
        assertEquals(new Account("Richard", "Hendricks"), account);
    }

    @Test
    void findSingleEntityFieldMap() {
        final EntityFieldMap entityFieldMap = dataRepository.findSingleEntityFieldMap().join();
        assertEquals(
                orderedMap(
                        "first_name", "Richard",
                        "last_name", "Hendricks"
                ),
                entityFieldMap
        );
    }

    @Test
    void findSingleEntityFieldList() {
        final EntityFieldList entityFieldList = dataRepository.findSingleEntityFieldList().join();
        assertEquals(List.of("Richard", "Hendricks"), entityFieldList);
    }

    @Test
    void findSingleEmail() {
        final String email = dataRepository.findSingleEmail().join();
        assertEquals("richard.hendricks@piedpiper.com", email);
    }

    @Test
    void findSingleRole() {
        final Role role = dataRepository.findSingleRole().join();
        assertEquals(CEO, role);
    }

    @Test
    void findSingleBalance() {
        final BigDecimal expectedBalance = new BigDecimal("70000");
        final BigDecimal actualBalance = dataRepository.findSingleBalance().join();

        assertEquals(
                0,
                expectedBalance.compareTo(actualBalance),
                format("Invalid balance: expected '?' but actual is '?'",
                        expectedBalance, actualBalance)
        );
    }

    @AfterAll
    static void afterAll() {
        POSTGRESQL_TEST_DB.stop();
    }
}