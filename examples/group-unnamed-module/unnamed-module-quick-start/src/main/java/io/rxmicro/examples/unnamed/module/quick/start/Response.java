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
// tag::content[]
package io.rxmicro.examples.unnamed.module.quick.start;

import static java.util.Objects.requireNonNull;

@SuppressWarnings("SameParameterValue")
final class Response {

    final String message;

    Response(final String message) {
        this.message = requireNonNull(message);
    }
}
// end::content[]