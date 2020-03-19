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
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.examples.data.mongo.update.model.AccountDocument;
import io.rxmicro.examples.data.mongo.update.model.AccountEntity;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import io.rxmicro.test.mockito.mongo.UpdateOperationMock;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Map;

import static io.rxmicro.examples.data.mongo.update.DataRepository.COLLECTION_NAME;
import static io.rxmicro.examples.data.mongo.update.model.Role.CEO;
import static io.rxmicro.test.mockito.mongo.MongoMockFactory.prepareMongoOperationMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@InitMocks
@RxMicroComponentTest(DataRepository.class)
final class DataRepository_UnitTest {

    private static final AccountDocument ACCOUNT_DOCUMENT = new AccountDocument(
            "russ.hanneman@russfest.net",
            "Russ",
            "Hanneman",
            new BigDecimal("1000000000.00")
    );

    private static final UpdateOperationMock UPDATE_ENTITY_OPERATION_MOCK = new UpdateOperationMock.Builder()
            .setUpdate(new Document("$set", new Document(Map.of(
                    "email", ACCOUNT_DOCUMENT.getEmail(),
                    "firstName", ACCOUNT_DOCUMENT.getFirstName(),
                    "lastName", ACCOUNT_DOCUMENT.getLastName(),
                    "balance", ACCOUNT_DOCUMENT.getBalance()
            ))))
            .setFilter(new Document("_id", 100L))
            .build();

    private static final UpdateOperationMock UPDATE_BY_ROLE_OPERATION_MOCK = new UpdateOperationMock.Builder()
            .setUpdate(new Document("$set", new Document("balance", BigDecimal.ZERO)))
            .setFilter(new Document("role", CEO))
            .build();

    private DataRepository dataRepository;

    @Mock
    @Alternative
    private MongoDatabase mongoDatabase;

    @Mock
    private UpdateResult updateResult;

    void prepareUpdateEntitySuccess() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                UPDATE_ENTITY_OPERATION_MOCK,
                1L
        );
    }

    @Test
    @BeforeTest(method = "prepareUpdateEntitySuccess")
    void updateEntitySuccess() {
        assertEquals(true, dataRepository.updateEntity(new AccountEntity(100L, ACCOUNT_DOCUMENT)).block());
    }

    void prepareUpdateEntityFailed() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                UPDATE_ENTITY_OPERATION_MOCK,
                new RuntimeException("error")
        );
    }

    @Test
    @BeforeTest(method = "prepareUpdateEntityFailed")
    void updateEntityFailed() {
        final RuntimeException exception =
                assertThrows(RuntimeException.class, () ->
                        dataRepository.updateEntity(new AccountEntity(100L, ACCOUNT_DOCUMENT)).block());
        assertEquals("error", exception.getMessage());
    }

    void prepareUpdateByRoleSuccess() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                UPDATE_BY_ROLE_OPERATION_MOCK,
                updateResult
        );
    }

    @Test
    @BeforeTest(method = "prepareUpdateByRoleSuccess")
    void updateByRoleSuccess() {
        assertSame(updateResult, dataRepository.updateByRole(BigDecimal.ZERO, CEO).block());
    }

    void prepareUpdateByRoleFailed() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                UPDATE_BY_ROLE_OPERATION_MOCK,
                new RuntimeException("error")
        );
    }

    @Test
    @BeforeTest(method = "prepareUpdateByRoleFailed")
    void updateByRoleFailed() {
        final RuntimeException exception =
                assertThrows(RuntimeException.class, () ->
                        dataRepository.updateByRole(BigDecimal.ZERO, CEO).block());
        assertEquals("error", exception.getMessage());
    }
}