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

package io.rxmicro.examples.testing.component.alternatives.simplest;

import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.RxMicroComponentTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
// <1>
@RxMicroComponentTest(ParentComponent.class)
final class ParentComponent1Test {

    // <3>
    @Alternative
    private final ChildComponent childComponent = () -> "test"; // <4>

    private ParentComponent parentComponent; // <2>

    @Test
    void Should_use_alternative() {
        assertEquals("test", parentComponent.getEnvironment()); // <5>
    }
}
// end::content[]