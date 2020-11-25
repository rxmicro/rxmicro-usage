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

package io.rxmicro.examples.monitoring.request.tracing.controller;

import io.rxmicro.cdi.Inject;
import io.rxmicro.examples.monitoring.request.tracing.model.rest.Request;
import io.rxmicro.examples.monitoring.request.tracing.model.rest.Response;
import io.rxmicro.examples.monitoring.request.tracing.service.AccountService;
import io.rxmicro.rest.method.GET;
import reactor.core.publisher.Mono;

// tag::content[]
public final class AccountController {

    @Inject
    private AccountService accountService;

    @GET("/account/${id}")
    Mono<Response> findById(final Request request) {
        return accountService.findById(request);
    }
}
// end::content[]
