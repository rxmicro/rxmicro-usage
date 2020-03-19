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

package io.rxmicro.examples.rest.controller.headers;

import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RxMicroRestBasedMicroServiceTest(RepeatingHeadersMicroService.class)
final class RepeatingHeadersMicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    @Test
    void Should_support_multi_value_headers() {
        final ClientHttpResponse response = blockingHttpClient.get("/");

        // tag::content[]
        assertEquals(
                "created|approved|rejected", // <1>
                response.headers().getValue("Single-Header")
        );
        assertEquals(
                List.of("created", "approved", "rejected"), //<2>
                response.headers().getValues("Repeating-Header")
        );
        // end::content[]
    }
}
