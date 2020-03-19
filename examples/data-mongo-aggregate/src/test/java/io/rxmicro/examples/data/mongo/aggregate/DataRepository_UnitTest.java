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

package io.rxmicro.examples.data.mongo.aggregate;

import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.examples.data.mongo.aggregate.model.Report;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import io.rxmicro.test.mockito.mongo.AggregateOperationMock;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.rxmicro.examples.data.mongo.aggregate.DataRepository.COLLECTION_NAME;
import static io.rxmicro.examples.data.mongo.aggregate.model.Role.CEO;
import static io.rxmicro.test.mockito.mongo.MongoMockFactory.prepareMongoOperationMocks;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@InitMocks
@RxMicroComponentTest(DataRepository.class)
final class DataRepository_UnitTest {

    private static final AggregateOperationMock AGGREGATE_OPERATION_MOCK =
            new AggregateOperationMock.Builder()
                    .addPipeline("{ $group : { _id: '$role', total : { $sum: '$balance'}} }")
                    .addPipeline("{ $sort: {total: -1, _id: -1} }")
                    .build();

    private DataRepository dataRepository;

    @Mock
    @Alternative
    private MongoDatabase mongoDatabase;

    void prepareAggregateSuccess() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                AGGREGATE_OPERATION_MOCK,
                new Document(Map.of(
                        "_id", "CEO",
                        "total", new BigDecimal("70000.00")
                ))
        );
    }

    @Test
    @BeforeTest(method = "prepareAggregateSuccess")
    void aggregateSuccess() {
        final List<Report> reports = requireNonNull(dataRepository.aggregate().collectList().block());
        assertEquals(
                List.of(new Report(CEO, new BigDecimal("70000.00"))),
                reports
        );
    }

    void prepareAggregateFailed() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                AGGREGATE_OPERATION_MOCK,
                new RuntimeException("error")
        );
    }

    @Test
    @BeforeTest(method = "prepareAggregateFailed")
    void aggregateFailed() {
        final RuntimeException exception =
                assertThrows(RuntimeException.class, () -> dataRepository.aggregate().collectList().block());
        assertEquals("error", exception.getMessage());
    }
}