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

import io.rxmicro.logger.RequestIdSupplier;

// tag::content[]
public interface Logger {

    // ...

    void trace(RequestIdSupplier requestIdSupplier, Object... otherArguments);

    void debug(RequestIdSupplier requestIdSupplier, Object... otherArguments);

    void info(RequestIdSupplier requestIdSupplier, Object... otherArguments);

    void warn(RequestIdSupplier requestIdSupplier, Object... otherArguments);

    void error(RequestIdSupplier requestIdSupplier, Object... otherArguments);

    // ...
}
// end::content[]
