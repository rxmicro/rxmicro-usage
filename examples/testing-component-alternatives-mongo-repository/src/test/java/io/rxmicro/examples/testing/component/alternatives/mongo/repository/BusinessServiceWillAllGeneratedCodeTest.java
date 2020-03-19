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

package io.rxmicro.examples.testing.component.alternatives.mongo.repository;

import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.examples.testing.component.alternatives.mongo.repository.model.Entity;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import io.rxmicro.test.mockito.mongo.FindOperationMock;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static io.rxmicro.test.mockito.mongo.MongoMockFactory.prepareMongoOperationMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// tag::content[]
@InitMocks
@RxMicroComponentTest(BusinessService.class)
final class BusinessServiceWillAllGeneratedCodeTest {

    private static final FindOperationMock FIND_OPERATION_MOCK =
            new FindOperationMock.Builder()
                    .setAnyQuery()              // <1>
                    //.setQuery("{_id: 1}")
                    .build();

    private BusinessService businessService;

    @Mock
    @Alternative
    private MongoDatabase mongoDatabase;

    // <4>
    void prepareOneEntityFound() {
        prepareMongoOperationMocks(
                mongoDatabase,
                "collection",
                FIND_OPERATION_MOCK,            // <5>
                new Document("data", "data")    // <6>
        );
    }

    @Test
    @BeforeTest(method = "prepareOneEntityFound")
    void Should_return_Entity_data() {
        final Optional<Entity> result = businessService.findById(1).join();
        assertTrue(result.isPresent());
        assertEquals("data", result.get().getData());
    }

    // <10>
    void prepareNoEntityFound() {
        prepareMongoOperationMocks(
                mongoDatabase,
                "collection",
                FIND_OPERATION_MOCK            // <11>
        );
    }

    @Test
    @BeforeTest(method = "prepareNoEntityFound")
    void Should_return_Not_Found_error() {
        final Optional<Entity> result = businessService.findById(1).join();
        assertEquals(Optional.empty(), result);
    }
}
// end::content[]
