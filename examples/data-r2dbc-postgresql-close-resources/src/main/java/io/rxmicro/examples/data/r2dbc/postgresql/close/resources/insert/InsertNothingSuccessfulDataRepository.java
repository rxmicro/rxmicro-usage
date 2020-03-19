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

package io.rxmicro.examples.data.r2dbc.postgresql.close.resources.insert;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.sql.operation.Insert;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.close.resources.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@PostgreSQLRepository
public interface InsertNothingSuccessfulDataRepository {

    @Insert("INSERT INTO ${table} VALUES(1, 'email', 'firstName', 'lastName', 0.0, 'CEO'::role) " +
            "ON CONFLICT DO NOTHING " +
            "RETURNING *")
    Mono<Account> insertOne01();

    @Insert("INSERT INTO ${table} VALUES(1, 'email', 'firstName', 'lastName', 0.0, 'CEO'::role) " +
            "ON CONFLICT DO NOTHING " +
            "RETURNING *")
    Maybe<Account> insertOne02();

    @Insert("INSERT INTO ${table} VALUES(1, 'email', 'firstName', 'lastName', 0.0, 'CEO'::role) " +
            "ON CONFLICT DO NOTHING " +
            "RETURNING *")
    CompletableFuture<Optional<Account>> insertOne03();

    @Insert("INSERT INTO ${table} VALUES(1, 'email', 'firstName', 'lastName', 0.0, 'CEO'::role) " +
            "ON CONFLICT DO NOTHING " +
            "RETURNING *")
    CompletionStage<Optional<Account>> insertOne04();

    // -----------------------------------------------------------------------------------------------------------------

    @Insert("INSERT INTO ${table} SELECT * FROM ${table} " +
            "ON CONFLICT DO NOTHING " +
            "RETURNING *")
    Mono<List<Account>> insertMany01();

    @Insert("INSERT INTO ${table} SELECT * FROM ${table} " +
            "ON CONFLICT DO NOTHING " +
            "RETURNING *")
    Flux<Account> insertMany02();

    @Insert("INSERT INTO ${table} SELECT * FROM ${table} " +
            "ON CONFLICT DO NOTHING " +
            "RETURNING *")
    Single<List<Account>> insertMany03();

    @Insert("INSERT INTO ${table} SELECT * FROM ${table} " +
            "ON CONFLICT DO NOTHING " +
            "RETURNING *")
    Flowable<Account> insertMany04();

    @Insert("INSERT INTO ${table} SELECT * FROM ${table} " +
            "ON CONFLICT DO NOTHING " +
            "RETURNING *")
    CompletableFuture<List<Account>> insertMany05();

    @Insert("INSERT INTO ${table} SELECT * FROM ${table} " +
            "ON CONFLICT DO NOTHING " +
            "RETURNING *")
    CompletionStage<List<Account>> insertMany06();
}
