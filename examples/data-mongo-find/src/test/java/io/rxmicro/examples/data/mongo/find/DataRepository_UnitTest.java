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

import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.data.Pageable;
import io.rxmicro.examples.data.mongo.find.model.Account;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import io.rxmicro.test.mockito.mongo.FindOperationMock;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.rxmicro.examples.data.mongo.find.DataRepository.COLLECTION_NAME;
import static io.rxmicro.examples.data.mongo.find.model.Role.CEO;
import static io.rxmicro.test.mockito.mongo.MongoMockFactory.prepareMongoOperationMocks;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@InitMocks
@RxMicroComponentTest(DataRepository.class)
final class DataRepository_UnitTest {

    private static final FindOperationMock FIND_BY_ID_OPERATION_MOCK = new FindOperationMock.Builder()
            .setQuery(new Document("_id", 1L))
            .build();

    private static final FindOperationMock FIND_BY_ROLE_OPERATION_MOCK = new FindOperationMock.Builder()
            .setQuery(new Document("role", CEO))
            .setSort("{role: 1, balance: 1}")
            .setLimit(10)
            .setSkip(5)
            .build();

    private static final Document RICHARD_HENDRICKS_DOCUMENT = new Document(Map.of(
            "_id", 1L,
            "email", "richard.hendricks@piedpiper.com",
            "firstName", "Richard",
            "lastName", "Hendricks",
            "balance", new BigDecimal("70000.00"),
            "role", "CEO"
    ));

    private static final Account RICHARD_HENDRICKS_MODEL = new Account(
            1L,
            "richard.hendricks@piedpiper.com",
            "Richard",
            "Hendricks",
            new BigDecimal("70000.00"),
            CEO
    );

    private DataRepository dataRepository;

    @Mock
    @Alternative
    private MongoDatabase mongoDatabase;

    void prepareFindByIdSuccess() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                FIND_BY_ID_OPERATION_MOCK,
                RICHARD_HENDRICKS_DOCUMENT
        );
    }

    @Test
    @BeforeTest(method = "prepareFindByIdSuccess")
    void findByIdSuccess() {
        final Account actual =
                requireNonNull(dataRepository.findById(1L).block());
        assertEquals(RICHARD_HENDRICKS_MODEL, actual);
    }

    void prepareFindByIdFailed() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                FIND_BY_ID_OPERATION_MOCK,
                new RuntimeException("error")
        );
    }

    @Test
    @BeforeTest(method = "prepareFindByIdFailed")
    void findByIdFailed() {
        final RuntimeException exception =
                assertThrows(RuntimeException.class, () -> dataRepository.findById(1L).block());
        assertEquals("error", exception.getMessage());
    }

    void prepareFindByRoleSuccess() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                FIND_BY_ROLE_OPERATION_MOCK,
                RICHARD_HENDRICKS_DOCUMENT,
                RICHARD_HENDRICKS_DOCUMENT
        );
    }

    @Test
    @BeforeTest(method = "prepareFindByRoleSuccess")
    void findByRoleSuccess() {
        final List<Account> actual =
                requireNonNull(dataRepository.findByRole(CEO, new Pageable(5, 10)).collectList().block());

        assertEquals(2, actual.size());
        for (final Account account : actual) {
            assertEquals(RICHARD_HENDRICKS_MODEL, account);
        }
    }

    void prepareFindByRoleFailed() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                FIND_BY_ROLE_OPERATION_MOCK,
                new RuntimeException("error")
        );
    }

    @Test
    @BeforeTest(method = "prepareFindByRoleFailed")
    void findByRoleFailed() {
        final RuntimeException exception =
                assertThrows(RuntimeException.class, () ->
                        dataRepository.findByRole(CEO, new Pageable(5, 10)).collectList().block());
        assertEquals("error", exception.getMessage());
    }
}