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

package io.rxmicro.examples.testing.microservice.alternatives.mongo.repository;

import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.junit.BeforeTest;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import io.rxmicro.test.mockito.mongo.FindOperationMock;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static io.rxmicro.test.mockito.mongo.MongoMockFactory.prepareMongoOperationMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
@InitMocks
@RxMicroRestBasedMicroServiceTest(MicroService.class)
final class MicroServiceWithAllGeneratedCodeTest {

    private static final FindOperationMock FIND_OPERATION_MOCK =
            new FindOperationMock.Builder()
                    .setAnyQuery()
                    //.setQuery("{_id: 1}")
                    .build();

    private BlockingHttpClient blockingHttpClient;

    @Mock
    @Alternative
    private MongoDatabase mongoDatabase;

    void prepareOneEntityFound() {
        prepareMongoOperationMocks(
                mongoDatabase,
                "collection",
                FIND_OPERATION_MOCK,
                new Document("data", "data")
        );
    }

    @Test
    @BeforeTest(method = "prepareOneEntityFound")
    void Should_return_Entity_data() {
        final ClientHttpResponse response = blockingHttpClient.get("/?id=1");

        assertEquals(jsonObject("message", "data"), response.getBody());
        assertEquals(200, response.getStatusCode());
    }

    void prepareNoEntityFound() {
        prepareMongoOperationMocks(
                mongoDatabase,
                "collection",
                FIND_OPERATION_MOCK
        );
    }

    @Test
    @BeforeTest(method = "prepareNoEntityFound")
    void Should_return_Not_Found_error() {
        final ClientHttpResponse response = blockingHttpClient.get("/?id=1");

        assertEquals(jsonObject("message", "Not Found"), response.getBody());
        assertEquals(404, response.getStatusCode());
    }
}
// end::content[]
