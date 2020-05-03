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

package io.rxmicro.examples.code.fragments.config.custom_order;

import io.rxmicro.config.Configs;
import io.rxmicro.examples.code.fragments.MicroService;

import static io.rxmicro.config.ConfigSource.RXMICRO_FILE_AT_THE_CURRENT_DIR;
import static io.rxmicro.config.ConfigSource.RXMICRO_FILE_AT_THE_HOME_DIR;
import static io.rxmicro.config.ConfigSource.SEPARATE_FILE_AT_THE_CURRENT_DIR;
import static io.rxmicro.config.ConfigSource.SEPARATE_FILE_AT_THE_HOME_DIR;
import static io.rxmicro.rest.server.RxMicro.startRestServer;

public final class UsePropertiesFilesOnlyExample {

    // tag::content[]
    public static void main(final String[] args) {
        new Configs.Builder()
                .withOrderedConfigSources(
                        RXMICRO_FILE_AT_THE_HOME_DIR,    // <1>
                        RXMICRO_FILE_AT_THE_CURRENT_DIR, // <2>
                        SEPARATE_FILE_AT_THE_HOME_DIR,   // <3>
                        SEPARATE_FILE_AT_THE_CURRENT_DIR // <4>
                )
                .build();
        startRestServer(MicroService.class);
    }
    // end::content[]
}
