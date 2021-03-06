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

package io.rxmicro.examples.rest.client.headers.model;

import io.rxmicro.rest.Header;
import io.rxmicro.rest.HeaderMappingStrategy;

// tag::content[]
// <1>
@HeaderMappingStrategy
public final class Request {

    // <2>
    @Header
    final String endpointVersion;

    // <3>
    @Header("UseProxy")
    final Boolean useProxy;

    public Request(final String endpointVersion, final Boolean useProxy) {
        this.endpointVersion = endpointVersion;
        this.useProxy = useProxy;
    }
}
// end::content[]
