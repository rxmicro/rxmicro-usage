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

package io.rxmicro.examples.graalvm.nativeimage.config;

import io.rxmicro.config.Configs;
import io.rxmicro.config.SecretsConfig;
import io.rxmicro.data.mongo.MongoConfig;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig;
import io.rxmicro.examples.graalvm.nativeimage.config.config.CustomConfig;
import io.rxmicro.examples.graalvm.nativeimage.config.config.DynamicConfig;
import io.rxmicro.rest.client.RestClientConfig;
import io.rxmicro.rest.server.HttpServerConfig;
import io.rxmicro.rest.server.RestServerConfig;
import io.rxmicro.rest.server.netty.NettyRestServerConfig;

import static io.rxmicro.config.Configs.getConfig;

public class GetConfigLauncher {

    public static void main(final String[] args) {
        new Configs.Builder().build();

        if (args.length == 0) {
            getSecretsConfig();
            getMongoConfig();
            getPostgreSQLConfig();
            getRestClientConfig();
            getHttpServerConfig();
            getRestServerConfig();
            getNettyRestServerConfig();
            getCustomConfigs();
        } else if ("SecretsConfig".equals(args[0])) {
            getSecretsConfig();
        } else if ("MongoConfig".equals(args[0])) {
            getMongoConfig();
        } else if ("PostgreSQLConfig".equals(args[0])) {
            getPostgreSQLConfig();
        } else if ("RestClientConfig".equals(args[0])) {
            getRestClientConfig();
        } else if ("HttpServerConfig".equals(args[0])) {
            getHttpServerConfig();
        } else if ("RestServerConfig".equals(args[0])) {
            getRestServerConfig();
        } else if ("NettyRestServerConfig".equals(args[0])) {
            getNettyRestServerConfig();
        } else if ("CustomConfigs".equals(args[0])) {
            getCustomConfigs();
        } else {
            throw new IllegalArgumentException("Unsupported argument: " + args[0]);
        }
    }

    private static void getSecretsConfig() {
        final SecretsConfig secretsConfig = getConfig(SecretsConfig.class);
        System.out.println("io.rxmicro.config.SecretsConfig.regex = " + secretsConfig.getRegex());
        System.out.println("io.rxmicro.config.SecretsConfig.values = " + secretsConfig.getValues());
        System.out.println();
    }

    private static void getMongoConfig() {
        final MongoConfig mongoConfig = getConfig(MongoConfig.class);
        System.out.println("io.rxmicro.data.mongo.MongoConfig.database = " + mongoConfig.getDatabase());
        System.out.println("io.rxmicro.data.mongo.MongoConfig.connectionString = " + mongoConfig.getConnectionString());
        System.out.println();
    }

    private static void getPostgreSQLConfig() {
        final PostgreSQLConfig postgreSQLConfig = getConfig(PostgreSQLConfig.class);
        System.out.println("io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.connectionString = " + postgreSQLConfig.getConnectionString());
        System.out.println("io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.acquireRetry = " + postgreSQLConfig.getAcquireRetry());
        System.out.println("io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.initialSize = " + postgreSQLConfig.getInitialSize());
        System.out.println("io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.maxSize = " + postgreSQLConfig.getMaxSize());
        System.out.println("io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.validationQuery = " + postgreSQLConfig.getValidationQuery());
        System.out.println("io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.maxIdleTime = " + postgreSQLConfig.getMaxIdleTime());
        System.out.println("io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.maxCreateConnectionTime = " + postgreSQLConfig.getMaxCreateConnectionTime());
        System.out.println("io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.maxAcquireTime = " + postgreSQLConfig.getMaxAcquireTime());
        System.out.println("io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.maxLifeTime = " + postgreSQLConfig.getMaxLifeTime());
        System.out.println("io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.password = " + postgreSQLConfig.getPassword());
        System.out.println("io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.database = " + postgreSQLConfig.getDatabase());
        System.out.println("io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.user = " + postgreSQLConfig.getUser());
        System.out.println("io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.options = " + postgreSQLConfig.getOptions());
        System.out.println("io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.connectTimeout = " + postgreSQLConfig.getConnectTimeout());
        System.out.println("io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.port = " + postgreSQLConfig.getPort());
        System.out.println("io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.host = " + postgreSQLConfig.getHost());
        System.out.println();
    }

    private static void getRestClientConfig() {
        final RestClientConfig restClientConfig = getConfig(RestClientConfig.class);
        System.out.println("io.rxmicro.rest.client.RestClientConfig.enableAdditionalValidations = " + restClientConfig.isEnableAdditionalValidations());
        System.out.println("io.rxmicro.rest.client.RestClientConfig.accessKey = " + restClientConfig.getAccessKey());
        System.out.println("io.rxmicro.rest.client.RestClientConfig.followRedirects = " + restClientConfig.isFollowRedirects());
        System.out.println("io.rxmicro.rest.client.RestClientConfig.requestTimeout = " + restClientConfig.getRequestTimeout());
        System.out.println("io.rxmicro.rest.client.RestClientConfig.connectionString = " + restClientConfig.getConnectionString());
        System.out.println("io.rxmicro.rest.client.RestClientConfig.schema = " + restClientConfig.getSchema());
        System.out.println("io.rxmicro.rest.client.RestClientConfig.port = " + restClientConfig.getPort());
        System.out.println("io.rxmicro.rest.client.RestClientConfig.host = " + restClientConfig.getHost());
        System.out.println();
    }

    private static void getHttpServerConfig() {
        final HttpServerConfig httpServerConfig = getConfig(HttpServerConfig.class);
        System.out.println("io.rxmicro.rest.server.HttpServerConfig.startTimeTrackerEnabled = " + httpServerConfig.isStartTimeTrackerEnabled());
        System.out.println("io.rxmicro.rest.server.HttpServerConfig.schema = " + httpServerConfig.getSchema());
        System.out.println("io.rxmicro.rest.server.HttpServerConfig.connectionString = " + httpServerConfig.getConnectionString());
        System.out.println("io.rxmicro.rest.server.HttpServerConfig.port = " + httpServerConfig.getPort());
        System.out.println("io.rxmicro.rest.server.HttpServerConfig.host = " + httpServerConfig.getHost());
        System.out.println();
    }

    private static void getRestServerConfig() {
        final RestServerConfig restServerConfig = getConfig(RestServerConfig.class);
        System.out.println("io.rxmicro.rest.server.RestServerConfig.handlerNotFoundErrorStatusCode = " + restServerConfig.getHandlerNotFoundErrorStatusCode());
        System.out.println("io.rxmicro.rest.server.RestServerConfig.handlerNotFoundErrorMessage = " + restServerConfig.getHandlerNotFoundErrorMessage());
        System.out.println("io.rxmicro.rest.server.RestServerConfig.corsNotAllowedErrorStatusCode = " + restServerConfig.getCorsNotAllowedErrorStatusCode());
        System.out.println("io.rxmicro.rest.server.RestServerConfig.corsNotAllowedErrorMessage = " + restServerConfig.getCorsNotAllowedErrorMessage());
        System.out.println("io.rxmicro.rest.server.RestServerConfig.humanReadableOutput = " + restServerConfig.isHumanReadableOutput());
        System.out.println("io.rxmicro.rest.server.RestServerConfig.hideInternalErrorMessage = " + restServerConfig.isHideInternalErrorMessage());
        System.out.println("io.rxmicro.rest.server.RestServerConfig.logHttpErrorExceptions = " + restServerConfig.isLogHttpErrorExceptions());
        System.out.println("io.rxmicro.rest.server.RestServerConfig.staticResponseHeaders = " + restServerConfig.getStaticResponseHeaders());
        System.out.println("io.rxmicro.rest.server.RestServerConfig.requestIdGenerator = " + restServerConfig.getRequestIdGenerator());
        System.out.println("io.rxmicro.rest.server.RestServerConfig.returnGeneratedRequestId = " + restServerConfig.isReturnGeneratedRequestId());
        System.out.println("io.rxmicro.rest.server.RestServerConfig.disableLoggerMessagesForHttpHealthChecks = " + restServerConfig.isDisableLoggerMessagesForHttpHealthChecks());
        System.out.println("io.rxmicro.rest.server.RestServerConfig.showRuntimeEnv = " + restServerConfig.isShowRuntimeEnv());
        System.out.println("io.rxmicro.rest.server.RestServerConfig.useFullClassNamesForRouterMappingLogMessages = " + restServerConfig.isUseFullClassNamesForRouterMappingLogMessages());
        System.out.println("io.rxmicro.rest.server.RestServerConfig.enableAdditionalValidations = " + restServerConfig.isEnableAdditionalValidations());
        System.out.println("io.rxmicro.rest.server.RestServerConfig.waitingForRequestIdGeneratorInitTimeoutInMillis = " + restServerConfig.getWaitingForRequestIdGeneratorInitTimeoutInMillis());
        System.out.println();
    }

    private static void getNettyRestServerConfig() {
        final NettyRestServerConfig nettyRestServerConfig = getConfig(NettyRestServerConfig.class);
        System.out.println("io.rxmicro.rest.server.netty.NettyRestServerConfig.transport = " + nettyRestServerConfig.getTransport());
        System.out.println("io.rxmicro.rest.server.netty.NettyRestServerConfig.channelIdType = " + nettyRestServerConfig.getChannelIdType());
        System.out.println();
    }

    private static void getCustomConfigs() {
        final CustomConfig customConfig = getConfig(CustomConfig.class);
        System.out.println("io.rxmicro.examples.graalvm.nativeimage.config.config.CustomConfig.value = " + customConfig.getValue());
        System.out.println();

        final DynamicConfig dynamicConfig = getConfig(DynamicConfig.class);
        System.out.println("io.rxmicro.examples.graalvm.nativeimage.config.config.DynamicConfig = " + dynamicConfig);
        System.out.println();
    }
}

