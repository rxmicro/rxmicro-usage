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

package io.rxmicro.examples.rest.client.model.types.model.request.without_body;

import io.rxmicro.examples.rest.client.model.types.model.Status;
import io.rxmicro.rest.PathVariable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

public class PathVarOnlyRequest {

    @PathVariable("a")
    Boolean booleanPathVariable;

    @PathVariable("b")
    Byte bytePathVariable;

    @PathVariable("c")
    Short shortPathVariable;

    @PathVariable("d")
    Integer intPathVariable;

    @PathVariable("e")
    Long longPathVariable;

    @PathVariable("f")
    BigInteger bigIntegerPathVariable;

    @PathVariable("g")
    Float floatPathVariable;

    @PathVariable("h")
    Double doublePathVariable;

    @PathVariable("i")
    BigDecimal decimalPathVariable;

    @PathVariable("j")
    Character charPathVariable;

    @PathVariable("k")
    String stringPathVariable;

    @PathVariable("l")
    Instant instantPathVariable;

    @PathVariable("m")
    Status enumPathVariable;
}
