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

package io.rxmicro.examples.graalvm.nativeimage.mongo.data;

import io.rxmicro.test.SystemOut;
import io.rxmicro.test.TestedProcessBuilder;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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
@Testcontainers
@RxMicroIntegrationTest
public final class MongoLauncher_UsingNativeImage_IT {

    @Container
    private static final GenericContainer<?> MONGO_TEST_DB =
            new GenericContainer<>("rxmicro/mongo-test-db")
                    .withExposedPorts(27017);

    private SystemOut systemOut;

    @Test
    void Should_display_all_Mongo_operation_results() throws IOException, InterruptedException {
        Process process = null;
        try {
            process = new TestedProcessBuilder()
                    .setCommandWithArgs(
                            "./MongoLauncher",
                            "-Dmongo.host=" + MONGO_TEST_DB.getContainerIpAddress(),
                            "-Dmongo.port=" + MONGO_TEST_DB.getFirstMappedPort()
                    )
                    .setRedirectStdOutAndStdErrToSysOut(true)
                    .setWorkingDir(new File("."))
                    .start();
            final int result = process.waitFor();
            assertEquals(0, result, "Invalid exit code");

            final String out = systemOut.asString();
            assertRequestMessageExists(
                    out,
                    "STDOUT: Account created: Account{id=100, email='test1@rxmicro.io', firstName='First1', " +
                            "lastName='Last1', balance=5000.00, role=Lead_Engineer}, result=BsonInt64{value=100}");
            assertRequestMessageExists(
                    out,
                    "STDOUT: Account updated: Account{id=100, email='test2@rxmicro.io', firstName='First2', " +
                            "lastName='Last2', balance=10000.00, role=CEO}, result=true\n"
            );
            assertRequestMessageExists(
                    out,
                    "STDOUT: countDocuments: 7"
            );
            assertRequestMessageExists(
                    out,
                    "STDOUT: estimatedDocumentCount: 7"
            );
            assertRequestMessageExists(
                    out,
                    "STDOUT: estimatedDocumentCount: 7"
            );
            assertRequestMessageExists(
                    out,
                    "STDOUT: Account found: Account{id=100, email='test2@rxmicro.io', firstName='First2', " +
                            "lastName='Last2', balance=10000.00, role=CEO}"
            );
            assertRequestMessageExists(
                    out,
                    "STDOUT: Aggregate reports: [Report{id=Systems_Architect, total=0}, Report{id=Lead_Engineer, total=0}, " +
                            "Report{id=Engineer, total=0}, Report{id=CEO, total=0}]"
            );
            assertRequestMessageExists(
                    out,
                    "STDOUT: Account deleted: Account{id=100, email='null', firstName='null', " +
                            "lastName='null', balance=null, role=null}, result=true"
            );
        } finally {
            if (process != null) {
                process.destroyForcibly();
            }
        }
    }

    private void assertRequestMessageExists(final String out,
                                            final String requiredMessage) {
        assertTrue(
                out.contains(requiredMessage),
                format("Console out does not contain required message: '?'. Full out is \n?", requiredMessage, out)
        );
    }
}
