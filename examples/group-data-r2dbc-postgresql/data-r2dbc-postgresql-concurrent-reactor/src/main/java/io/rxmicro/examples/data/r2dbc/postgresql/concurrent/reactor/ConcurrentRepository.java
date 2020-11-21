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

import io.rxmicro.data.sql.model.reactor.Transaction;
import io.rxmicro.data.sql.operation.Insert;
import io.rxmicro.data.sql.operation.Select;
import io.rxmicro.data.sql.operation.Update;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.reactor.model.Account;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.reactor.model.Order;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.reactor.model.Product;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

// tag::content[]
@PostgreSQLRepository
public interface ConcurrentRepository {

    Mono<Transaction> beginTransaction();

    @Select("SELECT * FROM ${table} WHERE id=? FOR UPDATE")
    Mono<Account> findAccountById(Transaction transaction, long id);

    @Select("SELECT * FROM ${table} WHERE id=? FOR UPDATE")
    Mono<Product> findProductById(Transaction transaction, int id);

    @Update(value = "UPDATE ${table} SET balance=? WHERE id=?", entityClass = Account.class)
    Mono<Void> updateAccountBalance(Transaction transaction, BigDecimal balance, long id);

    @Update(value = "UPDATE ${table} SET count=? WHERE id=?", entityClass = Product.class)
    Mono<Void> updateProductCount(Transaction transaction, int count, long id);

    @Insert
    Mono<Order> createOrder(Transaction transaction, Order order);
}
// end::content[]
