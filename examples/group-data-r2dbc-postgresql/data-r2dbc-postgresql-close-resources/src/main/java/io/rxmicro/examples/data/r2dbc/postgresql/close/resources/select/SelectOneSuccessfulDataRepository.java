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

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.sql.operation.Select;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.close.resources.model.Account;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@PostgreSQLRepository
public interface SelectOneSuccessfulDataRepository {

    @Select("SELECT email FROM ${table} WHERE id=1")
    Mono<Account> findOne01();

    @Select("SELECT email FROM ${table} WHERE id=1")
    Single<Account> findOne02();

    @Select("SELECT email FROM ${table} WHERE id=1")
    Maybe<Account> findOne03();

    @Select("SELECT email FROM ${table} WHERE id=1")
    CompletableFuture<Account> findOne04();

    @Select("SELECT email FROM ${table} WHERE id=1")
    CompletableFuture<Optional<Account>> findOne05();

    @Select("SELECT email FROM ${table} WHERE id=1")
    CompletionStage<Account> findOne06();

    @Select("SELECT email FROM ${table} WHERE id=1")
    CompletionStage<Optional<Account>> findOne07();

    // -----------------------------------------------------------------------------------------------------------------

    @Select("SELECT email FROM ${table} WHERE id=-1")
    Mono<Account> findOne08();

    @Select("SELECT email FROM ${table} WHERE id=-1")
    Maybe<Account> findOne09();

    @Select("SELECT email FROM ${table} WHERE id=-1")
    CompletableFuture<Optional<Account>> findOne10();

    @Select("SELECT email FROM ${table} WHERE id=-1")
    CompletionStage<Optional<Account>> findOne11();
}
