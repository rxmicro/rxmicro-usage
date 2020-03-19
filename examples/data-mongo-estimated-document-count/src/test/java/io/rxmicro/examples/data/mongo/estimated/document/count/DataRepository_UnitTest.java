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

package io.rxmicro.examples.data.mongo.estimated.document.count;

import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static io.rxmicro.examples.data.mongo.estimated.document.count.DataRepository.COLLECTION_NAME;
import static io.rxmicro.test.mockito.mongo.EstimatedDocumentCountMock.ESTIMATED_DOCUMENT_COUNT_MOCK;
import static io.rxmicro.test.mockito.mongo.MongoMockFactory.prepareMongoOperationMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@InitMocks
@RxMicroComponentTest(DataRepository.class)
final class DataRepository_UnitTest {

    private DataRepository dataRepository;

    @Mock
    @Alternative
    private MongoDatabase mongoDatabase;

    void prepareEstimatedDocumentCountSuccess() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                ESTIMATED_DOCUMENT_COUNT_MOCK,
                100
        );
    }

    @Test
    @BeforeTest(method = "prepareEstimatedDocumentCountSuccess")
    void estimatedDocumentCountSuccess() {
        assertEquals(100, dataRepository.estimatedDocumentCount().block());
    }

    void prepareEstimatedDocumentCountFailed() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                ESTIMATED_DOCUMENT_COUNT_MOCK,
                new RuntimeException("error")
        );
    }

    @Test
    @BeforeTest(method = "prepareEstimatedDocumentCountFailed")
    void estimatedDocumentCountFailed() {
        final RuntimeException exception =
                assertThrows(RuntimeException.class, () -> dataRepository.estimatedDocumentCount().block());
        assertEquals("error", exception.getMessage());
    }
}