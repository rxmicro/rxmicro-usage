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

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.sql.VariableValues;
import io.rxmicro.data.sql.operation.Delete;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.close.resources.model.Product;
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
public interface DeleteSuccessfulDataRepository {

    @Delete("DELETE FROM ${table} WHERE id = 1")
    Mono<Void> deleteOne01();

    @Delete("DELETE FROM ${table} WHERE id = 2")
    Mono<Boolean> deleteOne02();

    @Delete("DELETE FROM ${table} WHERE id = 3")
    Mono<Integer> deleteOne03();

    @Delete("DELETE FROM ${table} WHERE id = 4")
    Completable deleteOne04();

    @Delete("DELETE FROM ${table} WHERE id = 5")
    Single<Boolean> deleteOne05();

    @Delete("DELETE FROM ${table} WHERE id = 6")
    Single<Integer> deleteOne06();

    @Delete("DELETE FROM ${table} WHERE id = 7")
    CompletableFuture<Void> deleteOne07();

    @Delete("DELETE FROM ${table} WHERE id = 8")
    CompletableFuture<Boolean> deleteOne08();

    @Delete("DELETE FROM ${table} WHERE id = 9")
    CompletableFuture<Integer> deleteOne09();

    @Delete("DELETE FROM ${table} WHERE id = 10")
    CompletionStage<Void> deleteOne10();

    @Delete("DELETE FROM ${table} WHERE id = 11")
    CompletionStage<Boolean> deleteOne11();

    @Delete("DELETE FROM ${table} WHERE id = 12")
    CompletionStage<Integer> deleteOne12();
    // -----------------------------------------------------------------------------------------------------------------

    @Delete("DELETE FROM ${table} WHERE id = 13 RETURNING *")
    Mono<Product> deleteOne13();

    @Delete("DELETE FROM ${table} WHERE id = 14 RETURNING *")
    Maybe<Product> deleteOne14();

    @Delete("DELETE FROM ${table} WHERE id = 15 RETURNING *")
    CompletableFuture<Optional<Product>> deleteOne15();

    @Delete("DELETE FROM ${table} WHERE id = 16 RETURNING *")
    CompletionStage<Optional<Product>> deleteOne16();

    // -----------------------------------------------------------------------------------------------------------------

    @Delete("DELETE FROM ${table} WHERE id = 17 RETURNING *")
    Mono<List<Product>> deleteMany17();

    @Delete("DELETE FROM ${table} WHERE id = 18 RETURNING *")
    Flux<Product> deleteMany18();

    @Delete("DELETE FROM ${table} WHERE id = 19 RETURNING *")
    Single<List<Product>> deleteMany19();

    @Delete("DELETE FROM ${table} WHERE id = 20 RETURNING *")
    Flowable<Product> deleteMany20();

    @Delete("DELETE FROM ${table} WHERE id = 21 RETURNING *")
    CompletableFuture<List<Product>> deleteMany21();

    @Delete("DELETE FROM ${table} WHERE id = 22 RETURNING *")
    CompletionStage<List<Product>> deleteMany22();
}
