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

package io.rxmicro.examples.data.mongo.aggregate;

import io.rxmicro.data.mongo.MongoConfig;
import io.rxmicro.examples.data.mongo.aggregate.model.Report;
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.junit.RxMicroComponentTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static io.rxmicro.examples.data.mongo.aggregate.model.Role.CEO;
import static io.rxmicro.examples.data.mongo.aggregate.model.Role.Engineer;
import static io.rxmicro.examples.data.mongo.aggregate.model.Role.Lead_Engineer;
import static io.rxmicro.examples.data.mongo.aggregate.model.Role.Systems_Architect;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@RxMicroComponentTest(DataRepository.class)
final class DataRepository_IntegrationTest {

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
    void aggregate() {
        final List<Report> reports = requireNonNull(dataRepository.aggregate().collectList().block());
        assertEquals(
                List.of(
                        new Report(CEO, new BigDecimal("70000.00")),
                        new Report(Systems_Architect, new BigDecimal("20000.00")),
                        new Report(Lead_Engineer, new BigDecimal("10000.00")),
                        new Report(Engineer, new BigDecimal("10000.00"))
                ),
                reports
        );
    }

    @AfterAll
    static void afterAll() {
        MONGO_TEST_DB.stop();
    }
}