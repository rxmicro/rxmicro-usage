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

import io.rxmicro.config.DefaultConfigValue;
import io.rxmicro.rest.server.HttpServerConfig;

// tag::content[]
@DefaultConfigValue(
        configClass = HttpServerConfig.class,   // <1>
        name = "port",                          // <2>
        value = "9090"
)
@DefaultConfigValue(
        name = "http-server.host",  // <3>
        value = "localhost"
)
module examples.config.annotations { // <4>
    requires rxmicro.rest.server.netty;
    requires rxmicro.rest.server.exchange.json;
}
// end::content[]
