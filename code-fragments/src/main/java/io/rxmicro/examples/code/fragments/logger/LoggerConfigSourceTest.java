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
import io.rxmicro.logger.LoggerConfigSource;
import io.rxmicro.logger.LoggerConfigSources;
import io.rxmicro.logger.LoggerFactory;

public class LoggerConfigSourceTest {

    // tag::content[]
    // <1>
    static {
        LoggerConfigSources.setLoggerConfigSources(
                LoggerConfigSource.DEFAULT,
                LoggerConfigSource.CLASS_PATH_RESOURCE,
                LoggerConfigSource.TEST_CLASS_PATH_RESOURCE,
                LoggerConfigSource.FILE_AT_THE_HOME_DIR,
                LoggerConfigSource.FILE_AT_THE_CURRENT_DIR,
                LoggerConfigSource.FILE_AT_THE_RXMICRO_CONFIG_DIR,
                LoggerConfigSource.ENVIRONMENT_VARIABLES,
                LoggerConfigSource.JAVA_SYSTEM_PROPERTIES
        );
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerConfigSourceTest.class);

    public static void main(final String[] args) {
        LOGGER.info("Hello World!");
    }
    // end::content[]
}
