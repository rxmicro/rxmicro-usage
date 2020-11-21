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
import io.rxmicro.data.sql.VariableValues;
import io.rxmicro.data.sql.operation.Insert;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.close.resources.model.ProductWithInvalidColumnType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static io.rxmicro.examples.data.r2dbc.postgresql.close.resources.Constants.PRODUCT_TABLE_NAME;

@PostgreSQLRepository
@VariableValues({
        "${table}", PRODUCT_TABLE_NAME
})
public interface InsertInvalidEntityModelRepository {

    @Insert("INSERT INTO ${table} VALUES(112, 'name', 100.00, 10) RETURNING *")
    Mono<ProductWithInvalidColumnType> insertOne13();

    @Insert("INSERT INTO ${table} VALUES(113, 'name', 100.00, 10) RETURNING *")
    Maybe<ProductWithInvalidColumnType> insertOne14();

    @Insert("INSERT INTO ${table} VALUES(114, 'name', 100.00, 10) RETURNING *")
    CompletableFuture<Optional<ProductWithInvalidColumnType>> insertOne15();

    @Insert("INSERT INTO ${table} VALUES(115, 'name', 100.00, 10) RETURNING *")
    CompletionStage<Optional<ProductWithInvalidColumnType>> insertOne16();

    // -----------------------------------------------------------------------------------------------------------------

    @Insert("INSERT INTO ${table} SELECT 116, 'name', 100.00, 10 RETURNING *")
    Mono<List<ProductWithInvalidColumnType>> insertMany17();

    @Insert("INSERT INTO ${table} SELECT 117, 'name', 100.00, 10 RETURNING *")
    Flux<ProductWithInvalidColumnType> insertMany18();

    @Insert("INSERT INTO ${table} SELECT 118, 'name', 100.00, 10 RETURNING *")
    Single<List<ProductWithInvalidColumnType>> insertMany19();

    @Insert("INSERT INTO ${table} SELECT 119, 'name', 100.00, 10 RETURNING *")
    Flowable<ProductWithInvalidColumnType> insertMany20();

    @Insert("INSERT INTO ${table} SELECT 120, 'name', 100.00, 10 RETURNING *")
    CompletableFuture<List<ProductWithInvalidColumnType>> insertMany21();

    @Insert("INSERT INTO ${table} SELECT 121, 'name', 100.00, 10 RETURNING *")
    CompletionStage<List<ProductWithInvalidColumnType>> insertMany22();
}
