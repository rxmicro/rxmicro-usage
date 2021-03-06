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

package io.rxmicro.examples.testing.microservice.basic;

import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.ClientHttpResponse;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.api.Test;

import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
// <1>
@RxMicroRestBasedMicroServiceTest(MicroService.class)
class MicroServiceTest {

    private BlockingHttpClient blockingHttpClient; // <2>

    @Test
    void Should_handle_GET_request() {
        final ClientHttpResponse response = blockingHttpClient.get("/");

        assertEquals(jsonObject("message", "Hello World!"), response.getBody()); // <3>
        assertEquals(200, response.getStatusCode());
    }
}
// end::content[]