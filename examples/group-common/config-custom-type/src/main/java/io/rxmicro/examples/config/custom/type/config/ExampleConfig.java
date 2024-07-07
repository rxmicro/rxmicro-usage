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

package io.rxmicro.examples.config.custom.type.config;

import io.rxmicro.config.Config;
import io.rxmicro.examples.config.custom.type.CustomType;

// tag::content[]
public final class ExampleConfig extends Config {

    private CustomType type = () -> "DEFAULT_CONSTANT";

    public ExampleConfig(final String namespace) {
        super(namespace);
    }

    public CustomType getType() {
        return type;
    }

    public void setType(final CustomType type) {
        this.type = type;
    }
}
// end::content[]

