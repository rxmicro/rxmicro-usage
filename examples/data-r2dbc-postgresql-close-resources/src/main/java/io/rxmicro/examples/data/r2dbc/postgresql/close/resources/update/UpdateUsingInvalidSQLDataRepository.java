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
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.operation.Update;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@PostgreSQLRepository
public interface UpdateUsingInvalidSQLDataRepository {

    @Update("UPDATE not_found SET v=1 RETURNING id, name")
    Mono<EntityFieldMap> updateUsingInvalidSQL01();

    @Update("UPDATE not_found SET v=1 RETURNING id, name")
    Single<EntityFieldMap> updateUsingInvalidSQL02();

    @Update("UPDATE not_found SET v=1 RETURNING id, name")
    Maybe<EntityFieldMap> updateUsingInvalidSQL03();

    @Update("UPDATE not_found SET v=1 RETURNING id, name")
    CompletableFuture<EntityFieldMap> updateUsingInvalidSQL04();

    @Update("UPDATE not_found SET v=1 RETURNING id, name")
    CompletableFuture<Optional<EntityFieldMap>> updateUsingInvalidSQL05();

    @Update("UPDATE not_found SET v=1 RETURNING id, name")
    CompletionStage<EntityFieldMap> updateUsingInvalidSQL06();

    @Update("UPDATE not_found SET v=1 RETURNING id, name")
    CompletionStage<Optional<EntityFieldMap>> updateUsingInvalidSQL07();
    // -----------------------------------------------------------------------------------------------------------------

    @Update("UPDATE not_found SET v=1 RETURNING id, name")
    Flux<EntityFieldMap> updateUsingInvalidSQL08();

    @Update("UPDATE not_found SET v=1 RETURNING id, name")
    Mono<List<EntityFieldMap>> updateUsingInvalidSQL09();

    @Update("UPDATE not_found SET v=1 RETURNING id, name")
    Flowable<EntityFieldMap> updateUsingInvalidSQL10();

    @Update("UPDATE not_found SET v=1 RETURNING id, name")
    Single<List<EntityFieldMap>> updateUsingInvalidSQL11();

    @Update("UPDATE not_found SET v=1 RETURNING id, name")
    CompletableFuture<List<EntityFieldMap>> updateUsingInvalidSQL12();

    @Update("UPDATE not_found SET v=1 RETURNING id, name")
    CompletionStage<List<EntityFieldMap>> updateUsingInvalidSQL13();
}
