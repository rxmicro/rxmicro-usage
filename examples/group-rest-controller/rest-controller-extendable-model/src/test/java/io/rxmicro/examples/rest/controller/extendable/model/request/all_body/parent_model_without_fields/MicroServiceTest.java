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

package io.rxmicro.examples.rest.controller.extendable.model.request.all_body.parent_model_without_fields;

import io.rxmicro.http.HttpHeaders;
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
    void Should_return_valid_response1() {
        final HttpHeaders headers = HttpHeaders.of(
                "grandHeader", "grandHeader",
                "childHeader", "childHeader"
        );
        final Object body = jsonObject(
                "grandParameter", "grandParameter",
                "childParameter", "childParameter"
        );
        final ClientHttpResponse response = blockingHttpClient.put("/1/grandVar/childVar", headers, body);

        assertEquals(200, response.getStatusCode());
        assertTrue(response.isBodyEmpty(), () -> "Body not empty: " + response.getBody());

        assertEquals(
                "Child{" +
                        "httpVersion=HTTP/1.1, grandVar='grandVar', grandHeader='grandHeader', grandParameter='grandParameter', " +

                        "method='PUT', childVar='childVar', childHeader='childHeader', childParameter='childParameter'" +
                        "}",
                systemOut.asString()
        );
    }
}