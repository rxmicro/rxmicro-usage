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

package io.rxmicro.examples.testing.component.alternatives.basic;

import io.rxmicro.test.SystemOut;
import io.rxmicro.test.junit.RxMicroComponentTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
// <1>
@RxMicroComponentTest(BusinessComponent.class)
final class BusinessComponent2Test {

    // @Alternative // <2>
    private final BusinessComponent.BusinessChildComponent childComponent =
            new BusinessComponent.BusinessChildComponent() {
                @Override
                public String getEnvironmentName() {
                    return "test";
                }
            };

    private BusinessComponent businessComponent;

    private SystemOut systemOut;

    // <3>
    @BeforeEach
    void beforeEach() {
        businessComponent = new BusinessComponent(childComponent); // <4>
    }

    @Test
    void Should_print_mock_value() {
        businessComponent.execute();

        assertEquals("test", systemOut.asString()); // <5>
    }
}
// end::content[]
