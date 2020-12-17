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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
final class BusinessComponent1Test {

    private static final PrintStream REAL_SYS_OUT = System.out;

    private final ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();

    private final PrintStream systemOut = new PrintStream(bytesOut);

    private final BusinessComponent.BusinessChildComponent childComponent =
            new BusinessComponent.BusinessChildComponent() {
                @Override
                public String getEnvironmentName() {
                    return "test";
                }
            };

    private BusinessComponent businessComponent;

    @BeforeEach
    void beforeEach() {
        businessComponent = new BusinessComponent(childComponent);
        System.setOut(systemOut);
    }

    @Test
    void Should_print_mock_value() {
        businessComponent.execute();

        assertEquals("test", new String(bytesOut.toByteArray(), UTF_8).trim());
    }

    @AfterEach
    void afterEach() {
        System.setOut(REAL_SYS_OUT);
    }
}
// end::content[]
