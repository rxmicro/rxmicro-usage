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

package io.rxmicro.examples.json;

import java.math.BigDecimal;
import java.util.List;

// tag::content[]
public final class Response {

    final Child child = new Child(); // <1>

    final List<Integer> values = List.of(25, 50, 75, 100); // <2>

    final String string = "text"; // <3>

    final Integer integer = 10; // <4>

    final BigDecimal decimal = new BigDecimal("0.1234"); // <5>

    final Boolean logical = true; // <6>
}
// end::content[]
