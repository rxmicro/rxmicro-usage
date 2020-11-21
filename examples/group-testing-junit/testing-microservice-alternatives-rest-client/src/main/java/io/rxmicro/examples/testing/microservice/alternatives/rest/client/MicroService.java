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

package io.rxmicro.examples.testing.microservice.alternatives.rest.client;

import io.rxmicro.examples.testing.microservice.alternatives.rest.client.model.Response;
import io.rxmicro.rest.method.GET;

import java.util.concurrent.CompletableFuture;

import static io.rxmicro.rest.client.RestClientFactory.getRestClient;

// tag::content[]
final class MicroService {

    private final ExternalMicroService externalMicroService =
            getRestClient(ExternalMicroService.class);

    @GET("/")
    CompletableFuture<Response> get() {
        return externalMicroService.get();
    }
}
// end::content[]
