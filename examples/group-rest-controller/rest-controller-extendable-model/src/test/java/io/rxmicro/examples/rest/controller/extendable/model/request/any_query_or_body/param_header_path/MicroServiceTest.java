/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.examples.rest.controller.extendable.model.request.any_query_or_body.param_header_path;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.QueryParams;
import io.rxmicro.test.ClientHttpResponse;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.SystemOut;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@RxMicroRestBasedMicroServiceTest(MicroService.class)
final class MicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    private SystemOut systemOut;

    @Test
    void get_should_return_valid_response1() {
        final HttpHeaders headers = HttpHeaders.of(
                "parentHeader", "parentHeader"
        );
        final QueryParams queryParams = QueryParams.of(
                "childParameter", "childParameter"
        );
        final ClientHttpResponse response = blockingHttpClient.get("/1/grandVar", headers, queryParams);

        assertEquals(200, response.getStatusCode());
        assertTrue(response.isBodyEmpty(), () -> "Body not empty: " + response.getBody());

        assertEquals(
                "Child{" +
                        "grandVar='grandVar', " +
                        "parentHeader='parentHeader', " +
                        "childParameter='childParameter'" +
                        "}",
                systemOut.asString()
        );
    }

    @Test
    void put_should_return_valid_response1() {
        final HttpHeaders headers = HttpHeaders.of(
                "parentHeader", "parentHeader"
        );
        final Object body = jsonObject(
                "childParameter", "childParameter"
        );
        final ClientHttpResponse response = blockingHttpClient.put("/1/grandVar", headers, body);

        assertEquals(200, response.getStatusCode());
        assertTrue(response.isBodyEmpty(), () -> "Body not empty: " + response.getBody());

        assertEquals(
                "Child{" +
                        "grandVar='grandVar', " +
                        "parentHeader='parentHeader', " +
                        "childParameter='childParameter'" +
                        "}",
                systemOut.asString()
        );
    }

    @Test
    void get_should_return_valid_response2() {
        final HttpHeaders headers = HttpHeaders.of(
                "parentHeader", "parentHeader"
        );
        final ClientHttpResponse response = blockingHttpClient.get("/2/grandVar", headers);

        assertEquals(200, response.getStatusCode());
        assertTrue(response.isBodyEmpty(), () -> "Body not empty: " + response.getBody());

        assertEquals(
                "Parent{" +
                        "grandVar='grandVar', " +
                        "parentHeader='parentHeader'" +
                        "}",
                systemOut.asString()
        );
    }

    @Test
    void put_should_return_valid_response2() {
        final HttpHeaders headers = HttpHeaders.of(
                "parentHeader", "parentHeader"
        );
        final ClientHttpResponse response = blockingHttpClient.put("/2/grandVar", headers);

        assertEquals(200, response.getStatusCode());
        assertTrue(response.isBodyEmpty(), () -> "Body not empty: " + response.getBody());

        assertEquals(
                "Parent{" +
                        "grandVar='grandVar', " +
                        "parentHeader='parentHeader'" +
                        "}",
                systemOut.asString()
        );
    }
}