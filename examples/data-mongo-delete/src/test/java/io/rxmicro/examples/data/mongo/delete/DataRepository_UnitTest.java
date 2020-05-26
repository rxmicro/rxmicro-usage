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

package io.rxmicro.examples.data.mongo.delete;

import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeThisTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import io.rxmicro.test.mockito.mongo.DeleteOperationMock;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static io.rxmicro.examples.data.mongo.delete.DataRepository.COLLECTION_NAME;
import static io.rxmicro.test.mockito.mongo.MongoMockFactory.prepareMongoOperationMocks;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@InitMocks
@RxMicroComponentTest(DataRepository.class)
final class DataRepository_UnitTest {

    private static final DeleteOperationMock DELETE_BY_ID_OPERATION_MOCK = new DeleteOperationMock.Builder()
            .setFilter(new Document("_id", 1L))
            .build();

    private DataRepository dataRepository;

    @Mock
    @Alternative
    private MongoDatabase mongoDatabase;

    void prepareDeleteByIdSuccess() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                DELETE_BY_ID_OPERATION_MOCK,
                1L
        );
    }

    @Test
    @BeforeThisTest(method = "prepareDeleteByIdSuccess")
    void deleteByIdSuccess() {
        assertTrue(requireNonNull(dataRepository.deleteById(1L).block()));
    }

    void prepareDeleteByIdFailed() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                DELETE_BY_ID_OPERATION_MOCK,
                new RuntimeException("error")
        );
    }

    @Test
    @BeforeThisTest(method = "prepareDeleteByIdFailed")
    void deleteByIdFailed() {
        final RuntimeException exception =
                assertThrows(RuntimeException.class, () -> dataRepository.deleteById(1L).block());
        assertEquals("error", exception.getMessage());
    }
}