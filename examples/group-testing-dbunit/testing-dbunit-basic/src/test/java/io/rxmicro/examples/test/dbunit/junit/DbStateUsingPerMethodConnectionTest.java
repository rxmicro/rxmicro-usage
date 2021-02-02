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
import io.rxmicro.test.dbunit.junit.DbUnitTest;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.rxmicro.test.dbunit.TestDatabaseConfig.getCurrentTestDatabaseConfig;
import static io.rxmicro.test.dbunit.junit.RetrieveConnectionStrategy.PER_TEST_METHOD;

// tag::content[]
@RxMicroIntegrationTest
@Testcontainers
// <1>
@DbUnitTest(retrieveConnectionStrategy = PER_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class DbStateUsingPerMethodConnectionTest {

    @Container
    private final GenericContainer<?> postgresqlTestDb =
            new GenericContainer<>("rxmicro/postgres-test-db")
                    .withExposedPorts(5432);

    @BeforeEach
    void beforeEach() {
        getCurrentTestDatabaseConfig()
                .setHost(postgresqlTestDb.getContainerIpAddress())
                .setPort(postgresqlTestDb.getFirstMappedPort());
    }

    @Test
    @ExpectedDataSet("dataset/rxmicro-test-dataset.xml")
    @Order(1)
    void Should_contain_expected_dataset() {

    }

    @Test
    @InitialDataSet("dataset/rxmicro-test-dataset-two-rows-only.xml")
    @ExpectedDataSet("dataset/rxmicro-test-dataset-two-rows-only.xml")
    @Order(2)
    void Should_set_and_compare_dataset() {

    }
}
// end::content[]
