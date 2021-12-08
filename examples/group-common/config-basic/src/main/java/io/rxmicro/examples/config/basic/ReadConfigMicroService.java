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

package io.rxmicro.examples.config.basic;

import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.server.HttpServerConfig;

import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.rest.server.RxMicroRestServer.startRestServer;

// tag::content[]
public final class ReadConfigMicroService {

    @GET("/")
    void readHttpServerPort() {
        final HttpServerConfig config = getConfig(HttpServerConfig.class); // <1>
        System.out.println("HTTP server port: " + config.getPort());
    }

    public static void main(final String[] args) {
        startRestServer(ReadConfigMicroService.class);
    }
}
// end::content[]
