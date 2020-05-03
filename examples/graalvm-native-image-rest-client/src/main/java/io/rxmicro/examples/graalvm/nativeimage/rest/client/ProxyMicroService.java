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

package io.rxmicro.examples.graalvm.nativeimage.rest.client;

import io.rxmicro.examples.graalvm.nativeimage.rest.client.component.ExternalMicroService;
import io.rxmicro.examples.graalvm.nativeimage.rest.client.model.Model;
import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.server.RxMicro;

import java.util.concurrent.CompletableFuture;

import static io.rxmicro.rest.client.RestClientFactory.getRestClient;

public final class ProxyMicroService {

    private final ExternalMicroService externalMicroService =
            getRestClient(ExternalMicroService.class);

    @GET("/")
    CompletableFuture<Model> get() {
        return externalMicroService.getMessage();
    }

    public static void main(final String[] args) {
        RxMicro.startRestServer(ProxyMicroService.class);
    }
}
