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

package io.rxmicro.examples.rest.controller.model.field.mapping.strategy;

import io.rxmicro.rest.Header;
import io.rxmicro.rest.HeaderMappingStrategy;
import io.rxmicro.rest.ParameterMappingStrategy;
import io.rxmicro.rest.method.PUT;

import java.math.BigDecimal;
import java.time.Instant;

import static io.rxmicro.model.MappingStrategy.CAPITALIZE_WITH_UNDERSCORED;
import static io.rxmicro.model.MappingStrategy.LOWERCASE_WITH_HYPHEN;

@SuppressWarnings("EmptyMethod")
@ParameterMappingStrategy
@HeaderMappingStrategy
final class MappingStrategyMicroService {

    @PUT(value = "/consume1", httpBody = false)
    void consume1(final @Header BigDecimal supportedApiVersionCode,
                  final Instant maxSupportedDateTime) {
        // do something
    }

    @PUT(value = "/consume2", httpBody = false)
    @HeaderMappingStrategy(CAPITALIZE_WITH_UNDERSCORED)
    void consume2(final @Header BigDecimal supportedApiVersionCode,
                  final Instant maxSupportedDateTime) {
        // do something
    }

    @PUT(value = "/consume3", httpBody = false)
    @ParameterMappingStrategy(LOWERCASE_WITH_HYPHEN)
    void consume3(final @Header BigDecimal supportedApiVersionCode,
                  final Instant maxSupportedDateTime) {
        // do something
    }
}
