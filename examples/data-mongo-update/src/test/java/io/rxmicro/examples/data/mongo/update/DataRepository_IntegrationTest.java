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

package io.rxmicro.examples.data.mongo.update;

import com.mongodb.client.result.UpdateResult;
import io.rxmicro.data.mongo.MongoConfig;
import io.rxmicro.examples.data.mongo.update.model.AccountDocument;
import io.rxmicro.examples.data.mongo.update.model.AccountEntity;
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

import static io.rxmicro.data.RepositoryFactory.getRepository;
import static io.rxmicro.examples.data.mongo.update.model.Role.Engineer;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@RxMicroComponentTest(DataRepository.class)
final class DataRepository_IntegrationTest {

    private static final AccountDocument ACCOUNT_DOCUMENT = new AccountDocument(
            "russ.hanneman@russfest.net",
            "Russ",
            "Hanneman",
            new BigDecimal("1000000000.00")
    );

    private static final BigDecimal NEW_BALANCE = new BigDecimal("100.00");

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

    private DataRepository dataRepository;

    @Test
    void updateEntity() {
        final AccountEntity accountEntity = new AccountEntity(1L, ACCOUNT_DOCUMENT);
        final Boolean updated =
                requireNonNull(dataRepository.updateEntity(accountEntity).block());
        assertTrue(updated);
        assertEquals(
                accountEntity,
                getVerificationRepository().findAccountById(1L).block()
        );
    }

    @Test
    void updateDocument() {
        dataRepository.updateDocument(ACCOUNT_DOCUMENT, 2L).block();
        assertEquals(
                new AccountEntity(2L, ACCOUNT_DOCUMENT),
                getVerificationRepository().findAccountById(2L).block()
        );
    }

    @Test
    void updateById() {
        final Long updated =
                requireNonNull(dataRepository.updateById(NEW_BALANCE, 3L).block());
        assertEquals(1L, updated);
        assertEquals(
                NEW_BALANCE,
                getVerificationRepository().findBalanceById(3L).block()
        );
    }

    @Test
    void updateByRole() {
        final UpdateResult updateResult =
                requireNonNull(dataRepository.updateByRole(NEW_BALANCE, Engineer).block());
        assertEquals(3L, updateResult.getModifiedCount());
        assertEquals(
                List.of(NEW_BALANCE),
                getVerificationRepository().findBalanceByRole(Engineer).block()
        );
    }

    @Test
    void updateAll() {
        final UpdateResult updateResult =
                requireNonNull(dataRepository.updateAll(BigDecimal.ZERO).block());
        assertEquals(6L, updateResult.getModifiedCount());
        assertEquals(
                List.of(BigDecimal.ZERO),
                getVerificationRepository().findAllBalance().block()
        );
    }

    private VerificationRepository getVerificationRepository() {
        return getRepository(VerificationRepository.class);
    }

    @AfterAll
    static void afterAll() {
        MONGO_TEST_DB.stop();
    }
}