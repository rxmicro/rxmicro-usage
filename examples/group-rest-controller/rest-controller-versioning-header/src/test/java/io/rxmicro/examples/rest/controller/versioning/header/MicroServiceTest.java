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

package io.rxmicro.examples.rest.controller.versioning.header;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.SystemOut;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.rxmicro.http.HttpStandardHeaderNames.API_VERSION;
import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
@RxMicroRestBasedMicroServiceTest({OldMicroService.class, NewMicroService.class}) // <1>
final class MicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    private SystemOut systemOut;

    @ParameterizedTest
    @CsvSource({
            "v1,    old",
            "v2,    new"
    })
    void Should_route_to_valid_request_handler(final String versionHeaderValue,
                                               final String expectedOut) {
        blockingHttpClient.patch(
                "/patch",    // <2>
                HttpHeaders.of(
                        API_VERSION, // <3>
                        versionHeaderValue
                )
        );

        assertEquals(expectedOut, systemOut.asString());
    }
}
// end::content[]
