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

package io.rxmicro.examples.data.r2dbc.postgresql.concurrent.rxjava3;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.rxmicro.data.sql.model.rxjava3.Transaction;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.rxjava3.error.AccountNotFoundException;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.rxjava3.error.NotEnoughFundsException;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.rxjava3.error.NotEnoughProductCountException;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.rxjava3.error.ProductNotFoundException;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.rxjava3.model.Account;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.rxjava3.model.Order;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.rxjava3.model.Product;

import java.math.BigDecimal;

import static io.rxmicro.data.RepositoryFactory.getRepository;

// tag::content[]
public final class ConcurrentBusinessService {

    private final ConcurrentRepository repository = getRepository(ConcurrentRepository.class);

    public Single<Long> tryToBuy(final long idAccount,
                                 final int idProduct,
                                 int count) {
        return repository.beginTransaction()
                .flatMapMaybe(transaction -> repository.findAccountById(transaction, idAccount)
                        .flatMap(account -> repository.findProductById(transaction, idProduct)
                                .flatMap(product ->
                                        tryToBuy(transaction, account, product, count).toMaybe())
                                .switchIfEmpty(Maybe.error(() ->
                                        new ProductNotFoundException(idProduct)))
                        )
                        .switchIfEmpty(Maybe.error(() ->
                                new AccountNotFoundException(idAccount)))
                        .onErrorResumeNext(transaction.createRollbackThenReturnMaybeErrorFallback())
                )
                .toSingle();
    }

    private Single<Long> tryToBuy(final Transaction transaction,
                                  final Account account,
                                  final Product product,
                                  final int count) {
        if (count <= product.getCount()) {
            final BigDecimal cost = product.getPrice().multiply(BigDecimal.valueOf(count));
            if (cost.compareTo(account.getBalance()) <= 0) {
                return buy(transaction, account, product, count, cost);
            } else {
                return Single.error(new NotEnoughFundsException(cost, account.getBalance()));
            }
        } else {
            return Single.error(new NotEnoughProductCountException(count, product.getCount()));
        }
    }

    private Single<Long> buy(final Transaction transaction,
                             final Account account,
                             final Product product,
                             final int count,
                             final BigDecimal cost) {
        final int newProductCount = product.getCount() - count;
        final BigDecimal newBalance = account.getBalance().subtract(cost);
        final Order order = new Order(account.getId(), product.getId(), count);

        return repository.updateProductCount(transaction, newProductCount, product.getId())
                .andThen(repository.updateAccountBalance(transaction, newBalance, account.getId())
                        .andThen(repository.createOrder(transaction, order)
                                .map(Order::getId)
                                .flatMap(id -> transaction.commit()
                                        .andThen(Single.just(id)))
                        )
                );
    }
}
// end::content[]
