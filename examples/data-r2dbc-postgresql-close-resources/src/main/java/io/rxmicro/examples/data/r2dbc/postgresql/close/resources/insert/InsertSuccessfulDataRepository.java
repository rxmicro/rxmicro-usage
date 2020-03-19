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

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.sql.VariableValues;
import io.rxmicro.data.sql.operation.Insert;
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
public interface InsertSuccessfulDataRepository {

    @Insert("INSERT INTO ${table} VALUES(100, 'name', 100.00, 10)")
    Mono<Void> insertOne01();

    @Insert("INSERT INTO ${table} VALUES(101, 'name', 100.00, 10)")
    Mono<Boolean> insertOne02();

    @Insert("INSERT INTO ${table} VALUES(102, 'name', 100.00, 10)")
    Mono<Integer> insertOne03();

    @Insert("INSERT INTO ${table} VALUES(103, 'name', 100.00, 10)")
    Completable insertOne04();

    @Insert("INSERT INTO ${table} VALUES(104, 'name', 100.00, 10)")
    Single<Boolean> insertOne05();

    @Insert("INSERT INTO ${table} VALUES(105, 'name', 100.00, 10)")
    Single<Integer> insertOne06();

    @Insert("INSERT INTO ${table} VALUES(106, 'name', 100.00, 10)")
    CompletableFuture<Void> insertOne07();

    @Insert("INSERT INTO ${table} VALUES(107, 'name', 100.00, 10)")
    CompletableFuture<Boolean> insertOne08();

    @Insert("INSERT INTO ${table} VALUES(108, 'name', 100.00, 10)")
    CompletableFuture<Integer> insertOne09();

    @Insert("INSERT INTO ${table} VALUES(109, 'name', 100.00, 10)")
    CompletionStage<Void> insertOne10();

    @Insert("INSERT INTO ${table} VALUES(110, 'name', 100.00, 10)")
    CompletionStage<Boolean> insertOne11();

    @Insert("INSERT INTO ${table} VALUES(111, 'name', 100.00, 10)")
    CompletionStage<Integer> insertOne12();
    // -----------------------------------------------------------------------------------------------------------------

    @Insert("INSERT INTO ${table} VALUES(112, 'name', 100.00, 10) RETURNING *")
    Mono<Product> insertOne13();

    @Insert("INSERT INTO ${table} VALUES(113, 'name', 100.00, 10) RETURNING *")
    Maybe<Product> insertOne14();

    @Insert("INSERT INTO ${table} VALUES(114, 'name', 100.00, 10) RETURNING *")
    CompletableFuture<Optional<Product>> insertOne15();

    @Insert("INSERT INTO ${table} VALUES(115, 'name', 100.00, 10) RETURNING *")
    CompletionStage<Optional<Product>> insertOne16();

    // -----------------------------------------------------------------------------------------------------------------

    @Insert("INSERT INTO ${table} SELECT 116, 'name', 100.00, 10 RETURNING *")
    Mono<List<Product>> insertMany17();

    @Insert("INSERT INTO ${table} SELECT 117, 'name', 100.00, 10 RETURNING *")
    Flux<Product> insertMany18();

    @Insert("INSERT INTO ${table} SELECT 118, 'name', 100.00, 10 RETURNING *")
    Single<List<Product>> insertMany19();

    @Insert("INSERT INTO ${table} SELECT 119, 'name', 100.00, 10 RETURNING *")
    Flowable<Product> insertMany20();

    @Insert("INSERT INTO ${table} SELECT 120, 'name', 100.00, 10 RETURNING *")
    CompletableFuture<List<Product>> insertMany21();

    @Insert("INSERT INTO ${table} SELECT 121, 'name', 100.00, 10 RETURNING *")
    CompletionStage<List<Product>> insertMany22();
}
