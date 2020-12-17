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
import io.rxmicro.examples.data.mongo.supported.types.model.SupportedTypesDocument;
import io.rxmicro.examples.data.mongo.supported.types.model.SupportedTypesEntity;
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

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.stream.Stream;

import static io.rxmicro.data.mongo.MongoConfigCustomizer.getCurrentMongoCodecsConfigurator;
import static io.rxmicro.examples.data.mongo.supported.types.model.TestSupportedTypesInstance.SUPPORTED_TYPES;
import static io.rxmicro.examples.data.mongo.supported.types.model.TestSupportedTypesInstance.newSupportedTypes;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@RxMicroComponentTest(UpdateDataRepository.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class UpdateDataRepository_IntegrationTest {

    @Container
    private static final GenericContainer<?> MONGO_TEST_DB =
            new GenericContainer<>("rxmicro/mongo-test-db")
                    .withExposedPorts(27017);

    @WithConfig
    private static final MongoConfig MONGO_CONFIG = new MongoConfig()
            .setDatabase("rxmicro");

    static {
        getCurrentMongoCodecsConfigurator()
                .withDefaultConfiguration()
                .withExtendJavaCodecs()
                .withExtendMongoCodecs();
    }

    @BeforeAll
    static void beforeAll() {
        MONGO_TEST_DB.start();
        MONGO_CONFIG
                .setHost(MONGO_TEST_DB.getContainerIpAddress())
                .setPort(MONGO_TEST_DB.getFirstMappedPort());
    }

    private UpdateDataRepository dataRepository;

    static Stream<Function<UpdateDataRepository, Long>> methodProvider() {
        return Stream.of(
                repository -> repository.update1(
                        newSupportedTypes(SupportedTypesEntity.class)
                                .setBigDecimal(new BigDecimal("0.1"))
                ).block(),
                repository -> repository.update2(
                        newSupportedTypes(SupportedTypesDocument.class)
                                .setBigDecimal(new BigDecimal("0.2")),
                        SUPPORTED_TYPES.getId()
                ).block(),
                repository -> repository.update3().block(),
                repository -> repository.update4(
                        SUPPORTED_TYPES.getStatus(),
                        SUPPORTED_TYPES.getaBoolean(),
                        SUPPORTED_TYPES.getaByte(),
                        SUPPORTED_TYPES.getaShort(),
                        SUPPORTED_TYPES.getaInteger(),
                        SUPPORTED_TYPES.getaLong(),
                        new BigDecimal("0.4"),
                        SUPPORTED_TYPES.getCharacter(),
                        SUPPORTED_TYPES.getString(),
                        SUPPORTED_TYPES.getInstant(),
                        SUPPORTED_TYPES.getUuid(),
                        SUPPORTED_TYPES.getId()
                ).block()
        );
    }

    @ParameterizedTest
    @MethodSource("methodProvider")
    void update(final Function<UpdateDataRepository, Long> method) {
        assertEquals(1L, method.apply(dataRepository));
    }

    @AfterAll
    static void afterAll() {
        MONGO_TEST_DB.stop();
    }
}