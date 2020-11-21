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
import io.rxmicro.data.sql.VariableValues;
import io.rxmicro.data.sql.operation.Delete;
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
public interface DeleteInvalidEntityModelDataRepository {

    @Delete("DELETE FROM ${table} WHERE id = 13 RETURNING *")
    Mono<ProductWithInvalidColumnType> deleteOne13();

    @Delete("DELETE FROM ${table} WHERE id = 14 RETURNING *")
    Maybe<ProductWithInvalidColumnType> deleteOne14();

    @Delete("DELETE FROM ${table} WHERE id = 15 RETURNING *")
    CompletableFuture<Optional<ProductWithInvalidColumnType>> deleteOne15();

    @Delete("DELETE FROM ${table} WHERE id = 16 RETURNING *")
    CompletionStage<Optional<ProductWithInvalidColumnType>> deleteOne16();

    // -----------------------------------------------------------------------------------------------------------------

    @Delete("DELETE FROM ${table} WHERE id = 17 RETURNING *")
    Mono<List<ProductWithInvalidColumnType>> deleteMany17();

    @Delete("DELETE FROM ${table} WHERE id = 18 RETURNING *")
    Flux<ProductWithInvalidColumnType> deleteMany18();

    @Delete("DELETE FROM ${table} WHERE id = 19 RETURNING *")
    Single<List<ProductWithInvalidColumnType>> deleteMany19();

    @Delete("DELETE FROM ${table} WHERE id = 20 RETURNING *")
    Flowable<ProductWithInvalidColumnType> deleteMany20();

    @Delete("DELETE FROM ${table} WHERE id = 21 RETURNING *")
    CompletableFuture<List<ProductWithInvalidColumnType>> deleteMany21();

    @Delete("DELETE FROM ${table} WHERE id = 22 RETURNING *")
    CompletionStage<List<ProductWithInvalidColumnType>> deleteMany22();
}
