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

package io.rxmicro.examples.rest.controller.params;

import io.rxmicro.http.QueryParams;
import io.rxmicro.test.ClientHttpResponse;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
@RxMicroRestBasedMicroServiceTest(SimpleUsageMicroService.class)
final class SimpleUsageMicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    @ParameterizedTest
    @ValueSource(strings = {"/get1", "/get2"})
    void Should_process_HTTP_query_params(final String path) {
        final ClientHttpResponse response = blockingHttpClient.get(
                path, // <1>
                QueryParams.of(
                        "endpoint_version", "v1",   // <2>
                        "use-Proxy", true           // <3>
                )
        );

        assertHttpBody(response);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/post1", "/post2"})
    void Should_process_HTTP_body_params(final String path) {
        final ClientHttpResponse response = blockingHttpClient.post(
                path, // <1>
                jsonObject(
                        "endpoint_version", "v1",   // <2>
                        "use-Proxy", true           // <3>
                )
        );

        assertHttpBody(response);
    }

    private void assertHttpBody(final ClientHttpResponse response) {
        assertEquals(
                jsonObject(
                        "endpoint_version", "v1", // <4>
                        "use-Proxy", true         // <4>
                ),
                response.getBody()
        );
    }
}
// end::content[]
