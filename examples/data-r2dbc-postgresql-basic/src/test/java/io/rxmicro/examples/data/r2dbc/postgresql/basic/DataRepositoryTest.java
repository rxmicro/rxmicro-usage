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

package io.rxmicro.examples.data.r2dbc.postgresql.basic;

import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig;
import io.rxmicro.examples.data.r2dbc.postgresql.basic.entity.Account;
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.junit.RxMicroComponentTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
@Testcontainers
@RxMicroComponentTest(DataRepository.class)
final class DataRepositoryTest {

    @Container
    private GenericContainer<?> postgresqlTestDb =
            new GenericContainer<>("rxmicro/postgres-test-db")
                    .withExposedPorts(5432);

    @WithConfig
    private PostgreSQLConfig config = new PostgreSQLConfig()
            .setDatabase("rxmicro")
            .setUser("rxmicro")
            .setPassword("password");

    private DataRepository dataRepository;

    @BeforeEach
    void beforeEach() {
        config
                .setHost(postgresqlTestDb.getContainerIpAddress())
                .setPort(postgresqlTestDb.getFirstMappedPort());
    }

    @Test
    void Should_find_account() {
        final Account account = requireNonNull(
                dataRepository.findByEmail("richard.hendricks@piedpiper.com").block()
        );

        assertEquals("Richard", account.getFirstName());
        assertEquals("Hendricks", account.getLastName());
    }
}
// end::content[]