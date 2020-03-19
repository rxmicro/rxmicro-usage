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

package io.rxmicro.examples.data.r2dbc.postgresql.close.resources.delete;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.operation.Delete;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@PostgreSQLRepository
public interface DeleteUsingInvalidSQLDataRepository {

    @Delete("DELETE FROM not_found RETURNING id, name")
    Mono<EntityFieldMap> deleteUsingInvalidSQL01();

    @Delete("DELETE FROM not_found RETURNING id, name")
    Single<EntityFieldMap> deleteUsingInvalidSQL02();

    @Delete("DELETE FROM not_found RETURNING id, name")
    Maybe<EntityFieldMap> deleteUsingInvalidSQL03();

    @Delete("DELETE FROM not_found RETURNING id, name")
    CompletableFuture<EntityFieldMap> deleteUsingInvalidSQL04();

    @Delete("DELETE FROM not_found RETURNING id, name")
    CompletableFuture<Optional<EntityFieldMap>> deleteUsingInvalidSQL05();

    @Delete("DELETE FROM not_found RETURNING id, name")
    CompletionStage<EntityFieldMap> deleteUsingInvalidSQL06();

    @Delete("DELETE FROM not_found RETURNING id, name")
    CompletionStage<Optional<EntityFieldMap>> deleteUsingInvalidSQL07();
    // -----------------------------------------------------------------------------------------------------------------

    @Delete("DELETE FROM not_found RETURNING id, name")
    Flux<EntityFieldMap> deleteUsingInvalidSQL08();

    @Delete("DELETE FROM not_found RETURNING id, name")
    Mono<List<EntityFieldMap>> deleteUsingInvalidSQL09();

    @Delete("DELETE FROM not_found RETURNING id, name")
    Flowable<EntityFieldMap> deleteUsingInvalidSQL10();

    @Delete("DELETE FROM not_found RETURNING id, name")
    Single<List<EntityFieldMap>> deleteUsingInvalidSQL11();

    @Delete("DELETE FROM not_found RETURNING id, name")
    CompletableFuture<List<EntityFieldMap>> deleteUsingInvalidSQL12();

    @Delete("DELETE FROM not_found RETURNING id, name")
    CompletionStage<List<EntityFieldMap>> deleteUsingInvalidSQL13();
}
