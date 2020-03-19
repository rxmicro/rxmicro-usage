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

package io.rxmicro.examples.data.r2dbc.postgresql.concurrent.isolation.level;

import io.reactivex.rxjava3.core.Single;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.isolation.level.model.Account;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

import static io.rxmicro.data.RepositoryFactory.getRepository;
import static io.rxmicro.data.sql.model.IsolationLevel.SERIALIZABLE;

public final class BusinessService {

    private final MonoDataRepository monoDataRepository =
            getRepository(MonoDataRepository.class);

    private final SingleDataRepository singleDataRepository =
            getRepository(SingleDataRepository.class);

    private final CompletableFutureDataRepository completableFutureDataRepository =
            getRepository(CompletableFutureDataRepository.class);

    public Mono<String> testUsingReactor() {
        return monoDataRepository.begin(SERIALIZABLE)
                .flatMap(transaction -> monoDataRepository.findAccount(transaction, 1L)
                        .map(Account::getEmail)
                        .flatMap(email -> transaction.rollback()
                                .thenReturn(email))
                );
    }

    public Single<String> testUsingRxJava() {
        return singleDataRepository.begin(SERIALIZABLE)
                .flatMap(transaction -> singleDataRepository.findAccount(transaction, 1L)
                        .map(Account::getEmail)
                        .flatMap(email -> transaction.rollback()
                                .andThen(Single.just(email)))
                );
    }

    public CompletableFuture<String> testUsingCompletableFuture() {
        return completableFutureDataRepository.begin(SERIALIZABLE)
                .thenCompose(transaction -> completableFutureDataRepository.findAccount(transaction, 1L)
                        .thenApply(Account::getEmail)
                        .thenCompose(email -> transaction.rollback()
                                .thenApply(v -> email)));
    }
}
