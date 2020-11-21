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

package io.rxmicro.examples.testing.component.alternatives.mocks;

import io.rxmicro.test.SystemOut;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// tag::content[]
@InitMocks
@RxMicroComponentTest(BusinessComponent.class)
final class BusinessComponent2Test {

    private BusinessComponent businessComponent;

    @Mock
    // @Alternative
    private BusinessComponent.BusinessChildComponent childComponent;

    private SystemOut systemOut;

    @BeforeEach
    void beforeEach() {
        businessComponent = new BusinessComponent(childComponent);
    }

    @Test
    void Should_print_mock_value() {
        when(childComponent.getEnvironmentName()).thenReturn("mock");
        businessComponent.execute();
        assertEquals("mock", systemOut.asString());
    }
}
// end::content[]
