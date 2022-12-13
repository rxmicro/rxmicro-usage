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

module rxmicro.examples.util {
    requires rxmicro.common;
    requires rxmicro.json;
    requires org.apache.commons.io;
    requires org.jsoup;
    requires java.net.http;
    requires rxmicro.data.mongo;
    requires rxmicro.data.sql.r2dbc.postgresql;
    requires rxmicro.rest.server.netty;
    requires rxmicro.rest.client.jdk;
    requires io.netty.transport.classes.epoll;
    requires io.netty.transport.classes.kqueue;
    requires io.netty.common;
}