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

import io.rxmicro.config.DefaultConfigValue;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig;

@DefaultConfigValue(configClass = PostgreSQLConfig.class, name = "host", value = "localhost")
@DefaultConfigValue(configClass = PostgreSQLConfig.class, name = "port", value = "5432")
@DefaultConfigValue(configClass = PostgreSQLConfig.class, name = "user", value = "rxmicro")
@DefaultConfigValue(configClass = PostgreSQLConfig.class, name = "password", value = "password")
@DefaultConfigValue(configClass = PostgreSQLConfig.class, name = "database", value = "rxmicro")
module examples.monitoring.request.tracing {
    requires rxmicro.rest.server.netty;
    requires rxmicro.rest.server.exchange.json;
    requires rxmicro.data.sql.r2dbc.postgresql;
    requires rxmicro.cdi;
    requires reactor.core;

    opens io.rxmicro.examples.monitoring.request.tracing.model.db to
            rxmicro.common;
}