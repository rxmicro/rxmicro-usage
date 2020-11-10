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

package io.rxmicro.examples.data.mongo.supported.types;

import io.rxmicro.data.mongo.MongoConfig;
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.junit.RxMicroComponentTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static io.rxmicro.data.mongo.MongoConfigCustomizer.getCurrentMongoCodecsConfigurator;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@RxMicroComponentTest(GetDataRepository.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class GetDataRepository_IntegrationTest extends AbstractDataRepositoryIntegrationTest {

    static {
        getCurrentMongoCodecsConfigurator()
                .withDefaultConfiguration()
                .withExtendJavaCodecs()
                .withExtendMongoCodecs();
    }

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
                .setHost(MONGO_TEST_DB.getContainerIpAddress())
                .setPort(MONGO_TEST_DB.getFirstMappedPort());
    }

    private GetDataRepository dataRepository;

    static Stream<String> methodProvider() {
        return METHOD_MAPPING.keySet().stream();
    }

    @ParameterizedTest
    @MethodSource("methodProvider")
    void invoke(final String method) {
        final Map.Entry<Function<GetDataRepository, Optional<?>>, Object> entry = METHOD_MAPPING.get(method);
        assertEquals(
                Optional.of(entry.getValue()),
                entry.getKey().apply(dataRepository)
        );
    }

    @AfterAll
    static void afterAll() {
        MONGO_TEST_DB.stop();
    }
}