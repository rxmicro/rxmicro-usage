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

package io.rxmicro.examples.monitoring.request.tracing.model;

import io.rxmicro.http.error.HttpErrorException;

// tag::content[]
public final class AccountNotFound404Exception extends HttpErrorException {

    public static final int STATUS_CODE = 404;

    public AccountNotFound404Exception() {
        super(STATUS_CODE);
    }
}
// end::content[]
