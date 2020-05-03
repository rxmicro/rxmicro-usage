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

package io.rxmicro.examples.rest.controller.notfound;

import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.api.Test;

import static io.rxmicro.test.json.JsonFactory.jsonErrorObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RxMicroRestBasedMicroServiceTest(CustomizeNotFoundMicroService.class)
final class CustomizeNotFoundMicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    @Test
    void Should_return_custom_success_status() {
        final ClientHttpResponse response = blockingHttpClient.get("/?found=true");

        assertEquals(200, response.getStatusCode());
    }

    // tag::content[]
    @Test
    void Should_return_custom_not_found_message() {
        final ClientHttpResponse response = blockingHttpClient.get("/?found=false");

        assertEquals(jsonErrorObject("Custom not found message"), response.getBody()); // <1>
        assertEquals(404, response.getStatusCode()); // <2>
    }
    // end::content[]
}

