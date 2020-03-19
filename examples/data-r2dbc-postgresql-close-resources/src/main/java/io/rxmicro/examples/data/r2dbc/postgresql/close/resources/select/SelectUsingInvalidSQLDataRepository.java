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
import io.rxmicro.data.sql.VariableValues;
import io.rxmicro.data.sql.operation.Select;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static io.rxmicro.examples.data.r2dbc.postgresql.close.resources.Constants.ACCOUNT_TABLE_NAME;

@PostgreSQLRepository
@VariableValues({
        "${table}", ACCOUNT_TABLE_NAME
})
public interface SelectUsingInvalidSQLDataRepository {

    @Select("SELECT not_found FROM ${table}")
    Mono<String> findUsingInvalidSQL01();

    @Select("SELECT not_found FROM ${table}")
    Single<String> findUsingInvalidSQL02();

    @Select("SELECT not_found FROM ${table}")
    Maybe<String> findUsingInvalidSQL03();

    @Select("SELECT not_found FROM ${table}")
    CompletableFuture<String> findUsingInvalidSQL04();

    @Select("SELECT not_found FROM ${table}")
    CompletableFuture<Optional<String>> findUsingInvalidSQL05();

    @Select("SELECT not_found FROM ${table}")
    CompletionStage<String> findUsingInvalidSQL06();

    @Select("SELECT not_found FROM ${table}")
    CompletionStage<Optional<String>> findUsingInvalidSQL07();
    // -----------------------------------------------------------------------------------------------------------------

    @Select("SELECT not_found FROM ${table}")
    Flux<String> findUsingInvalidSQL08();

    @Select("SELECT not_found FROM ${table}")
    Mono<List<String>> findUsingInvalidSQL09();

    @Select("SELECT not_found FROM ${table}")
    Flowable<String> findUsingInvalidSQL10();

    @Select("SELECT not_found FROM ${table}")
    Single<List<String>> findUsingInvalidSQL11();

    @Select("SELECT not_found FROM ${table}")
    CompletableFuture<List<String>> findUsingInvalidSQL12();

    @Select("SELECT not_found FROM ${table}")
    CompletionStage<List<String>> findUsingInvalidSQL13();
}
