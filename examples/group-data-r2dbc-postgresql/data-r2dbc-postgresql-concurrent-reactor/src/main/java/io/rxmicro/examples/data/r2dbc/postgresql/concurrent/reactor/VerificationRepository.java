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

package io.rxmicro.examples.data.r2dbc.postgresql.concurrent.reactor;

import io.rxmicro.data.sql.VariableValues;
import io.rxmicro.data.sql.operation.Select;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.reactor.model.Account;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.reactor.model.Order;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.reactor.model.Product;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

// tag::content[]
@PostgreSQLRepository
public interface VerificationRepository {

    @Select(value = "SELECT COALESCE(SUM(balance), 0) FROM ${table}", entityClass = Account.class)
    Mono<BigDecimal> getTotalBalance();

    @Select(value = "SELECT COALESCE(SUM(count), 0) FROM ${table}", entityClass = Product.class)
    Mono<Integer> getTotalProductCount();

    @Select("SELECT COALESCE(SUM(p.price * o.count), 0) FROM ${order-table} o, ${product-table} p WHERE o.id_product = p.id")
    @VariableValues({
            "${order-table}", "\"order\"",
            "${product-table}", "product"
    })
    Mono<BigDecimal> getTotalOrderCost();

    @Select(value = "SELECT COALESCE(SUM(count), 0) FROM ${table}", entityClass = Order.class)
    Mono<Integer> getTotalOrderedProductCount();
}
// end::content[]
