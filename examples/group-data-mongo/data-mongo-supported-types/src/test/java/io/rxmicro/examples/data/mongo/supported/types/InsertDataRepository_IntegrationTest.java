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
import io.rxmicro.examples.data.mongo.supported.types.model.SupportedTypesEntity;
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.junit.RxMicroComponentTest;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.rxmicro.data.mongo.MongoConfigCustomizer.getCurrentMongoCodecsConfigurator;
import static io.rxmicro.examples.data.mongo.supported.types.model.TestSupportedTypesInstance.newSupportedTypes;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@RxMicroComponentTest(InsertDataRepository.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class InsertDataRepository_IntegrationTest {

    @WithConfig
    private static final MongoConfig MONGO_CONFIG = new MongoConfig()
            .setDatabase("rxmicro");

    static {
        getCurrentMongoCodecsConfigurator()
                .withDefaultConfiguration()
                .withExtendJavaCodecs()
                .withExtendMongoCodecs();
    }

    @Container
    private final GenericContainer<?> mongoTestDb =
            new GenericContainer<>("rxmicro/mongo-test-db")
                    .withExposedPorts(27017);

    private InsertDataRepository dataRepository;

    @BeforeEach
    void beforeEach() {
        MONGO_CONFIG
                .setHost(mongoTestDb.getContainerIpAddress())
                .setPort(mongoTestDb.getFirstMappedPort());
    }

    @Test
    void insert() {
        final SupportedTypesEntity newSupportedTypes = newSupportedTypes(SupportedTypesEntity.class)
                .setId(new ObjectId());
        assertEquals(newSupportedTypes, dataRepository.insert(newSupportedTypes).block());
    }
}