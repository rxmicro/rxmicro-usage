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

package io.rxmicro.examples.cdi.all.rxmicro.components.jee;

import com.mongodb.reactivestreams.client.MongoClient;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.spi.ConnectionFactory;
import io.rxmicro.cdi.Inject;
import io.rxmicro.cdi.Named;
import io.rxmicro.cdi.PostConstruct;
import io.rxmicro.data.mongo.MongoConfig;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig;
import io.rxmicro.examples.cdi.all.rxmicro.components.CustomPostgreSQLNamedConfig;
import io.rxmicro.examples.cdi.all.rxmicro.components.component.MongoRepository;
import io.rxmicro.examples.cdi.all.rxmicro.components.component.PostgreSQLRepository;
import io.rxmicro.examples.cdi.all.rxmicro.components.component.RestClient;
import io.rxmicro.http.client.HttpClientConfig;
import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.server.HttpServerConfig;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.netty.NettyRestServerConfig;

@SuppressWarnings("EmptyMethod")
public final class InternalTypesJEEStylePrivateFieldInjectionRestController {

    @Inject
    private MongoRepository mongoRepository;

    @Inject
    private MongoConfig mongoConfig;

    @Inject
    @Named("custom-mongo")
    private MongoConfig customMongoConfig;

    @Inject
    private MongoClient mongoClient;

    @Inject
    @Named("custom-mongo")
    private MongoClient customMongoClient;

    @Inject
    private PostgreSQLRepository postgreSQLRepository;

    @Inject
    private PostgreSQLConfig postgreSQLConfig;

    @Inject
    @CustomPostgreSQLNamedConfig
    private PostgreSQLConfig customPostgreSQLConfig;

    @Inject
    private ConnectionFactory connectionFactory;

    @Inject
    private ConnectionPool connectionPool;

    @Inject
    @CustomPostgreSQLNamedConfig
    private ConnectionFactory customConnectionFactory;

    @Inject
    @CustomPostgreSQLNamedConfig
    private ConnectionPool customConnectionPool;

    @Inject
    private RestClient restClient;

    @Inject
    private HttpClientConfig httpClientConfig;

    @Inject
    @Named("custom-http-client")
    private HttpClientConfig customHttpClientConfig;

    @Inject
    private HttpServerConfig httpServerConfig;

    @Inject
    private RestServerConfig restServerConfig;

    @Inject
    private NettyRestServerConfig nettyRestServerConfig;

    @PostConstruct
    private void postConstruct() {

    }

    @GET("/jee/fields")
    void test() {

    }

}