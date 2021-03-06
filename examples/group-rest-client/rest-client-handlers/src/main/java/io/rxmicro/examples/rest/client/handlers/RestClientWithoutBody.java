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

package io.rxmicro.examples.rest.client.handlers;

import io.reactivex.rxjava3.core.Completable;
import io.rxmicro.examples.rest.client.handlers.model.Request;
import io.rxmicro.rest.client.RestClient;
import io.rxmicro.rest.method.GET;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

// tag::content[]
@RestClient
public interface RestClientWithoutBody {

    // <1>

    @GET("/jse/completedFuture1")
    CompletableFuture<Void> completedFuture1();

    @GET("/jse/completedFuture2")
    CompletableFuture<Void> completedFuture2(final Request request);

    @GET("/jse/completedFuture3")
    CompletableFuture<Void> completedFuture3(final String requestParameter);

    // <2>

    @GET("/jse/completionStage1")
    CompletionStage<Void> completionStage1();

    @GET("/jse/completionStage2")
    CompletionStage<Void> completionStage2(final Request request);

    @GET("/jse/completionStage3")
    CompletionStage<Void> completionStage3(final String requestParameter);

    // <3>

    @GET("/spring-reactor/mono1")
    Mono<Void> mono1();

    @GET("/spring-reactor/mono2")
    Mono<Void> mono2(final Request request);

    @GET("/spring-reactor/mono3")
    Mono<Void> mono3(final String requestParameter);

    // <4>

    @GET("/rxjava3/completable1")
    Completable completable1();

    @GET("/rxjava3/completable2")
    Completable completable2(final Request request);

    @GET("/rxjava3/completable3")
    Completable completable3(final String requestParameter);
}
// end::content[]