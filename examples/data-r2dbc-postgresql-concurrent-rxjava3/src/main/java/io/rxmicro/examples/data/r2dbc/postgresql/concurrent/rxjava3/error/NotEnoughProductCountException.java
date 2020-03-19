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

package io.rxmicro.examples.data.r2dbc.postgresql.concurrent.rxjava3.error;

import io.rxmicro.common.RxMicroException;

import static io.rxmicro.common.util.Formats.format;

// tag::content[]
public final class NotEnoughProductCountException extends RxMicroException {

    public NotEnoughProductCountException(final int expectedCount,
                                          final int actualCount) {
        super(
                format("Not enough product count: expected=?, but actual=?", expectedCount, actualCount),
                false,
                false
        );
    }
}
// end::content[]
