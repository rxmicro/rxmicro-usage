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

package io.rxmicro.examples.processor.proxy.model;

import io.rxmicro.rest.Header;
import io.rxmicro.rest.HeaderMappingStrategy;
import io.rxmicro.rest.ParameterMappingStrategy;

@HeaderMappingStrategy
@ParameterMappingStrategy
public final class Response {

    @Header
    String header;

    String param;

    public Response() {
    }

    public Response(final String header, final String param) {
        this.header = header;
        this.param = param;
    }

    public String getHeader() {
        return header;
    }

    public String getParam() {
        return param;
    }
}

