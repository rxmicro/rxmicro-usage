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

package io.rxmicro.examples.test.dbunit.junit;

import io.rxmicro.test.dbunit.ExpectedDataSet;
import io.rxmicro.test.dbunit.InitialDataSet;
import io.rxmicro.test.dbunit.RollbackChanges;
import io.rxmicro.test.dbunit.junit.DbUnitTest;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.rxmicro.test.dbunit.TestDatabaseConfig.getCurrentTestDatabaseConfig;

// tag::content[]
@RxMicroIntegrationTest
@Testcontainers
// <1>
@DbUnitTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// FIXME "org.postgresql.util.PSQLException: FATAL: the database system is starting up" and remove @Ignore
@Ignore
final class RollbackChangesTest {

    @Container
    private static final GenericContainer<?> POSTGRESQL_TEST_DB =
            new GenericContainer<>("rxmicro/postgres-test-db")
                    .withExposedPorts(5432);

    @BeforeAll
    static void beforeAll() {
        getCurrentTestDatabaseConfig()
                .setHost(POSTGRESQL_TEST_DB.getContainerIpAddress())
                .setPort(POSTGRESQL_TEST_DB.getFirstMappedPort());
    }

    @Test
    // <2>
    @RollbackChanges
    @InitialDataSet("dataset/rxmicro-test-dataset-empty.xml")
    @ExpectedDataSet("dataset/rxmicro-test-dataset-empty.xml")
    @Order(1)
    void Should_set_and_compare_dataset() {

    }

    @Test
    @ExpectedDataSet("dataset/rxmicro-test-dataset.xml")
    @Order(2)
    void Should_contain_expected_dataset() {

    }
}
// end::content[]
