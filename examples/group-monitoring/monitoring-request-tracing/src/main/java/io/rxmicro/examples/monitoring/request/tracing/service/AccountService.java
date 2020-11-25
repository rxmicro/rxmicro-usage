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

package io.rxmicro.examples.monitoring.request.tracing.service;

import io.rxmicro.cdi.Inject;
import io.rxmicro.examples.monitoring.request.tracing.model.AccountNotFound404Exception;
import io.rxmicro.examples.monitoring.request.tracing.model.rest.Request;
import io.rxmicro.examples.monitoring.request.tracing.model.rest.Response;
import io.rxmicro.examples.monitoring.request.tracing.repository.AccountRepository;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import reactor.core.publisher.Mono;

// tag::content[]
public final class AccountService {

    // <1>
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    @Inject
    private AccountRepository accountRepository;

    public Mono<Response> findById(final Request request) {
        LOGGER.debug(
                request, // <2>
                "Finding account by id=?...", request.getId()
        );
        return accountRepository.findById(request, request.getId())
                .switchIfEmpty(Mono.error(() -> {
                    LOGGER.error(
                            request, // <3>
                            "Account not found by id=?!", request.getId()
                    );
                    return new AccountNotFound404Exception();
                }))
                .map(account -> {
                    LOGGER.debug(
                            request, // <4>
                            "Account exists by id=?: ?",
                            request.getId(), account
                    );
                    return new Response(
                            account.getEmail(), account.getFirstName(), account.getLastName()
                    );
                });
    }
}
// end::content[]
