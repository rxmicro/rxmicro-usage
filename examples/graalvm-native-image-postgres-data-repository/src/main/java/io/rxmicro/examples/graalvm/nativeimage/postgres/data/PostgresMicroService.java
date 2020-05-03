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

package io.rxmicro.examples.graalvm.nativeimage.postgres.data;

import io.rxmicro.config.Configs;
import io.rxmicro.config.WaitFor;
import io.rxmicro.examples.graalvm.nativeimage.postgres.data.model.Response;
import io.rxmicro.examples.graalvm.nativeimage.postgres.data.repository.PostgresDataRepository;
import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.server.RxMicro;

import java.util.concurrent.CompletableFuture;

import static io.rxmicro.data.RepositoryFactory.getRepository;

/**
 * docker run -it --rm --name=postgres-test-db -p 5432:5432 rxmicro/postgres-test-db
 */
public class PostgresMicroService {

    private final PostgresDataRepository dataRepository =
            getRepository(PostgresDataRepository.class);

    @GET("/")
    CompletableFuture<Response> get() {
        return dataRepository.select().thenApply(Response::new);
    }

    public static void main(final String[] args) {
        new Configs.Builder()
                .withCommandLineArguments(args)
                .build();
        new WaitFor(args).start();
        RxMicro.startRestServer(PostgresMicroService.class);
    }
}
