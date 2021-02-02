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

import io.rxmicro.test.dbunit.TestDatabaseConfig;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.GenericContainer;

import java.lang.reflect.Method;

import static io.rxmicro.test.dbunit.TestDatabaseConfig.getCurrentTestDatabaseConfig;

public final class BeforeDbUnitTestDebugExtension extends BaseDebugExtension implements
        BeforeAllCallback, BeforeTestExecutionCallback,
        AfterTestExecutionCallback, AfterAllCallback {

    @Override
    public void beforeAll(final ExtensionContext context) {
        log(context, "<before:before> ALL");
    }

    @Override
    public void beforeTestExecution(final ExtensionContext context) {
        final Method method = context.getRequiredTestMethod();
        final GenericContainer<?> container = getPostgresContainer(context);
        final TestDatabaseConfig config = getCurrentTestDatabaseConfig();
        log(
                context,
                "<before:before> [?] Docker container socket: jdbc:postgresql://?:?/rxmicro",
                method.getName(), container.getContainerIpAddress(), container.getFirstMappedPort()
        );
        log(
                context,
                "<before:before> [?] TestDatabaseConfig.jdbc: ?",
                method.getName(), config.getJdbcUrl()
        );
    }

    @Override
    public void afterTestExecution(final ExtensionContext context)  {
        final Method method = context.getRequiredTestMethod();
        final GenericContainer<?> container = getPostgresContainer(context);
        final TestDatabaseConfig config = getCurrentTestDatabaseConfig();
        log(
                context,
                "<before:after>  [?] Docker container socket: jdbc:postgresql://?:?/rxmicro",
                method.getName(), container.getContainerIpAddress(), container.getFirstMappedPort()
        );
        log(
                context,
                "<before:after>  [?] TestDatabaseConfig.jdbc: ?",
                method.getName(), config.getJdbcUrl()
        );
    }

    @Override
    public void afterAll(final ExtensionContext context){
        log(context, "<before:after>  ALL");
    }
}

