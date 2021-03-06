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

package io.rxmicro.examples.testing.microservice.with.config;

import io.rxmicro.rest.server.HttpServerConfig;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.SystemOut;
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RxMicroRestBasedMicroServiceTest(MicroService.class)
final class MicroServiceTest {

    // tag::WithConfig[]
    @WithConfig
    private static final HttpServerConfig SERVER_CONFIG =
            new HttpServerConfig()
                    .setPort(56789);
    // end::WithConfig[]

    private BlockingHttpClient blockingHttpClient;

    private SystemOut systemOut;

    @Test
    void Should_use_custom_http_server_config() {
        blockingHttpClient.get("/");

        assertEquals("56789", systemOut.asString());
    }
}