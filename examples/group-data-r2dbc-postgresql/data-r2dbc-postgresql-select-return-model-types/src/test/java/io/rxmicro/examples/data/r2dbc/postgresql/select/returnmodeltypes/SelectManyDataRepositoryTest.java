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
import static io.rxmicro.examples.data.r2dbc.postgresql.select.returnmodeltypes.model.Role.CEO;
import static io.rxmicro.examples.data.r2dbc.postgresql.select.returnmodeltypes.model.Role.Lead_Engineer;
import static io.rxmicro.examples.data.r2dbc.postgresql.select.returnmodeltypes.model.Role.Systems_Architect;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@RxMicroComponentTest(SelectManyDataRepository.class)
final class SelectManyDataRepositoryTest {

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
                .setHost(POSTGRESQL_TEST_DB.getContainerIpAddress())
                .setPort(POSTGRESQL_TEST_DB.getFirstMappedPort());
    }

    private SelectManyDataRepository dataRepository;

    @Test
    void findAllAccounts() {
        final List<Account> accounts = dataRepository.findAllAccounts().join();
        assertEquals(
                List.of(
                        new Account("Richard", "Hendricks"),
                        new Account("Bertram", "Gilfoyle"),
                        new Account("Dinesh", "Chugtai")
                ),
                accounts
        );
    }

    @Test
    void findAllEntityFieldMapList() {
        final List<EntityFieldMap> accounts = dataRepository.findAllEntityFieldMapList().join();
        assertEquals(
                List.of(
                        orderedMap(
                                "first_name", "Richard",
                                "last_name", "Hendricks"
                        ),
                        orderedMap(
                                "first_name", "Bertram",
                                "last_name", "Gilfoyle"
                        ),
                        orderedMap(
                                "first_name", "Dinesh",
                                "last_name", "Chugtai"
                        )
                ),
                accounts
        );
    }

    @Test
    void findAllEntityFieldList() {
        final List<EntityFieldList> entityFieldLists = dataRepository.findAllEntityFieldList().join();
        assertEquals(
                List.of(
                        List.of("Richard", "Hendricks"),
                        List.of("Bertram", "Gilfoyle"),
                        List.of("Dinesh", "Chugtai")
                ),
                entityFieldLists
        );
    }

    @Test
    void findAllEmails() {
        final List<String> emails = dataRepository.findAllEmails().join();
        assertEquals(
                List.of(
                        "richard.hendricks@piedpiper.com",
                        "bertram.gilfoyle@piedpiper.com",
                        "dinesh.chugtai@piedpiper.com"
                ),
                emails);
    }

    @Test
    void findAllRoles() {
        final List<Role> roles = dataRepository.findAllRoles().join();
        assertEquals(List.of(CEO, Lead_Engineer, Systems_Architect), roles);
    }

    @Test
    void findAllBalances() {
        final List<BigDecimal> balances = dataRepository.findAllBalances().join();
        assertEquals(
                List.of(
                        new BigDecimal("10000.00"),
                        new BigDecimal("20000.00"),
                        new BigDecimal("70000.00")
                ),
                balances
        );
    }

    @AfterAll
    static void afterAll() {
        POSTGRESQL_TEST_DB.stop();
    }
}