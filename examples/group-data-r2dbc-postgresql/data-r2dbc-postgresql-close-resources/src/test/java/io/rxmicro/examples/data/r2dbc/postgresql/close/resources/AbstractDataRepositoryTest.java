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

package io.rxmicro.examples.data.r2dbc.postgresql.close.resources;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.examples.data.r2dbc.postgresql.close.resources.Spies.verifyCloseConnection;
import static io.rxmicro.tool.common.Reflections.allMethods;
import static io.rxmicro.tool.common.Reflections.invokeMethod;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public abstract class AbstractDataRepositoryTest<T> {

    private final boolean expectedException;

    protected AbstractDataRepositoryTest(final boolean expectedException) {
        this.expectedException = expectedException;
    }

    protected abstract T getRepository();

    @TestFactory
    Stream<DynamicTest> shouldCloseConnectionAndReThrowError() {
        final Class<?> interfaceClass = getRepository().getClass().getInterfaces()[0];
        return allMethods(interfaceClass, m -> true).stream()
                .sorted(Comparator.comparing(Method::getName))
                .map(method -> {
                            final String methodName = format(
                                    "?.?",
                                    method.getDeclaringClass().getSimpleName(), method.getName()
                            );
                            return dynamicTest(
                                    format("test ?", methodName),
                                    () -> {
                                        if (expectedException) {
                                            final RuntimeException exception =
                                                    assertThrows(RuntimeException.class, () ->
                                                            blockingInvoke(getRepository(), method));
                                            System.out.println(format(
                                                    "RESULT: ? -> ? (?)",
                                                    methodName,
                                                    exception.getMessage(),
                                                    exception.getClass().getName()
                                            ));
                                        } else {
                                            final Object result =
                                                    assertDoesNotThrow(() ->
                                                            blockingInvoke(getRepository(), method));
                                            System.out.println(format(
                                                    "RESULT: ? -> ? (?)",
                                                    methodName,
                                                    result,
                                                    result != null ? result.getClass().getName() : "null"
                                            ));
                                        }
                                        verifyCloseConnection();
                                    });
                        }
                );
    }

    private Object blockingInvoke(final T repository,
                                  final Method method) {
        final Object result = invokeMethod(repository, method);
        if (result instanceof CompletionStage) {
            return ((CompletionStage<?>) result).toCompletableFuture().join();
        } else if (result instanceof Mono) {
            return ((Mono<?>) result).block();
        } else if (result instanceof Flux) {
            return ((Flux<?>) result).collectList().block();
        } else if (result instanceof Single) {
            return ((Single<?>) result).blockingGet();
        } else if (result instanceof Maybe) {
            return ((Maybe<?>) result).blockingGet();
        } else if (result instanceof Flowable) {
            return ((Flowable<?>) result).blockingStream().collect(toList());
        } else if (result instanceof Completable) {
            ((Completable) result).blockingAwait();
            return null;
        } else {
            throw new IllegalArgumentException(
                    "Unsupported reactive result: " + (result != null ? result.getClass() : null)
            );
        }
    }
}
