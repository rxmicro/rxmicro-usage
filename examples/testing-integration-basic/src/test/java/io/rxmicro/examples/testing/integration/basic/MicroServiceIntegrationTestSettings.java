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

package io.rxmicro.examples.testing.integration.basic;

import io.rxmicro.common.model.Option;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.BlockingHttpClientSettings;

import static io.rxmicro.http.ProtocolSchema.HTTPS;
import static io.rxmicro.rest.Version.Strategy.HEADER;
import static io.rxmicro.test.HttpServers.getRandomFreePort;


public final class MicroServiceIntegrationTestSettings {

    // tag::content[]
    private static final int SERVER_PORT = getRandomFreePort(); // <4>

    @BlockingHttpClientSettings(
            schema = HTTPS,                     // <1>
            host = "examples.rxmicro.io",       // <2>
            port = 9876,                        // <3>
            randomPortProvider = "SERVER_PORT", // <4>
            versionValue = "v1.1",              // <5>
            versionStrategy = HEADER,           // <6>
            requestTimeout = 15,                // <7>
            followRedirects = Option.ENABLED    // <8>
    )
    private BlockingHttpClient blockingHttpClient;
    // end::content[]
}

