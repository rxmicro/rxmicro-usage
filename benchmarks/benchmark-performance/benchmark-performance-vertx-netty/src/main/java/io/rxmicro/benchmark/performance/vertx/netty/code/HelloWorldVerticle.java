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

package io.rxmicro.benchmark.performance.vertx.netty.code;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;

@SuppressWarnings("deprecation")
public class HelloWorldVerticle extends AbstractVerticle {

    private HttpServer server;

    @Override
    public void start(final Future<Void> startFuture) {
        server = vertx.createHttpServer().requestHandler(req -> req.response()
                .putHeader("content-type", "application/json")
                .end(JsonObject.mapFrom(new Response("Hello World")).toString())
        );
        server.listen(8080, res -> {
            if (res.succeeded()) {
                startFuture.complete();
            } else {
                startFuture.fail(res.cause());
            }
        });
    }

    @Override
    public void stop(final Future<Void> stopFuture) {
        server.close(res -> {
            if (res.succeeded()) {
                stopFuture.complete();
            } else {
                stopFuture.fail(res.cause());
            }
        });
    }
}
