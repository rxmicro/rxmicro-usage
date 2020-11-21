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

package io.rxmicro.examples.testing.microservice.alternatives.cdi;

import io.rxmicro.cdi.Inject;
import io.rxmicro.rest.method.PATCH;

import java.util.concurrent.CompletableFuture;

import static io.rxmicro.common.util.Formats.format;

// tag::content[]
public final class MicroService {

    // <1>
    @Inject
    private BusinessService businessService;

    // <2>
    @Inject
    private BusinessServiceImpl businessServiceImpl;

    @PATCH("/")
    CompletableFuture<Void> handle() {
        final CompletableFuture<String> result1 = businessService.doSomething();        // <3>
        final CompletableFuture<String> result2 = businessServiceImpl.doSomething();    // <4>

        return CompletableFuture.allOf(result1, result2)
                .whenComplete((nothing, throwable) ->
                        System.out.println(
                                format("{?,?}", result1.join(), result2.join())// <5>
                        )
                );
    }
}
// end::content[]
