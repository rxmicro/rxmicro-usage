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

package io.rxmicro.examples.data.mongo.insert;

import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.examples.data.mongo.insert.model.Account;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import io.rxmicro.test.mockito.mongo.InsertOperationMock;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Map;

import static io.rxmicro.examples.data.mongo.insert.DataRepository.COLLECTION_NAME;
import static io.rxmicro.test.mockito.mongo.MongoMockFactory.prepareMongoOperationMocks;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@InitMocks
@RxMicroComponentTest(DataRepository.class)
final class DataRepository_UnitTest {

    private static final Account ACCOUNT = new Account(
            100L,
            "russ.hanneman@russfest.net",
            "Russ",
            "Hanneman",
            new BigDecimal("1000000000.00")
    );

    private static final InsertOperationMock INSERT_OPERATION_MOCK = new InsertOperationMock.Builder()
            .setDocument(new Document(Map.of(
                    "_id", ACCOUNT.getId(),
                    "email", ACCOUNT.getEmail(),
                    "firstName", ACCOUNT.getFirstName(),
                    "lastName", ACCOUNT.getLastName(),
                    "balance", ACCOUNT.getBalance()
            )))
            .build();

    private DataRepository dataRepository;

    @Mock
    @Alternative
    private MongoDatabase mongoDatabase;

    void prepareInsertSuccess() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                INSERT_OPERATION_MOCK
        );
    }

    @Test
    @BeforeTest(method = "prepareInsertSuccess")
    void insertSuccess() {
        assertDoesNotThrow(() -> dataRepository.insert(ACCOUNT).block());
    }

    void prepareInsertFailed() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                INSERT_OPERATION_MOCK,
                new RuntimeException("error")
        );
    }

    @Test
    @BeforeTest(method = "prepareInsertFailed")
    void insertFailed() {
        final RuntimeException exception =
                assertThrows(RuntimeException.class, () -> dataRepository.insert(ACCOUNT).block());
        assertEquals("error", exception.getMessage());
    }
}