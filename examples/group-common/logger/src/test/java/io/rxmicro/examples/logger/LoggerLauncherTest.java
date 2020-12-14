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

package io.rxmicro.examples.logger;

import io.rxmicro.test.SystemOut;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.rxmicro.logger.LoggerImplProviderFactory.resetLoggerImplFactory;
import static io.rxmicro.test.junit.ExAssertions.assertSystemOutContains;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RxMicroIntegrationTest
final class LoggerLauncherTest {

    private SystemOut systemOut;

    @BeforeEach
    void beforeEach() {
        resetLoggerImplFactory();
    }

    @Test
    @Order(1)
    void Should_display_additional_information() {
        LoggerLauncher.main(new String[0]);

        assertSystemOutContains(systemOut, "io.rxmicro.examples.logger.LoggerLauncher.main(LoggerLauncher.java:27): From main()");
        assertSystemOutContains(systemOut, "io.rxmicro.examples.logger.LoggerLauncher.test(LoggerLauncher.java:32): From test()");
    }

    @AfterEach
    void afterEach(){
        resetLoggerImplFactory();
    }
}