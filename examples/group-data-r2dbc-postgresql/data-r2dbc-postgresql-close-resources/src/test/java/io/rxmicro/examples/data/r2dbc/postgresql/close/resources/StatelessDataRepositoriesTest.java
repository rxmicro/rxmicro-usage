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

package io.rxmicro.examples.data.r2dbc.postgresql.close.resources;

import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig;
import io.rxmicro.examples.data.r2dbc.postgresql.close.resources.delete.DeleteNothingSuccessfulDataRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.close.resources.delete.DeleteUsingInvalidSQLDataRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.close.resources.insert.InsertNothingSuccessfulDataRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.close.resources.insert.InsertUsingInvalidSQLDataRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.close.resources.select.SelectManySuccessfulDataRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.close.resources.select.SelectOneSuccessfulDataRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.close.resources.select.SelectUsingInvalidEntityModelDataRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.close.resources.select.SelectUsingInvalidSQLDataRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.close.resources.update.UpdateNothingSuccessfulDataRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.close.resources.update.UpdateUsingInvalidSQLDataRepository;
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.junit.RxMicroComponentTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfigCustomizer.setConnectionDecorator;

@Testcontainers
@RxMicroComponentTest(Object.class)
final class StatelessDataRepositoriesTest {

    @Container
    final static GenericContainer<?> POSTGRESQL_TEST_DB =
            new GenericContainer<>("rxmicro/postgres-test-db")
                    .withExposedPorts(5432);

    @WithConfig
    final static PostgreSQLConfig CONFIG = new PostgreSQLConfig()
            .setDatabase("rxmicro")
            .setUser("rxmicro")
            .setPassword("password");

    static {
        setConnectionDecorator(Spies::decorateConnection);
    }

    @BeforeAll
    static void beforeAll() {
        POSTGRESQL_TEST_DB.start();
        CONFIG
                .setHost(POSTGRESQL_TEST_DB.getHost())
                .setPort(POSTGRESQL_TEST_DB.getFirstMappedPort());
    }

    @AfterAll
    static void afterAll() {
        POSTGRESQL_TEST_DB.stop();
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------- SELECT -------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    @Nested
    @RxMicroComponentTest(SelectOneSuccessfulDataRepository.class)
    class SelectOneSuccessful extends AbstractDataRepositoryTest<SelectOneSuccessfulDataRepository> {

        private SelectOneSuccessfulDataRepository repository;

        SelectOneSuccessful() {
            super(false);
        }

        @Override
        protected SelectOneSuccessfulDataRepository getRepository() {
            return repository;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Nested
    @RxMicroComponentTest(SelectManySuccessfulDataRepository.class)
    class SelectManySuccessful extends AbstractDataRepositoryTest<SelectManySuccessfulDataRepository> {

        private SelectManySuccessfulDataRepository repository;

        SelectManySuccessful() {
            super(false);
        }

        @Override
        protected SelectManySuccessfulDataRepository getRepository() {
            return repository;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Nested
    @RxMicroComponentTest(SelectUsingInvalidSQLDataRepository.class)
    class SelectUsingInvalidSQL extends AbstractDataRepositoryTest<SelectUsingInvalidSQLDataRepository> {

        private SelectUsingInvalidSQLDataRepository repository;

        SelectUsingInvalidSQL() {
            super(true);
        }

        @Override
        protected SelectUsingInvalidSQLDataRepository getRepository() {
            return repository;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Nested
    @RxMicroComponentTest(SelectUsingInvalidEntityModelDataRepository.class)
    class SelectUsingInvalidEntityModel extends AbstractDataRepositoryTest<SelectUsingInvalidEntityModelDataRepository> {

        private SelectUsingInvalidEntityModelDataRepository repository;

        SelectUsingInvalidEntityModel() {
            super(true);
        }

        @Override
        protected SelectUsingInvalidEntityModelDataRepository getRepository() {
            return repository;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------- INSERT -------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    @Nested
    @RxMicroComponentTest(InsertNothingSuccessfulDataRepository.class)
    class InsertNothingSuccessful extends AbstractDataRepositoryTest<InsertNothingSuccessfulDataRepository> {

        private InsertNothingSuccessfulDataRepository repository;

        InsertNothingSuccessful() {
            super(false);
        }

        @Override
        protected InsertNothingSuccessfulDataRepository getRepository() {
            return repository;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Nested
    @RxMicroComponentTest(InsertUsingInvalidSQLDataRepository.class)
    class InsertUsingInvalidSQL extends AbstractDataRepositoryTest<InsertUsingInvalidSQLDataRepository> {

        private InsertUsingInvalidSQLDataRepository repository;

        InsertUsingInvalidSQL() {
            super(true);
        }

        @Override
        protected InsertUsingInvalidSQLDataRepository getRepository() {
            return repository;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------- UPDATE -------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    @Nested
    @RxMicroComponentTest(UpdateNothingSuccessfulDataRepository.class)
    class UpdateNothingSuccessful extends AbstractDataRepositoryTest<UpdateNothingSuccessfulDataRepository> {

        private UpdateNothingSuccessfulDataRepository repository;

        UpdateNothingSuccessful() {
            super(false);
        }

        @Override
        protected UpdateNothingSuccessfulDataRepository getRepository() {
            return repository;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Nested
    @RxMicroComponentTest(UpdateUsingInvalidSQLDataRepository.class)
    class UpdateUsingInvalidSQL extends AbstractDataRepositoryTest<UpdateUsingInvalidSQLDataRepository> {

        private UpdateUsingInvalidSQLDataRepository repository;

        UpdateUsingInvalidSQL() {
            super(true);
        }

        @Override
        protected UpdateUsingInvalidSQLDataRepository getRepository() {
            return repository;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------- DELETE -------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    @Nested
    @RxMicroComponentTest(DeleteNothingSuccessfulDataRepository.class)
    class DeleteNothingSuccessful extends AbstractDataRepositoryTest<DeleteNothingSuccessfulDataRepository> {

        private DeleteNothingSuccessfulDataRepository repository;

        DeleteNothingSuccessful() {
            super(false);
        }

        @Override
        protected DeleteNothingSuccessfulDataRepository getRepository() {
            return repository;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Nested
    @RxMicroComponentTest(DeleteUsingInvalidSQLDataRepository.class)
    class DeleteUsingInvalidSQL extends AbstractDataRepositoryTest<DeleteUsingInvalidSQLDataRepository> {

        private DeleteUsingInvalidSQLDataRepository repository;

        DeleteUsingInvalidSQL() {
            super(true);
        }

        @Override
        protected DeleteUsingInvalidSQLDataRepository getRepository() {
            return repository;
        }
    }
}
