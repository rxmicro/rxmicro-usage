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

package io.rxmicro.examples.testing.component.order;

import io.rxmicro.test.junit.BeforeThisTest;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.List;

import static io.rxmicro.common.util.Reflections.invokeMethod;

public final class RxMicroComponentEmulationTestExtension
        implements BeforeAllCallback, BeforeEachCallback, BeforeTestExecutionCallback, AfterEachCallback {

    @Override
    public void beforeAll(final ExtensionContext context) {
        System.out.println("RX-MICRO: Test class validated.");
    }

    @Override
    public void beforeEach(final ExtensionContext context) {
        System.out.println("RX-MICRO: Alternatives of the RxMicro components registered in the RxMicro runtime containers.");
        System.out.println("RX-MICRO: SystemOut instance created and injected to the instance of the test class.");
    }

    @Override
    public void beforeTestExecution(final ExtensionContext context) {
        final BeforeThisTest beforeTest = context.getRequiredTestMethod().getAnnotation(BeforeThisTest.class);
        if (beforeTest != null) {
            invokeMethod(List.of(context.getRequiredTestInstance()), beforeTest.method());
        }
        System.out.println("RX-MICRO: Tested component instance created, if it is not created by user.");
        System.out.println("RX-MICRO: Alternatives of the user components injected to the tested component instance.");
    }

    @Override
    public void afterEach(final ExtensionContext context) {
        System.out.println("RX-MICRO: All registered alternatives removed from the RxMicro runtime containers.");
        System.out.println("RX-MICRO: System.out reset.");
    }
}
