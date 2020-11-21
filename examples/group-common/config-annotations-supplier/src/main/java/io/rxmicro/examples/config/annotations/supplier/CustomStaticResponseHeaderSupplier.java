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

package io.rxmicro.examples.config.annotations.supplier;

import io.rxmicro.rest.server.PredefinedStaticResponseHeader;
import io.rxmicro.rest.server.StaticResponseHeader;

import java.util.Set;
import java.util.function.Supplier;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedSet;

public final class CustomStaticResponseHeaderSupplier implements Supplier<Set<StaticResponseHeader>> {

    @Override
    public Set<StaticResponseHeader> get() {
        return unmodifiableOrderedSet(
                PredefinedStaticResponseHeader.SERVER,
                new StaticResponseHeader() {

                    @Override
                    public String getName() {
                        return "Custom-Static-Header";
                    }

                    @Override
                    public String getValue() {
                        return System.getProperty("Custom-Static-Header-Value", "null");
                    }
                }
        );
    }
}
