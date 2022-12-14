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

package io.rxmicro.examples.data.mongo.basic;

import io.rxmicro.data.mongo.MongoConfig;
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
    private static final GenericContainer<?> MONGO_TEST_DB =
            new GenericContainer<>("rxmicro/mongo-test-db")
                    .withExposedPorts(27017); // <4>

    // <5>
    @WithConfig
    private static final MongoConfig MONGO_CONFIG = new MongoConfig()
            .setDatabase("rxmicro"); // <6>

    @BeforeAll
    static void beforeAll() {
        MONGO_TEST_DB.start(); // <7>
        MONGO_CONFIG
                .setHost(MONGO_TEST_DB.getHost()) // <8>
                .setPort(MONGO_TEST_DB.getFirstMappedPort());
    }

    private DataRepository dataRepository; // <9>

    // ... test methods must be here

    @AfterAll
    static void afterAll() {
        MONGO_TEST_DB.stop(); // <10>
    }
}
// end::content[]
