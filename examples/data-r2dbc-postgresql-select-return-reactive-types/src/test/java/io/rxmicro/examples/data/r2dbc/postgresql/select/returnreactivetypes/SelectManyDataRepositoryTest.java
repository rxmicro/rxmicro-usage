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

package io.rxmicro.examples.data.r2dbc.postgresql.select.returnreactivetypes;

import io.reactivex.rxjava3.functions.BiConsumer;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig;
import io.rxmicro.examples.data.r2dbc.postgresql.select.returnreactivetypes.model.Account;
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.junit.RxMicroComponentTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@RxMicroComponentTest(SelectManyDataRepository.class)
final class SelectManyDataRepositoryTest {

    private static final List<Account> EXPECTED_ACCOUNTS = List.of(
            new Account("Richard", "Hendricks"),
            new Account("Bertram", "Gilfoyle"),
            new Account("Dinesh", "Chugtai")
    );

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

    private SelectManyDataRepository dataRepository;

    @Test
    void findAll1_should_return_all_accounts() {
        final List<Account> actualAccounts = dataRepository.findAll1().block();
        assertEquals(EXPECTED_ACCOUNTS, actualAccounts);
    }

    @Test
    void findAll2_should_return_all_accounts() {
        final List<Account> actualAccounts = dataRepository.findAll2().collectList().block();
        assertEquals(EXPECTED_ACCOUNTS, actualAccounts);
    }

    @Test
    void findAll3_should_return_all_accounts() {
        final List<Account> actualAccounts = dataRepository.findAll3().join();
        assertEquals(EXPECTED_ACCOUNTS, actualAccounts);
    }

    @Test
    void findAll4_should_return_all_accounts() {
        final List<Account> actualAccounts = dataRepository.findAll4().toCompletableFuture().join();
        assertEquals(EXPECTED_ACCOUNTS, actualAccounts);
    }

    @Test
    void findAll5_should_return_all_accounts() {
        final List<Account> actualAccounts = dataRepository.findAll5()
                .collect(ArrayList::new, (BiConsumer<List<Account>, Account>) List::add)
                .blockingGet();
        assertEquals(EXPECTED_ACCOUNTS, actualAccounts);
    }

    @Test
    void findAll6_should_return_all_accounts() {
        final List<Account> actualAccounts = dataRepository.findAll6().blockingGet();
        assertEquals(EXPECTED_ACCOUNTS, actualAccounts);
    }

    @AfterAll
    static void afterAll() {
        postgresqlTestDb.stop();
    }
}