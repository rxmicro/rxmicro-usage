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

package io.rxmicro.examples.data.mongo.find;

import io.rxmicro.data.Pageable;
import io.rxmicro.data.SortOrder;
import io.rxmicro.data.mongo.MongoConfig;
import io.rxmicro.examples.data.mongo.find.model.Account;
import io.rxmicro.examples.data.mongo.find.model.Role;
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.junit.RxMicroComponentTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;

import static io.rxmicro.data.SortOrder.ASCENDING;
import static io.rxmicro.examples.data.mongo.find.model.Role.Engineer;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@RxMicroComponentTest(DataRepository.class)
final class DataRepository_IntegrationTest {

    private static final Account CARLA_WALTON = new Account(
            4L,
            "carla.walton@piedpiper.com",
            "Carla",
            "Walton",
            new BigDecimal("5000.00"),
            Engineer
    );

    private static final Account ELIZABET_KIRSIPUU = new Account(
            6L,
            "elizabet.kirsipuu@piedpiper.com",
            "Elizabet",
            "Kirsipuu",
            new BigDecimal("3000.00"),
            Engineer
    );

    private static final Account SANJAY_BASU = new Account(
            5L,
            "sanjay.basu@piedpiper.com",
            "Sanjay",
            "Basu",
            new BigDecimal("2000.00"),
            Role.Engineer
    );

    @Container
    private static final GenericContainer<?> MONGO_TEST_DB =
            new GenericContainer<>("rxmicro/mongo-test-db")
                    .withExposedPorts(27017);

    @WithConfig
    private static final MongoConfig MONGO_CONFIG = new MongoConfig()
            .setDatabase("rxmicro");

    @BeforeAll
    static void beforeAll() {
        MONGO_TEST_DB.start();
        MONGO_CONFIG
                .setHost(MONGO_TEST_DB.getHost())
                .setPort(MONGO_TEST_DB.getFirstMappedPort());
    }

    private DataRepository dataRepository;

    @Test
    void findById() {
        final Account account =
                requireNonNull(dataRepository.findById(4L).block());
        assertEquals(CARLA_WALTON, account);
    }

    @Test
    void findWithProjectionById() {
        final Account account =
                requireNonNull(dataRepository.findWithProjectionById(4L).block());
        assertEquals(
                new Account(
                        4L,
                        null,
                        "Carla",
                        "Walton",
                        null,
                        null
                ),
                account
        );
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void findByRole(final boolean useSortOrder) {
        final Flux<Account> flux;
        if (useSortOrder) {
            flux = dataRepository.findByRole(Engineer, ASCENDING, new Pageable(0, 2));
        } else {
            flux = dataRepository.findByRole(Engineer, new Pageable(0, 2));
        }

        final List<Account> accounts = requireNonNull(flux.collectList().block());
        assertEquals(List.of(SANJAY_BASU, ELIZABET_KIRSIPUU), accounts);
    }

    @Test
    void findByRole() {
        final List<Account> accounts =
                requireNonNull(dataRepository.findByRole(
                        Engineer,
                        SortOrder.DESCENDING,
                        new Pageable(0, 2)).collectList().block());
        assertEquals(List.of(CARLA_WALTON, ELIZABET_KIRSIPUU), accounts);
    }

    @AfterAll
    static void afterAll() {
        MONGO_TEST_DB.stop();
    }
}