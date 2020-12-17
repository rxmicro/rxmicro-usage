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
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.reactor.error.AccountNotFoundException;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.reactor.error.NotEnoughFundsException;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.reactor.error.NotEnoughProductCountException;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.reactor.error.ProductNotFoundException;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.reactor.model.Account;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.reactor.model.Order;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.reactor.model.Product;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static io.rxmicro.data.RepositoryFactory.getRepository;

// tag::content[]
public final class ConcurrentBusinessService {

    private final ConcurrentRepository repository = getRepository(ConcurrentRepository.class);

    /**
     * @return order id if purchase is successful or
     *         error signal if:
     *                  - account not found or
     *                  - product not found or
     *                  - products ran out or
     *                  - money ran out
     */
    public Mono<Long> tryToBuy(final long idAccount,
                               final int idProduct,
                               final int count) {
        return repository.beginTransaction()
                .flatMap(transaction -> repository.findAccountById(transaction, idAccount)
                        .flatMap(account -> repository.findProductById(transaction, idProduct)
                                .flatMap(product ->
                                        tryToBuy(transaction, account, product, count))
                                .switchIfEmpty(Mono.error(() ->
                                        // product not found
                                        new ProductNotFoundException(idProduct))))
                        // account not found
                        .switchIfEmpty(Mono.error(() -> new AccountNotFoundException(idAccount)))
                        .onErrorResume(transaction.createRollbackThenReturnErrorFallback())
                );
    }

    private Mono<Long> tryToBuy(final Transaction transaction,
                                final Account account,
                                final Product product,
                                final int count) {
        if (count <= product.getCount()) {
            final BigDecimal cost = product.getPrice().multiply(BigDecimal.valueOf(count));
            if (cost.compareTo(account.getBalance()) <= 0) {
                return buy(transaction, account, product, count, cost);
            } else {
                // money ran out
                return Mono.error(new NotEnoughFundsException(cost, account.getBalance()));
            }
        } else {
            // products ran out
            return Mono.error(new NotEnoughProductCountException(count, product.getCount()));
        }
    }

    // purchase is successful, returns order id
    private Mono<Long> buy(final Transaction transaction,
                           final Account account,
                           final Product product,
                           final int count,
                           final BigDecimal cost) {
        final int newProductCount = product.getCount() - count;
        final BigDecimal newBalance = account.getBalance().subtract(cost);
        final Order order = new Order(account.getId(), product.getId(), count);

        return repository.updateProductCount(transaction, newProductCount, product.getId())
                .then(repository.updateAccountBalance(transaction, newBalance, account.getId())
                        .then(repository.createOrder(transaction, order)
                                .map(Order::getId)
                                .flatMap(id -> transaction.commit()
                                        .thenReturn(id))
                        )
                );
    }
}
// end::content[]
