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

import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;

public final class LoggerLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerLauncher.class);

    private static void test() {
        LOGGER.info("From test()");
    }

    public static void main(final String[] args) {
        LOGGER.info("From main()");
        test();
    }
}
