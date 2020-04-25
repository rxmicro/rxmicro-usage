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

import io.rxmicro.data.mongo.MongoCodecsConfigurator;
import io.rxmicro.data.mongo.MongoConfig;
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.junit.RxMicroComponentTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.function.Function;
import java.util.stream.Stream;

import static io.rxmicro.examples.data.mongo.supported.types.model.TestSupportedTypesInstance.SUPPORTED_TYPES;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@RxMicroComponentTest(DeleteDataRepository.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class DeleteDataRepository_IntegrationTest {

    @WithConfig
    private static final MongoConfig MONGO_CONFIG = new MongoConfig()
            .setDatabase("rxmicro")
            .setMongoCodecsConfigurator(new MongoCodecsConfigurator()
                    .withDefaultConfiguration()
                    .withExtendJavaCodecs()
                    .withExtendMongoCodecs()
            );

    @Container
    private final GenericContainer<?> mongoTestDb =
            new GenericContainer<>("rxmicro/mongo-test-db")
                    .withExposedPorts(27017);

    private DeleteDataRepository dataRepository;

    static Stream<Function<DeleteDataRepository, Long>> methodProvider() {
        return Stream.of(
                repository -> repository.delete1(SUPPORTED_TYPES.getId()).block(),
                repository -> repository.delete2(SUPPORTED_TYPES).block(),
                repository -> repository.delete3().block(),
                repository -> repository.delete4(
                        SUPPORTED_TYPES.getId(),
                        SUPPORTED_TYPES.getStatus(),
                        SUPPORTED_TYPES.getaBoolean(),
                        SUPPORTED_TYPES.getaByte(),
                        SUPPORTED_TYPES.getaShort(),
                        SUPPORTED_TYPES.getaInteger(),
                        SUPPORTED_TYPES.getaLong(),
                        SUPPORTED_TYPES.getBigDecimal(),
                        SUPPORTED_TYPES.getCharacter(),
                        SUPPORTED_TYPES.getString(),
                        SUPPORTED_TYPES.getInstant(),
                        SUPPORTED_TYPES.getUuid()
                ).block()
        );
    }

    @BeforeEach
    void beforeEach() {
        MONGO_CONFIG
                .setHost(mongoTestDb.getContainerIpAddress())
                .setPort(mongoTestDb.getFirstMappedPort());
    }

    @ParameterizedTest
    @MethodSource("methodProvider")
    void delete(final Function<DeleteDataRepository, Long> method) {
        assertEquals(1L, method.apply(dataRepository));
    }
}