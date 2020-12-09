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

package io.rxmicro.examples.config.custom.type;

import io.rxmicro.test.SystemOut;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import org.junit.jupiter.api.Test;

import static io.rxmicro.common.util.Formats.format;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RxMicroIntegrationTest
final class LauncherTest {

    private SystemOut systemOut;

    @Test
    void Should_support_custom_type_injection(){
        Launcher.main(new String[0]);

        final String output = systemOut.asString();

        assertOutputsContainRequiredMessage(output, "Default constant: DEFAULT_CONSTANT");
        assertOutputsContainRequiredMessage(output, "Enum constant: ENUM_CONSTANT");
        assertOutputsContainRequiredMessage(output, "Class constant: CLASS_CONSTANT");
        assertOutputsContainRequiredMessage(output, "Interface constant: INTERFACE_CONSTANT");
        assertOutputsContainRequiredMessage(output, "Annotation constant: ANNOTATION_CONSTANT");
    }

    private void assertOutputsContainRequiredMessage(final String output,
                                                     final String message) {
        assertTrue(
                output.contains(message),
                () -> format(
                        "Console output does not contain required message: '?'! Full output is\n?",
                        message, output
                )
        );
    }
}