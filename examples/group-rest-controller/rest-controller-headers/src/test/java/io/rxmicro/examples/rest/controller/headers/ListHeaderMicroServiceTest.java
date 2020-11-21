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

import io.rxmicro.examples.rest.controller.headers.model.Status;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.SystemOut;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static io.rxmicro.examples.rest.controller.headers.model.Status.approved;
import static io.rxmicro.examples.rest.controller.headers.model.Status.created;
import static io.rxmicro.examples.rest.controller.headers.model.Status.rejected;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

// tag::content[]
@RxMicroRestBasedMicroServiceTest(ListHeaderMicroService.class)
final class ListHeaderMicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    private SystemOut systemOut;

    static Stream<Arguments> supportedListProvider() {
        return Stream.of(
                arguments("created|approved|rejected"),                         // <1>
                arguments(new Status[]{created, approved, rejected}, 0),        // <2>
                arguments(new String[]{"created", "approved", "rejected"}, 0),  // <3>
                arguments(List.of(created, approved, rejected)),                // <4>
                arguments(List.of("created", "approved", "rejected")),          // <5>
                arguments(HttpHeaders.of(                                       // <6>
                        "Supported-Statuses", "created",
                        "Supported-Statuses", "approved",
                        "Supported-Statuses", "rejected"
                )),
                arguments(HttpHeaders.of(                                       // <6>
                        "Supported-Statuses", "created|approved",
                        "Supported-Statuses", "rejected"
                ))
        );
    }

    @ParameterizedTest
    @MethodSource("supportedListProvider")
    void Should_support_list_headers(final Object headerValue) {
        blockingHttpClient.get(
                "/",
                headerValue instanceof HttpHeaders ?
                        (HttpHeaders) headerValue :
                        HttpHeaders.of("Supported-Statuses", headerValue) // <7>
        );

        assertEquals(
                "[created, approved, rejected]", // <8>
                systemOut.asString()
        );
    }
}
// end::content[]
