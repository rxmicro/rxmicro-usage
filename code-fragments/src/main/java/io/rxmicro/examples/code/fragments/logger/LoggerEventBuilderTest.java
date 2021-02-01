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

package io.rxmicro.examples.code.fragments.logger;

import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerEventBuilder;
import io.rxmicro.logger.LoggerFactory;

public final class LoggerEventBuilderTest {

    // tag::intro[]
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerEventBuilderTest.class);

    public static void main(final String[] args) {
        final LoggerEventBuilder builder = LoggerFactory.newLoggerEventBuilder();
        builder.setMessage("Hello World!");
        LOGGER.info(builder.build());
    }
    // end::intro[]

    // tag::usage[]
    public void log1(final Throwable throwable,
                     final boolean withStackTrace) {
        final LoggerEventBuilder builder = LoggerFactory.newLoggerEventBuilder()
                .setMessage("Some error message: ?", throwable.getMessage());
        if (withStackTrace) {
            builder.setThrowable(throwable);
        }
        LOGGER.error(builder.build());
    }

    public void log2(final Throwable throwable,
                     final boolean withStackTrace) {
        if (withStackTrace) {
            LOGGER.error(throwable, "Some error message: ?", throwable.getMessage());
        } else {
            LOGGER.error("Some error message: ?", throwable.getMessage());
        }
    }
    // end::usage[]


    public void features(final Throwable throwable) {
        // tag::features[]
        LOGGER.info(
                LoggerFactory.newLoggerEventBuilder()
                        .setMessage("Some error message")
                        .setThreadName("Test-Thread-Name")
                        .setThreadId(34L)
                        .setStackFrame("package.Class", "method", "Test.java", 85)
                        .setThrowable(throwable)
                        .build()
        );
        // end::features[]
    }
}

