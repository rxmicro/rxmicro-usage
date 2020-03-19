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

package io.rxmicro.examples.data.r2dbc.postgresql.close.resources.update;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.sql.operation.Update;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.close.resources.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@PostgreSQLRepository
public interface UpdateNothingSuccessfulDataRepository {

    @Update("UPDATE ${table} SET balance=0.0 WHERE id = -1 RETURNING *")
    Mono<Account> updateOne01();

    @Update("UPDATE ${table} SET balance=0.0 WHERE id = -1 RETURNING *")
    Maybe<Account> updateOne02();

    @Update("UPDATE ${table} SET balance=0.0 WHERE id = -1 RETURNING *")
    CompletableFuture<Optional<Account>> updateOne03();

    @Update("UPDATE ${table} SET balance=0.0 WHERE id = -1 RETURNING *")
    CompletionStage<Optional<Account>> updateOne04();

    // -----------------------------------------------------------------------------------------------------------------

    @Update("UPDATE ${table} SET balance=0.0 WHERE id < 0 RETURNING *")
    Mono<List<Account>> updateMany01();

    @Update("UPDATE ${table} SET balance=0.0 WHERE id < 0 RETURNING *")
    Flux<Account> updateMany02();

    @Update("UPDATE ${table} SET balance=0.0 WHERE id < 0 RETURNING *")
    Single<List<Account>> updateMany03();

    @Update("UPDATE ${table} SET balance=0.0 WHERE id < 0 RETURNING *")
    Flowable<Account> updateMany04();

    @Update("UPDATE ${table} SET balance=0.0 WHERE id < 0 RETURNING *")
    CompletableFuture<List<Account>> updateMany05();

    @Update("UPDATE ${table} SET balance=0.0 WHERE id < 0 RETURNING *")
    CompletionStage<List<Account>> updateMany06();
}
