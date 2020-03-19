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
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.sql.operation.Select;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.close.resources.model.AccountWithInvalidColumnType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@PostgreSQLRepository
public interface SelectUsingInvalidEntityModelDataRepository {

    @Select("SELECT * FROM ${table}")
    Mono<AccountWithInvalidColumnType> findUsingInvalidEntityModel01();

    @Select("SELECT * FROM ${table}")
    Single<AccountWithInvalidColumnType> findUsingInvalidEntityModel02();

    @Select("SELECT * FROM ${table}")
    Maybe<AccountWithInvalidColumnType> findUsingInvalidEntityModel03();

    @Select("SELECT * FROM ${table}")
    CompletableFuture<AccountWithInvalidColumnType> findUsingInvalidEntityModel04();

    @Select("SELECT * FROM ${table}")
    CompletableFuture<Optional<AccountWithInvalidColumnType>> findUsingInvalidEntityModel05();

    @Select("SELECT * FROM ${table}")
    CompletionStage<AccountWithInvalidColumnType> findUsingInvalidEntityModel06();

    @Select("SELECT * FROM ${table}")
    CompletionStage<Optional<AccountWithInvalidColumnType>> findUsingInvalidEntityModel07();
    // -----------------------------------------------------------------------------------------------------------------

    @Select("SELECT * FROM ${table}")
    Flux<AccountWithInvalidColumnType> findUsingInvalidEntityModel08();

    @Select("SELECT * FROM ${table}")
    Mono<List<AccountWithInvalidColumnType>> findUsingInvalidEntityModel09();

    @Select("SELECT * FROM ${table}")
    Flowable<AccountWithInvalidColumnType> findUsingInvalidEntityModel10();

    @Select("SELECT * FROM ${table}")
    Single<List<AccountWithInvalidColumnType>> findUsingInvalidEntityModel11();

    @Select("SELECT * FROM ${table}")
    CompletableFuture<List<AccountWithInvalidColumnType>> findUsingInvalidEntityModel12();

    @Select("SELECT * FROM ${table}")
    CompletionStage<List<AccountWithInvalidColumnType>> findUsingInvalidEntityModel13();

}
