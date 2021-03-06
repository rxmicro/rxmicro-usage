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
import io.rxmicro.data.sql.model.EntityFieldMap;
import io.rxmicro.data.sql.operation.Insert;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@PostgreSQLRepository
public interface InsertUsingInvalidSQLDataRepository {

    @Insert("INSERT INTO not_found VALUES(1, 'name') RETURNING id, name")
    Mono<EntityFieldMap> insertUsingInvalidSQL01();

    @Insert("INSERT INTO not_found VALUES(1, 'name') RETURNING id, name")
    Single<EntityFieldMap> insertUsingInvalidSQL02();

    @Insert("INSERT INTO not_found VALUES(1, 'name') RETURNING id, name")
    Maybe<EntityFieldMap> insertUsingInvalidSQL03();

    @Insert("INSERT INTO not_found VALUES(1, 'name') RETURNING id, name")
    CompletableFuture<EntityFieldMap> insertUsingInvalidSQL04();

    @Insert("INSERT INTO not_found VALUES(1, 'name') RETURNING id, name")
    CompletableFuture<Optional<EntityFieldMap>> insertUsingInvalidSQL05();

    @Insert("INSERT INTO not_found VALUES(1, 'name') RETURNING id, name")
    CompletionStage<EntityFieldMap> insertUsingInvalidSQL06();

    @Insert("INSERT INTO not_found VALUES(1, 'name') RETURNING id, name")
    CompletionStage<Optional<EntityFieldMap>> insertUsingInvalidSQL07();
    // -----------------------------------------------------------------------------------------------------------------

    @Insert("INSERT INTO not_found SELECT * FROM account RETURNING id, name")
    Flux<EntityFieldMap> insertUsingInvalidSQL08();

    @Insert("INSERT INTO not_found SELECT * FROM account RETURNING id, name")
    Mono<List<EntityFieldMap>> insertUsingInvalidSQL09();

    @Insert("INSERT INTO not_found SELECT * FROM account RETURNING id, name")
    Flowable<EntityFieldMap> insertUsingInvalidSQL10();

    @Insert("INSERT INTO not_found SELECT * FROM account RETURNING id, name")
    Single<List<EntityFieldMap>> insertUsingInvalidSQL11();

    @Insert("INSERT INTO not_found SELECT * FROM account RETURNING id, name")
    CompletableFuture<List<EntityFieldMap>> insertUsingInvalidSQL12();

    @Insert("INSERT INTO not_found SELECT * FROM account RETURNING id, name")
    CompletionStage<List<EntityFieldMap>> insertUsingInvalidSQL13();
}
