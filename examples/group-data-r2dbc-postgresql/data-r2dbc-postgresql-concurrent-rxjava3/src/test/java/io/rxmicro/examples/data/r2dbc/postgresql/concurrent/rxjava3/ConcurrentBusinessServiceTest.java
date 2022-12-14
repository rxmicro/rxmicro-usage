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

import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.rxjava3.error.AccountNotFoundException;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.rxjava3.error.NotEnoughFundsException;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.rxjava3.error.NotEnoughProductCountException;
import io.rxmicro.examples.data.r2dbc.postgresql.concurrent.rxjava3.error.ProductNotFoundException;
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.junit.RxMicroComponentTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.data.RepositoryFactory.getRepository;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("ResultOfMethodCallIgnored")
// tag::content[]
@Testcontainers
@RxMicroComponentTest(ConcurrentBusinessService.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class ConcurrentBusinessServiceTest {

    @Container
    private static final GenericContainer<?> POSTGRESQL_TEST_DB =
            new GenericContainer<>("rxmicro/postgres-test-db")
                    .withExposedPorts(5432);

    @WithConfig
    private static final PostgreSQLConfig CONFIG = new PostgreSQLConfig()
            .setDatabase("rxmicro")
            .setUser("rxmicro")
            .setPassword("password")
            .setMaxSize(3);

    @BeforeAll
    static void beforeAll() {
        POSTGRESQL_TEST_DB.start();
        CONFIG
                .setHost(POSTGRESQL_TEST_DB.getHost())
                .setPort(POSTGRESQL_TEST_DB.getFirstMappedPort());
    }

    private ConcurrentBusinessService service;

    @Test
    @Order(1)
    void Should_throw_AccountNotFoundException() {
        final AccountNotFoundException exception =
                assertThrows(AccountNotFoundException.class, () -> service.tryToBuy(0L, 0, 1).blockingGet());
        assertEquals("Account not found by id=0", exception.getMessage());
    }

    @Test
    @Order(2)
    void Should_throw_ProductNotFoundException() {
        final ProductNotFoundException exception =
                assertThrows(ProductNotFoundException.class, () -> service.tryToBuy(1L, 0, 1).blockingGet());
        assertEquals("Product not found by id=0", exception.getMessage());
    }

    @Test
    @Order(3)
    void Should_throw_NotEnoughFundsException() {
        final NotEnoughFundsException exception =
                assertThrows(NotEnoughFundsException.class, () -> service.tryToBuy(3L, 1, 10).blockingGet());
        assertEquals("Not enough funds to buy product: expected=57500.00, but actual=10000.00", exception.getMessage());
    }

    @Test
    @Order(4)
    void Should_throw_NotEnoughProductCountException() {
        final NotEnoughProductCountException exception =
                assertThrows(NotEnoughProductCountException.class, () -> service.tryToBuy(1L, 1, 1_000_000_000).blockingGet());
        assertEquals("Not enough product count: expected=1000000000, but actual=10", exception.getMessage());
    }

    @Test
    @Order(5)
    void Should_handle_concurrent_requests_correctly() throws ExecutionException, InterruptedException {
        final BeforeState beforeState = getBeforeState();
        final ExecutorService executorService = Executors.newFixedThreadPool(3);
        try {
            List<Future<?>> futures = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                final int idAccount = i + 1;
                futures.add(executorService.submit(() -> {
                    for (final int[] data : getProductMatrix()) {
                        final int idProduct = data[0];
                        final int count = data[1];
                        try {
                            final Long idOrder = service.tryToBuy(idAccount, idProduct, count).blockingGet();
                            System.out.println(format(
                                    "idAccount=?, idProduct=?, count=?, idOrder=?",
                                    idAccount, idProduct, count, idOrder
                            ));
                        } catch (final NotEnoughFundsException | NotEnoughProductCountException e) {
                            System.out.println(format(
                                    "idAccount=?, idProduct=?, count=?, error=?",
                                    idAccount, idProduct, count, e.getMessage()
                            ));
                            if (e instanceof NotEnoughProductCountException) {
                                break;
                            }
                        }
                    }
                }));
            }
            for (final Future<?> future : futures) {
                future.get();
            }
        } finally {
            executorService.shutdownNow();
        }

        verifyState(beforeState);
    }

    private BeforeState getBeforeState() {
        final VerificationRepository repository = getRepository(VerificationRepository.class);
        return new BeforeState(
                requireNonNull(repository.getTotalBalance().blockingGet()),
                requireNonNull(repository.getTotalProductCount().blockingGet())
        );
    }

    private int[][] getProductMatrix() {
        // idProduct -> product count
        final int[][] productMatrix = new int[100][2];
        final Random random = new Random();
        for (int i = 0; i < productMatrix.length; i++) {
            productMatrix[i][0] = random.nextInt(20) + 1;  // idProduct
            productMatrix[i][1] = random.nextInt(15) + 1;// product count
        }
        return productMatrix;
    }

    private void verifyState(final BeforeState beforeState) {
        final VerificationRepository repository = getRepository(VerificationRepository.class);
        final BigDecimal totalBalanceAfterBuying = requireNonNull(repository.getTotalBalance().blockingGet());
        final BigDecimal totalOrderCost = requireNonNull(repository.getTotalOrderCost().blockingGet());
        assertEquals(
                0,
                beforeState.totalBalance.compareTo(totalBalanceAfterBuying.add(totalOrderCost)),
                format(
                        "Invalid funds state: totalBalanceBeforeBuying=?, totalBalanceAfterBuying=?, totalOrderCost=?",
                        beforeState.totalBalance, totalBalanceAfterBuying, totalOrderCost
                )
        );

        final Integer totalProductCountAfterBuying = requireNonNull(repository.getTotalProductCount().blockingGet());
        final Integer totalOrderedProductCountAfterBuying = requireNonNull(repository.getTotalOrderedProductCount().blockingGet());
        assertEquals(
                beforeState.totalProductCount,
                totalProductCountAfterBuying + totalOrderedProductCountAfterBuying,
                format("Invalid product count state: totalProductCountBeforeBuying=?, totalProductCountAfterBuying=?, totalOrderedProductCount=?",
                        beforeState.totalProductCount, totalProductCountAfterBuying, totalOrderedProductCountAfterBuying
                )
        );
    }

    @AfterAll
    static void afterAll() {
        POSTGRESQL_TEST_DB.stop();
    }

    private static final class BeforeState {

        private final BigDecimal totalBalance;

        private final int totalProductCount;

        private BeforeState(final BigDecimal totalBalance,
                            final int totalProductCount) {
            this.totalBalance = totalBalance;
            this.totalProductCount = totalProductCount;
        }
    }
}
// end::content[]