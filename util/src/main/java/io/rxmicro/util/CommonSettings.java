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

package io.rxmicro.util;

import io.rxmicro.common.InvalidStateException;

import java.util.Optional;

public final class CommonSettings {

    public static final String RX_MICRO_WORKSPACE_HOME_VARIABLE_NAME = "RX_MICRO_WORKSPACE_HOME";

    public static final String RX_MICRO_WORKSPACE_HOME;

    static {
        RX_MICRO_WORKSPACE_HOME = Optional.ofNullable(System.getenv(RX_MICRO_WORKSPACE_HOME_VARIABLE_NAME)).orElseThrow(() -> {
            throw new InvalidStateException("System variable '?' not defined", RX_MICRO_WORKSPACE_HOME_VARIABLE_NAME);
        });
    }

    private CommonSettings() {
    }
}

