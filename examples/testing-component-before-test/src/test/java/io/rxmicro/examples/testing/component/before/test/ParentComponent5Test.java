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
import io.rxmicro.test.junit.BeforeIterationMethodSource;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// tag::content[]
@InitMocks
@RxMicroComponentTest(ParentComponent.class)
final class ParentComponent5Test {

    private ParentComponent parentComponent;

    @Mock
    @Alternative
    private ChildComponent childComponent;

    void beforeEachPreparer1() {
        when(childComponent.getValue()).thenReturn(new BigDecimal("23").toString());
    }

    void beforeEachPreparer2() {
        when(childComponent.getValue()).thenReturn("23");
    }

    @ParameterizedTest
    // <1>
    @BeforeIterationMethodSource(methods = {
            "beforeEachPreparer1",
            "beforeEachPreparer2"
    })
    void Should_use_alternative2(final String method) { // <2>
        assertEquals("23", parentComponent.getEnvironment());
    }
}
// end::content[]
