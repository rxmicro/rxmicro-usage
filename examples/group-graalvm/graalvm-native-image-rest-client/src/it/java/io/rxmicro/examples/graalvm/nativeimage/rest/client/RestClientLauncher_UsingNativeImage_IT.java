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

package io.rxmicro.examples.graalvm.nativeimage.rest.client;

import io.rxmicro.test.SystemOut;
import io.rxmicro.test.TestedProcessBuilder;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.io.File;
import java.io.IOException;

import static io.rxmicro.common.util.Formats.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EnabledIfEnvironmentVariable(
        named = "GRAALVM_HOME",
        matches = ".*?",
        disabledReason = "This test requires a native image that is built only if Graalvm SDK exists."
)
// tag::content[]
@RxMicroIntegrationTest
final class RestClientLauncher_UsingNativeImage_IT {

    private SystemOut systemOut;

    @Test
    @EnabledIf("isRestJdkClientLauncherBuilt")
    void RestJdkClientLauncher_should_return_Hello_World() throws IOException, InterruptedException {
        test("./RestJdkClientLauncher");
    }

    @Test
    @EnabledIf("isRestNettyClientLauncherBuilt")
    void RestNettyClientLauncher_should_return_Hello_World() throws IOException, InterruptedException {
        test("./RestNettyClientLauncher");
    }

    private void test(final String commandWithArgs) throws IOException, InterruptedException {
        final Process process = new TestedProcessBuilder()
                .setCommandWithArgs(commandWithArgs)
                .setWorkingDir(new File("."))
                .start();
        final int result = process.waitFor();
        assertEquals(0, result, "Invalid exit code");

        final String out = systemOut.asString();
        final String requiredMessage = "STDOUT: Hello World!";
        assertTrue(
                out.contains(requiredMessage),
                format("Console out does not contain required message: '?'. Full out is \n?", requiredMessage, out)
        );
    }

    static boolean isRestJdkClientLauncherBuilt() {
        return new File("./RestJdkClientLauncher").exists();
    }

    static boolean isRestNettyClientLauncherBuilt() {
        return new File("./RestNettyClientLauncher").exists();
    }

}
// end::content[]
