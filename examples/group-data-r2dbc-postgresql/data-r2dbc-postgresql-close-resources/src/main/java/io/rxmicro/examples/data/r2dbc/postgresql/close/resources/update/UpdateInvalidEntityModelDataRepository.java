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
import io.rxmicro.data.sql.VariableValues;
import io.rxmicro.data.sql.operation.Update;
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
public interface UpdateInvalidEntityModelDataRepository {

    @Update("UPDATE ${table} SET count = 0 WHERE id = 13 RETURNING *")
    Mono<ProductWithInvalidColumnType> updateOne13();

    @Update("UPDATE ${table} SET count = 0 WHERE id = 14 RETURNING *")
    Maybe<ProductWithInvalidColumnType> updateOne14();

    @Update("UPDATE ${table} SET count = 0 WHERE id = 15 RETURNING *")
    CompletableFuture<Optional<ProductWithInvalidColumnType>> updateOne15();

    @Update("UPDATE ${table} SET count = 0 WHERE id = 16 RETURNING *")
    CompletionStage<Optional<ProductWithInvalidColumnType>> updateOne16();

    // -----------------------------------------------------------------------------------------------------------------

    @Update("UPDATE ${table} SET count = 0 WHERE id = 17 RETURNING *")
    Mono<List<ProductWithInvalidColumnType>> updateMany17();

    @Update("UPDATE ${table} SET count = 0 WHERE id = 18 RETURNING *")
    Flux<ProductWithInvalidColumnType> updateMany18();

    @Update("UPDATE ${table} SET count = 0 WHERE id = 19 RETURNING *")
    Single<List<ProductWithInvalidColumnType>> updateMany19();

    @Update("UPDATE ${table} SET count = 0 WHERE id = 20 RETURNING *")
    Flowable<ProductWithInvalidColumnType> updateMany20();

    @Update("UPDATE ${table} SET count = 0 WHERE id = 21 RETURNING *")
    CompletableFuture<List<ProductWithInvalidColumnType>> updateMany21();

    @Update("UPDATE ${table} SET count = 0 WHERE id = 22 RETURNING *")
    CompletionStage<List<ProductWithInvalidColumnType>> updateMany22();
}
