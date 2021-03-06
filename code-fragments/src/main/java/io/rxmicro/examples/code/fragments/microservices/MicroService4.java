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

package io.rxmicro.examples.code.fragments.microservices;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.Instant;

import static java.nio.charset.StandardCharsets.UTF_8;

// tag::content[]
public final class MicroService4 {

    public static void main(final String[] args) throws IOException {
        final HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
        server.createContext("/now-instant", exchange -> {
            final String content = Instant.now().toString();
            exchange.sendResponseHeaders(200, content.length());
            exchange.getResponseHeaders().add("Content-Type", "text/txt");
            try (final OutputStream body = exchange.getResponseBody()) {
                body.write(content.getBytes(UTF_8));
            }
        });
        server.start();
    }
}
// end::content[]
