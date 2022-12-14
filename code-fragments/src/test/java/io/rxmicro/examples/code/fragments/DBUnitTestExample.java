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

package io.rxmicro.examples.code.fragments;

import io.rxmicro.test.dbunit.ExpectedDataSet;
import io.rxmicro.test.dbunit.InitialDataSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.rxmicro.test.dbunit.TestDatabaseConfig.getCurrentTestDatabaseConfig;

public class DBUnitTestExample {

    private PostgresqlTestDockerContainer postgresqlTestDockerContainer;

    // tag::config[]
    @BeforeEach
    void beforeEach() {
        getCurrentTestDatabaseConfig()
                .setHost(postgresqlTestDockerContainer.getHost())
                .setPort(postgresqlTestDockerContainer.getFirstMappedPort());
    }
    // end::config[]

    // tag::prepare[]
    @Test
    // <1>
    @InitialDataSet("dataset/rxmicro-test-dataset-two-rows-only.xml")
    void prepare_database() {
        // do something with prepared database
    }
    // end::prepare[]

    // tag::verify[]
    @Test
    // <1>
    @ExpectedDataSet("dataset/rxmicro-test-dataset-two-rows-only.xml")
    void verify_database_state() {
        // change database state
    }
    // end::verify[]

    private interface PostgresqlTestDockerContainer {

        String getHost();

        int getFirstMappedPort();
    }
}
