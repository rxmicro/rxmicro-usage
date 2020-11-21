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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.time.Duration;

import static io.rxmicro.test.dbunit.junit.RetrieveConnectionStrategy.PER_ALL_TEST_CLASSES;

// tag::content[]
@RxMicroIntegrationTest
@Testcontainers
@DbUnitTest(retrieveConnectionStrategy = PER_ALL_TEST_CLASSES)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class DbStateUsingPerAllTestClassesConnectionTest {

    @Container
    private static final DockerComposeContainer<?> COMPOSE =
            new DockerComposeContainer<>(new File("integration-tests-environment.yml").getAbsoluteFile())
                    .withLocalCompose(true)
                    .withPull(false)
                    .withTailChildContainers(true)
                    .waitingFor("postgres-db", Wait.forLogMessage(".*PostgreSQL init process complete.*\\s", 1));

    @Test
    @ExpectedDataSet("dataset/rxmicro-test-dataset.xml")
    @Order(1)
    void Should_contain_expected_dataset() {

    }

    @Test
    @InitialDataSet("dataset/rxmicro-test-dataset-two-rows-only.xml")
    @ExpectedDataSet("dataset/rxmicro-test-dataset-two-rows-only.xml")
    @Order(2)
    void Should_set_and_compare_dataset(){

    }
}
// end::content[]
