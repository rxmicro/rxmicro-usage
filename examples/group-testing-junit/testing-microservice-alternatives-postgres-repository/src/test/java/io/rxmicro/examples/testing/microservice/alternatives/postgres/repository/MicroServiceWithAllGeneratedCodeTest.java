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

package io.rxmicro.examples.testing.microservice.alternatives.postgres.repository;

import io.r2dbc.pool.ConnectionPool;
import io.rxmicro.test.ClientHttpResponse;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.junit.BeforeThisTest;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import io.rxmicro.test.mockito.r2dbc.SQLQueryWithParamsMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static io.rxmicro.test.mockito.r2dbc.SQLMockFactory.prepareSQLOperationMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
@InitMocks
@RxMicroRestBasedMicroServiceTest(MicroService.class)
final class MicroServiceWithAllGeneratedCodeTest {

    private static final SQLQueryWithParamsMock SQL_PARAMS_MOCK =
            new SQLQueryWithParamsMock.Builder()
                    .setAnySql()
                    //.setSql("SELECT data FROM entity WHERE id = $1")
                    //.setBindParams(1L)
                    .build();

    private BlockingHttpClient blockingHttpClient;

    @Mock
    @Alternative
    private ConnectionPool connectionPool;

    void prepareOneEntityFound() {
        prepareSQLOperationMocks(
                connectionPool,
                SQL_PARAMS_MOCK,
                "data"
        );
    }

    @Test
    @BeforeThisTest(method = "prepareOneEntityFound")
    void Should_return_Entity_data() {
        final ClientHttpResponse response = blockingHttpClient.get("/?id=1");

        assertEquals(jsonObject("message", "data"), response.getBody());
        assertEquals(200, response.getStatusCode());
    }

    void prepareNoEntityFound() {
        prepareSQLOperationMocks(
                connectionPool,
                SQL_PARAMS_MOCK,
                List.of()
        );
    }

    @Test
    @BeforeThisTest(method = "prepareNoEntityFound")
    void Should_return_Not_Found_error() {
        final ClientHttpResponse response = blockingHttpClient.get("/?id=1");

        assertEquals(jsonObject("message", "Not Found"), response.getBody());
        assertEquals(404, response.getStatusCode());
    }
}
// end::content[]
