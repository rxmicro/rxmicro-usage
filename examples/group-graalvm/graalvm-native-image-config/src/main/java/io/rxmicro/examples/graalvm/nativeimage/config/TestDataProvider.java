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

import java.util.Map;
import java.util.stream.Stream;

import static java.util.Map.entry;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

public final class TestDataProvider {

    public static final Map<String, String> SECRETS_SYSTEM_PROPERTIES = Map.ofEntries(
            entry("secrets.regex", ","),
            entry("secrets.values", "secret1,secret2,secret3")
    );

    public static final Map<String, String> MONGO_SYSTEM_PROPERTIES = Map.ofEntries(
            entry("mongo.database", "examples"),
            entry("mongo.host", "10.10.10.10"),
            entry("mongo.port", "1234")
    );

    public static final Map<String, String> POSTGRES_SYSTEM_PROPERTIES = Map.ofEntries(
            entry("postgre-sql.acquireRetry", "5"),
            entry("postgre-sql.initialSize", "10"),
            entry("postgre-sql.maxSize", "20"),
            entry("postgre-sql.validationQuery", "SELECT 1+1"),
            entry("postgre-sql.maxIdleTime", "PT60M"),
            entry("postgre-sql.maxCreateConnectionTime", "PT5S"),
            entry("postgre-sql.maxAcquireTime", "PT6S"),
            entry("postgre-sql.maxLifeTime", "PT7S"),
            entry("postgre-sql.password", "password"),
            entry("postgre-sql.database", "database"),
            entry("postgre-sql.user", "user"),
            entry("postgre-sql.options", "k1=v1,k2=v2"),
            entry("postgre-sql.connectTimeout", "PT30S"),
            entry("postgre-sql.port", "1234"),
            entry("postgre-sql.host", "10.10.10.10")
    );

    public static final Map<String, String> REST_CLIENT_SYSTEM_PROPERTIES = Map.ofEntries(
            entry("rest-client.enableAdditionalValidations", "true"),
            entry("rest-client.accessKey", "accessKey"),
            entry("rest-client.followRedirects", "false"),
            entry("rest-client.requestTimeout", "PT10S"),
            entry("rest-client.connectTimeout", "PT3S"),
            entry("rest-client.connectionString", "https://10.10.10.10:8443"),
            entry("rest-client.schema", "HTTPS"),
            entry("rest-client.port", "8443"),
            entry("rest-client.host", "10.10.10.10"),
            entry("rest-client.maxIdleTime", "PT10S"),
            entry("rest-client.maxLifeTime", "PT15S"),
            entry("rest-client.evictionInterval", "PT20S"),
            entry("rest-client.maxConnections", "12"),
            entry("rest-client.pendingAcquireMaxCount", "10"),
            entry("rest-client.pendingAcquireTimeout", "PT30S"),
            entry("rest-client.leasingStrategy", "LIFO")
    );

    public static final Map<String, String> HTTP_SERVER_SYSTEM_PROPERTIES = Map.ofEntries(
            entry("http-server.startTimeTrackerEnabled", "false"),
            entry("http-server.schema", "HTTPS"),
            entry("http-server.connectionString", "https://10.10.10.10:8443"),
            entry("http-server.port", "8443"),
            entry("http-server.host", "10.10.10.10")
    );

    public static final Map<String, String> REST_SERVER_SYSTEM_PROPERTIES = Map.ofEntries(
            entry("rest-server.handlerNotFoundErrorStatusCode", "404"),
            entry("rest-server.handlerNotFoundErrorMessage", "No handler"),
            entry("rest-server.requestIdGeneratorProvider", "@io.rxmicro.examples.graalvm.nativeimage.config.CustomRequestIdGeneratorProvider:CUSTOM"),
            entry("rest-server.corsNotAllowedErrorStatusCode", "400"),
            entry("rest-server.corsNotAllowedErrorMessage", "No CORS"),
            entry("rest-server.humanReadableOutput", "true"),
            entry("rest-server.hideInternalErrorMessage", "false"),
            entry("rest-server.logHttpErrorExceptions", "false"),
            entry("rest-server.staticResponseHeaders", "@io.rxmicro.rest.server.PredefinedStaticResponseHeader:SERVER"),
            entry("rest-server.returnGeneratedRequestId", "false"),
            entry("rest-server.disableLoggerMessagesForHttpHealthChecks", "false"),
            entry("rest-server.showRuntimeEnv", "true"),
            entry("rest-server.useFullClassNamesForRouterMappingLogMessages", "false"),
            entry("rest-server.enableAdditionalValidations", "true"),
            entry("rest-server.requestIdGeneratorInitTimeout", "PT15M")
    );

    public static final Map<String, String> NETTY_RUNTIME_SYSTEM_PROPERTIES = Map.ofEntries(
            entry("netty-runtime.shareWorkerThreads", "true"),
            entry("netty-runtime.acceptorThreadCount", "2"),
            entry("netty-runtime.acceptorThreadNameCategory", "acceptor1"),
            entry("netty-runtime.acceptorThreadPriority", "2"),
            entry("netty-runtime.workerThreadCount", "4"),
            entry("netty-runtime.workerThreadNameCategory", "worker1"),
            entry("netty-runtime.workerThreadPriority", "4"),
            entry("netty-runtime.channelIdType", "@io.rxmicro.netty.runtime.PredefinedNettyChannelIdType:LONG")
    );

    public static final Map<String, String> NETTY_REST_SERVER_SYSTEM_PROPERTIES = Map.ofEntries(
            entry("netty-rest-server.maxHttpRequestInitialLineLength", "2"),
            entry("netty-rest-server.maxHttpRequestHeaderSize", "3"),
            entry("netty-rest-server.maxHttpRequestChunkSize", "4"),
            entry("netty-rest-server.validateHttpRequestHeaders", "true"),
            entry("netty-rest-server.initialHttpRequestBufferSize", "5"),
            entry("netty-rest-server.allowDuplicateHttpRequestContentLengths", "true"),
            entry("netty-rest-server.maxHttpRequestContentLength", "6"),
            entry("netty-rest-server.closeOnHttpRequestContentExpectationFailed", "false")
    );

    public static final Map<String, String> CUSTOM_SYSTEM_PROPERTIES = Map.ofEntries(
            entry("custom.value", "customValue"),
            entry("dynamic.host", "10.10.10.10"),
            entry("dynamic.port", "1234")
    );

    public static final Map<String, String> ALL_SYSTEM_PROPERTIES = Stream.of(
            SECRETS_SYSTEM_PROPERTIES.entrySet().stream(),
            MONGO_SYSTEM_PROPERTIES.entrySet().stream(),
            POSTGRES_SYSTEM_PROPERTIES.entrySet().stream(),
            REST_CLIENT_SYSTEM_PROPERTIES.entrySet().stream(),
            HTTP_SERVER_SYSTEM_PROPERTIES.entrySet().stream(),
            REST_SERVER_SYSTEM_PROPERTIES.entrySet().stream(),
            NETTY_RUNTIME_SYSTEM_PROPERTIES.entrySet().stream(),
            NETTY_REST_SERVER_SYSTEM_PROPERTIES.entrySet().stream(),
            CUSTOM_SYSTEM_PROPERTIES.entrySet().stream()
    ).flatMap(identity()).collect(toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

    private TestDataProvider() {
    }
}

