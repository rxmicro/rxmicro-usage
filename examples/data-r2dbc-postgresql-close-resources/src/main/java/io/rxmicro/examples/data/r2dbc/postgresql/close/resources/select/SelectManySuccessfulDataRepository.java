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

package io.rxmicro.examples.data.r2dbc.postgresql.close.resources.select;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.sql.operation.Select;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.close.resources.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@PostgreSQLRepository
public interface SelectManySuccessfulDataRepository {

    @Select("SELECT * FROM ${table}")
    Mono<List<Account>> findMany01();

    @Select("SELECT * FROM ${table}")
    Flux<Account> findMany02();

    @Select("SELECT * FROM ${table}")
    Single<List<Account>> findMany03();

    @Select("SELECT * FROM ${table}")
    Flowable<Account> findMany04();

    @Select("SELECT * FROM ${table}")
    CompletableFuture<List<Account>> findMany05();

    @Select("SELECT * FROM ${table}")
    CompletionStage<List<Account>> findMany06();

    // -----------------------------------------------------------------------------------------------------------------

    @Select("SELECT * FROM ${table} LIMIT 0")
    Mono<List<Account>> findMany07();

    @Select("SELECT * FROM ${table} LIMIT 0")
    Flux<Account> findMany08();

    @Select("SELECT * FROM ${table} LIMIT 0")
    Single<List<Account>> findMany09();

    @Select("SELECT * FROM ${table} LIMIT 0")
    Flowable<Account> findMany10();

    @Select("SELECT * FROM ${table} LIMIT 0")
    CompletableFuture<List<Account>> findMany11();

    @Select("SELECT * FROM ${table} LIMIT 0")
    CompletionStage<List<Account>> findMany12();
}
