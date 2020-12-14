/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.examples.processor.proxy.server;

import io.rxmicro.examples.processor.proxy.model.CustomErrorException;
import io.rxmicro.examples.processor.proxy.model.Request;
import io.rxmicro.examples.processor.proxy.model.Response;
import io.rxmicro.rest.method.PUT;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.concurrent.CompletableFuture.failedFuture;

public final class RestControllerProxy {

    @PUT("/")
    CompletableFuture<Response> handle(final Request request) {
        if ("error".equals(request.getParam())) {
            return failedFuture(new CustomErrorException("custom error"));
        } else {
            return completedFuture(new Response(request.getHeader(), request.getParam()));
        }
    }
}

