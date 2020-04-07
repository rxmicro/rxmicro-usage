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
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.junit.RxMicroComponentTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

// tag::content[]
// <1>
@Testcontainers
// <2>
@RxMicroComponentTest(DataRepository.class)
final class DataRepositoryTestTemplate1 {

    // <3>
    @Container
    private static GenericContainer<?> postgresqlTestDb =
            new GenericContainer<>("rxmicro/postgres-test-db")
                    .withExposedPorts(5432); // <4>

    // <5>
    @WithConfig
    private static PostgreSQLConfig config = new PostgreSQLConfig()
            .setDatabase("rxmicro")
            .setUser("rxmicro")
            .setPassword("password"); // <6>

    @BeforeAll
    static void beforeAll() {
        postgresqlTestDb.start(); // <7>
        config
                .setHost(postgresqlTestDb.getContainerIpAddress()) // <8>
                .setPort(postgresqlTestDb.getFirstMappedPort());
    }

    private DataRepository dataRepository; // <9>

    // ... test methods must be here

    @AfterAll
    static void afterAll() {
        postgresqlTestDb.stop(); // <10>
    }
}
// end::content[]
