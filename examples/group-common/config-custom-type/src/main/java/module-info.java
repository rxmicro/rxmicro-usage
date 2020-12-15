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

module examples.config.custom.type {
    requires rxmicro.config;

    exports io.rxmicro.examples.config.custom.type;

    exports io.rxmicro.examples.config.custom.type.config to
            rxmicro.runtime,
            rxmicro.config;

    // tag::content[]
    exports io.rxmicro.examples.config.custom.type._class to
            rxmicro.common;
    exports io.rxmicro.examples.config.custom.type._interface to
            rxmicro.common;
    exports io.rxmicro.examples.config.custom.type._annotation to
            rxmicro.common;
    // end::content[]
}