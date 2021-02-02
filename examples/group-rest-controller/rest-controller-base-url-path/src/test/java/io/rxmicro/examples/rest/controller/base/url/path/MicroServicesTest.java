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

package io.rxmicro.examples.rest.controller.base.url.path;

import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.ClientHttpResponse;
import io.rxmicro.test.SystemOut;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

// tag::content[]
@RxMicroRestBasedMicroServiceTest({
        MicroService1.class, MicroService2.class, MicroService3.class,
        MicroService4.class, MicroService5.class, MicroService6.class
})
final class MicroServicesTest {

    private BlockingHttpClient blockingHttpClient;

    private SystemOut systemOut;

    static Stream<Arguments> pathProvider() {
        return Stream.of(
                arguments("/patch", MicroService1.class),
                arguments("/base-url-path/patch", MicroService2.class),
                arguments("/base-url-path/v1/patch", MicroService3.class),
                arguments("/v1/base-url-path/patch", MicroService4.class),
                arguments("/before-base-url-path/v1/after-base-url-path/patch", MicroService5.class),
                arguments("/before/base/url/path/v1/after/base/url/path/patch", MicroService6.class)
        );
    }

    @ParameterizedTest
    @MethodSource("pathProvider")
    void Should_route_HTTP_request_to_valid_handler(final String path,
                                                    final Class<?> microService) {
        final ClientHttpResponse response = blockingHttpClient.patch(path);

        assertEquals(200, response.getStatusCode());
        assertEquals(microService.getSimpleName(), systemOut.asString());
    }
}
// end::content[]