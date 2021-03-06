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

package io.rxmicro.examples.rest.controller.redirect;

import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.BlockingHttpClientSettings;
import io.rxmicro.test.SystemOut;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.api.Test;

import static io.rxmicro.common.model.Option.ENABLED;
import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
@RxMicroRestBasedMicroServiceTest(MicroService.class)
final class MicroServiceTest {

    // <1>
    @BlockingHttpClientSettings(followRedirects = ENABLED)
    private BlockingHttpClient blockingHttpClient;

    private SystemOut systemOut;

    @Test
    void Should_redirect() {
        blockingHttpClient.put(
                "/old-url",
                jsonObject("parameter", "test")
        ); // <2>

        assertEquals("test", systemOut.asString()); // <3>
    }
}
// end::content[]
