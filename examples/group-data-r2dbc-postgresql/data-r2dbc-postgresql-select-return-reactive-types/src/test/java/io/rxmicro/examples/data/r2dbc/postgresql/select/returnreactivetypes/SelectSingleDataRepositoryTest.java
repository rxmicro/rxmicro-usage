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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
@Testcontainers
@RxMicroComponentTest(SelectSingleDataRepository.class)
final class SelectSingleDataRepositoryTest {

    private static final String RICHARD_HENDRICKS_EMAIL = "richard.hendricks@piedpiper.com";

    private static final String NOT_FOUND_EMAIL = "not-found@piedpiper.com";

    private static final Account EXPECTED_ACCOUNT = new Account("Richard", "Hendricks");

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
    void findByEmail1_should_return_account() {
        final Optional<Account> optionalAccount =
                dataRepository.findByEmail1(RICHARD_HENDRICKS_EMAIL).blockOptional();
        assertEquals(Optional.of(EXPECTED_ACCOUNT), optionalAccount);
    }

    @Test
    void findByEmail1_should_return_empty() {
        final Optional<Account> optionalAccount =
                dataRepository.findByEmail1(NOT_FOUND_EMAIL).blockOptional();
        assertEquals(Optional.empty(), optionalAccount);
    }

    @Test
    void findByEmail2_should_return_account() {
        final Account account =
                dataRepository.findByEmail2(RICHARD_HENDRICKS_EMAIL).join();
        assertEquals(EXPECTED_ACCOUNT, account);
    }

    @Test
    void findByEmail3_should_return_empty() {
        final Account account =
                dataRepository.findByEmail3(RICHARD_HENDRICKS_EMAIL).toCompletableFuture().join();
        assertEquals(EXPECTED_ACCOUNT, account);
    }

    @Test
    void findByEmail4_should_return_account() {
        final Optional<Account> optionalAccount =
                dataRepository.findByEmail4(RICHARD_HENDRICKS_EMAIL).join();
        assertEquals(Optional.of(EXPECTED_ACCOUNT), optionalAccount);
    }

    @Test
    void findByEmail4_should_return_empty() {
        final Optional<Account> optionalAccount =
                dataRepository.findByEmail4(NOT_FOUND_EMAIL).join();
        assertEquals(Optional.empty(), optionalAccount);
    }

    @Test
    void findByEmail5_should_return_account() {
        final Optional<Account> optionalAccount =
                dataRepository.findByEmail5(RICHARD_HENDRICKS_EMAIL).toCompletableFuture().join();
        assertEquals(Optional.of(EXPECTED_ACCOUNT), optionalAccount);
    }

    @Test
    void findByEmail5_should_return_empty() {
        final Optional<Account> optionalAccount =
                dataRepository.findByEmail5(NOT_FOUND_EMAIL).toCompletableFuture().join();
        assertEquals(Optional.empty(), optionalAccount);
    }

    @Test
    void findByEmail6_should_return_account() {
        final Account account =
                dataRepository.findByEmail6(RICHARD_HENDRICKS_EMAIL).blockingGet();
        assertEquals(EXPECTED_ACCOUNT, account);
    }

    @Test
    void findByEmail7_should_return_account() {
        final Optional<Account> optionalAccount =
                Optional.ofNullable(dataRepository.findByEmail7(RICHARD_HENDRICKS_EMAIL).blockingGet());
        assertEquals(Optional.of(EXPECTED_ACCOUNT), optionalAccount);
    }

    @Test
    void findByEmail7_should_return_empty() {
        final Optional<Account> optionalAccount =
                Optional.ofNullable(dataRepository.findByEmail7(NOT_FOUND_EMAIL).blockingGet());
        assertEquals(Optional.empty(), optionalAccount);
    }

    @AfterAll
    static void afterAll() {
        POSTGRESQL_TEST_DB.stop();
    }
}
// end::content[]