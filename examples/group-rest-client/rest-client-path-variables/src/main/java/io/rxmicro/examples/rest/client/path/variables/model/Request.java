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

package io.rxmicro.examples.rest.client.path.variables.model;

import io.rxmicro.rest.PathVariable;

// tag::content[]
public final class Request {

    // <1>
    @PathVariable
    final String category;

    // <2>
    @PathVariable("class")
    final String type;

    @PathVariable
    final String subType;

    public Request(final String category, final String type, final String subType) {
        this.category = category;
        this.type = type;
        this.subType = subType;
    }
}
// end::content[]
