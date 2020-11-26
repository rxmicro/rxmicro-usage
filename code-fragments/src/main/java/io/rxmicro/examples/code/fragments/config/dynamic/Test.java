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

package io.rxmicro.examples.code.fragments.config.dynamic;

import static io.rxmicro.config.Configs.getConfig;

public final class Test {

    // tag::content[]
    public static void main(final String[] args) {
        final DynamicAsMapConfig config = getConfig(DynamicAsMapConfig.class);

        System.out.println(config.getBigDecimal("bigDecimal"));
        System.out.println(config.getBigInteger("bigInteger"));
        System.out.println(config.getBoolean("boolean"));
        System.out.println(config.getInteger("integer"));
        System.out.println(config.getString("string"));
    }
    // end::content[]
}
