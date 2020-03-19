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

package io.rxmicro.examples.data.mongo.count.documents;

import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import io.rxmicro.test.mockito.mongo.CountDocumentsOperationMock;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static io.rxmicro.examples.data.mongo.count.documents.DataRepository.COLLECTION_NAME;
import static io.rxmicro.examples.data.mongo.count.documents.model.Role.CEO;
import static io.rxmicro.test.mockito.mongo.MongoMockFactory.prepareMongoOperationMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@InitMocks
@RxMicroComponentTest(DataRepository.class)
final class DataRepository_UnitTest {

    private static final CountDocumentsOperationMock COUNT_ALL_DOCUMENTS_OPERATION_MOCK =
            new CountDocumentsOperationMock.Builder()
                    .build();

    private static final CountDocumentsOperationMock COUNT_FILTERED_DOCUMENTS_OPERATION_MOCK =
            new CountDocumentsOperationMock.Builder()
                    .setQuery(new Document("role", CEO))
                    .setLimit(100)
                    .setSkip(0)
                    .build();

    private DataRepository dataRepository;

    @Mock
    @Alternative
    private MongoDatabase mongoDatabase;

    private void prepareCountAllDocumentsSuccess() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                COUNT_ALL_DOCUMENTS_OPERATION_MOCK,
                100L);
    }

    @Test
    @BeforeTest(method = "prepareCountAllDocumentsSuccess")
    void countAllDocuments_should_return_count() {
        assertEquals(100, dataRepository.countDocuments().block());
    }

    private void prepareCountAllDocumentsFailed() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                COUNT_ALL_DOCUMENTS_OPERATION_MOCK,
                new RuntimeException("error"));
    }

    @Test
    @BeforeTest(method = "prepareCountAllDocumentsFailed")
    void countAllDocuments_should_return_error() {
        final RuntimeException exception =
                assertThrows(RuntimeException.class, () -> dataRepository.countDocuments().block());
        assertEquals("error", exception.getMessage());
    }

    private void prepareCountFilteredDocumentsSuccess() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                COUNT_FILTERED_DOCUMENTS_OPERATION_MOCK,
                100L);
    }

    @Test
    @BeforeTest(method = "prepareCountFilteredDocumentsSuccess")
    void countFilteredDocuments_should_return_count() {
        assertEquals(100, dataRepository.countDocuments(CEO).block());
    }

    private void prepareCountFilteredDocumentsFailed() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                COUNT_FILTERED_DOCUMENTS_OPERATION_MOCK,
                new RuntimeException("error"));
    }

    @Test
    @BeforeTest(method = "prepareCountFilteredDocumentsFailed")
    void countFilteredDocuments_should_return_error() {
        final RuntimeException exception =
                assertThrows(RuntimeException.class, () -> dataRepository.countDocuments(CEO).block());
        assertEquals("error", exception.getMessage());
    }

}