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

package io.rxmicro.examples.test.dbunit.junit.debug;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.GenericContainer;

import java.lang.reflect.Field;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.reflection.Reflections.getDeclaredField;
import static io.rxmicro.reflection.Reflections.getFieldValue;

class BaseDebugExtension {

    final GenericContainer<?> getPostgresContainer(final ExtensionContext context) {
        try {
            final Field field = getDeclaredField(context.getRequiredTestClass(), "POSTGRESQL_TEST_DB");
            return (GenericContainer<?>) getFieldValue((Object) null, field);
        } catch (final Exception exception) {
            final Field field = getDeclaredField(context.getRequiredTestClass(), "postgresqlTestDb");
            return (GenericContainer<?>) getFieldValue(context.getRequiredTestInstance(), field);
        }
    }

    final void log(final ExtensionContext context,
                   final String messageTemplate,
                   final Object... args) {
        final String message = format(messageTemplate, args);
        final Thread currentThread = Thread.currentThread();
        System.out.println(
                format("[INFO] Running ? {?/?}: ?",
                        context.getRequiredTestClass().getName(), currentThread.getId(), currentThread.getName(), message
                )
        );
    }
}

