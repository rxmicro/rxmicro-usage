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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.rxmicro.test.json.JsonFactory.jsonErrorObject;
import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
@RxMicroRestBasedMicroServiceTest(NotFoundMicroService.class)
final class NotFoundMicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    @ParameterizedTest
    @ValueSource(strings = {"/get1", "/get2", "/get3"})
    void Should_return_found_response(final String urlPath) {
        final ClientHttpResponse response = blockingHttpClient.get(urlPath + "?found=true");

        assertEquals(jsonObject("message", "Hello World!"), response.getBody()); // <1>
        assertEquals(200, response.getStatusCode()); // <1>
    }

    @ParameterizedTest
    @ValueSource(strings = {"/get1", "/get2", "/get3"})
    void Should_return_not_found_response(final String urlPath) {
        final ClientHttpResponse response = blockingHttpClient.get(urlPath + "?found=false");

        assertEquals(jsonErrorObject("Not Found"), response.getBody()); // <2>
        assertEquals(404, response.getStatusCode()); // <2>
    }
}
// end::content[]
