/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.examples.code.fragments.config.java_config;

import io.rxmicro.config.Configs;
import io.rxmicro.examples.code.fragments.MicroService;
import io.rxmicro.rest.server.HttpServerConfig;

import static io.rxmicro.rest.server.RxMicro.startRestServer;

public final class Main {

    // tag::content[]
    public static void main(final String[] args) {
        new Configs.Builder()
                .withConfigs(new HttpServerConfig()
                        .setPort(9090)) // <1>
                .build();
        startRestServer(MicroService.class);
    }
    // end::content[]
}
