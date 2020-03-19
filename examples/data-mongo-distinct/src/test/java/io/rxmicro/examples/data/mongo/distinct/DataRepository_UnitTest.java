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

package io.rxmicro.examples.data.mongo.distinct;

import com.mongodb.reactivestreams.client.MongoDatabase;
import io.rxmicro.examples.data.mongo.distinct.model.Role;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import io.rxmicro.test.mockito.mongo.DistinctOperationMock;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static io.rxmicro.examples.data.mongo.distinct.DataRepository.COLLECTION_NAME;
import static io.rxmicro.examples.data.mongo.distinct.model.Role.CEO;
import static io.rxmicro.examples.data.mongo.distinct.model.Role.Systems_Architect;
import static io.rxmicro.test.mockito.mongo.MongoMockFactory.prepareMongoOperationMocks;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@InitMocks
@RxMicroComponentTest(DataRepository.class)
final class DataRepository_UnitTest {

    public static final DistinctOperationMock<String> FIND_EMAIL_BY_ID_OPERATION_MOCK =
            new DistinctOperationMock.Builder<String>()
                    .setResultClass(String.class)
                    .setField("email")
                    .setQuery(new Document("_id", 1L))
                    .build();

    public static final DistinctOperationMock<Role> FIND_ALL_USED_ROLES_OPERATION_MOCK =
            new DistinctOperationMock.Builder<Role>()
                    .setResultClass(Role.class)
                    .setField("role")
                    .build();

    private DataRepository dataRepository;

    @Mock
    @Alternative
    private MongoDatabase mongoDatabase;

    void prepareGetEmailByIdSuccess() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                FIND_EMAIL_BY_ID_OPERATION_MOCK,
                "richard.hendricks@piedpiper.com"
        );
    }

    @Test
    @BeforeTest(method = "prepareGetEmailByIdSuccess")
    void getEmailByIdSuccess() {
        final String email = requireNonNull(dataRepository.getEmailById(1L).block());
        assertEquals("richard.hendricks@piedpiper.com", email);
    }

    void prepareGetEmailByIdFailed() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                FIND_EMAIL_BY_ID_OPERATION_MOCK,
                new RuntimeException("error")
        );
    }

    @Test
    @BeforeTest(method = "prepareGetEmailByIdFailed")
    void getEmailByIdFailed() {
        final RuntimeException exception =
                assertThrows(RuntimeException.class, () -> dataRepository.getEmailById(1L).block());
        assertEquals("error", exception.getMessage());
    }

    void prepareGetAllUsedRolesSuccess() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                FIND_ALL_USED_ROLES_OPERATION_MOCK,
                CEO,
                Systems_Architect
        );
    }

    @Test
    @BeforeTest(method = "prepareGetAllUsedRolesSuccess")
    void getAllUsedRolesSuccess() {
        final List<Role> roles = requireNonNull(dataRepository.getAllUsedRoles().collectList().block());
        assertEquals(
                List.of(
                        CEO,
                        Systems_Architect
                ),
                roles
        );
    }

    void prepareGetAllUsedRolesFailed() {
        prepareMongoOperationMocks(
                mongoDatabase,
                COLLECTION_NAME,
                FIND_ALL_USED_ROLES_OPERATION_MOCK,
                new RuntimeException("error")
        );
    }

    @Test
    @BeforeTest(method = "prepareGetAllUsedRolesFailed")
    void getAllUsedRolesFailed() {
        final RuntimeException exception =
                assertThrows(RuntimeException.class, () -> dataRepository.getAllUsedRoles().collectList().block());
        assertEquals("error", exception.getMessage());
    }
}