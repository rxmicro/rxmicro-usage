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

package io.rxmicro.examples.testing.component.before.test;

import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeThisTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// tag::content[]
@InitMocks
@RxMicroComponentTest(ParentComponent.class)
final class ParentComponent4Test {

    private ParentComponent parentComponent;

    @Mock
    @Alternative
    private ChildComponent childComponent;

    void beforeTest1() { // <1>
        when(childComponent.getValue()).thenReturn("mock1");
    }

    @Test
    // <2>
    @BeforeThisTest(method = "beforeTest1")
    void Should_use_alternative1() {
        assertEquals("mock1", parentComponent.getEnvironment());
    }

    void beforeTest2() {
        when(childComponent.getValue()).thenReturn("mock2");
    }

    @Test
    @BeforeThisTest(method = "beforeTest2")
    void Should_use_alternative2() {
        assertEquals("mock2", parentComponent.getEnvironment());
    }
}
// end::content[]
