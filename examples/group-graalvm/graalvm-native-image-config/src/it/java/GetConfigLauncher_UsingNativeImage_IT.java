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

import io.rxmicro.test.SystemOut;
import io.rxmicro.test.TestedProcessBuilder;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

import static io.rxmicro.common.util.Formats.format;
import static java.util.Map.entry;
import static java.util.function.Function.identity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RxMicroIntegrationTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class GetConfigLauncher_UsingNativeImage_IT {

    private SystemOut systemOut;

    @Order(1)
    @Test
    void Should_read_SecretsConfig_correctly() throws IOException, InterruptedException {
        final Map<String, String> systemProperties = Map.of(
                "secrets.regex", ",",
                "secrets.values", "secret1,secret2,secret3"
        );
        exec("SecretsConfig", systemProperties);

        final String out = systemOut.asString();
        assertRequestMessageExists(out, "io.rxmicro.config.SecretsConfig.regex = ,");
        assertRequestMessageExists(out, "io.rxmicro.config.SecretsConfig.values = secret1,secret2,secret3");
    }

    @Order(2)
    @Test
    void Should_read_MongoConfig_correctly() throws IOException, InterruptedException {
        final Map<String, String> systemProperties = Map.of(
                "mongo.database", "examples",
                "mongo.host", "10.10.10.10",
                "mongo.port", "1234"
        );
        exec("MongoConfig", systemProperties);

        final String out = systemOut.asString();
        assertRequestMessageExists(out, "io.rxmicro.data.mongo.MongoConfig.database = examples");
        assertRequestMessageExists(out, "io.rxmicro.data.mongo.MongoConfig.connectionString = mongodb://10.10.10.10:1234");
    }

    @Order(3)
    @Test
    void Should_read_PostgreSQLConfig_correctly() throws IOException, InterruptedException {
        final Map<String, String> systemProperties = Map.ofEntries(
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
        exec("PostgreSQLConfig", systemProperties);

        final String out = systemOut.asString();
        assertRequestMessageExists(
                out,
                "io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.connectionString = r2dbc:postgresql://10.10.10.10:1234/database"
        );
        assertRequestMessageExists(
                out,
                "io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.acquireRetry = 5"
        );
        assertRequestMessageExists(
                out,
                "io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.initialSize = 10"
        );
        assertRequestMessageExists(
                out,
                "io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.maxSize = 20"
        );
        assertRequestMessageExists(
                out,
                "io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.validationQuery = SELECT 1+1"
        );
        assertRequestMessageExists(
                out,
                "io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.maxIdleTime = PT1H"
        );
        assertRequestMessageExists(
                out,
                "io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.maxCreateConnectionTime = PT5S"
        );
        assertRequestMessageExists(
                out,
                "io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.maxAcquireTime = PT6S"
        );
        assertRequestMessageExists(
                out,
                "io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.maxLifeTime = PT7S"
        );
        assertRequestMessageExists(
                out,
                "io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.password = password"
        );
        assertRequestMessageExists(
                out,
                "io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.database = database"
        );
        assertRequestMessageExists(
                out,
                "io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.user = user"
        );
        assertRequestMessageExists(
                out,
                "io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.options = Optional[{k1=v1, k2=v2}]"
        );
        assertRequestMessageExists(
                out,
                "io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.connectTimeout = PT30S"
        );
        assertRequestMessageExists(
                out,
                "io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.port = 1234"
        );
        assertRequestMessageExists(
                out,
                "io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLConfig.host = 10.10.10.10"
        );
    }

    @Order(4)
    @Test
    void Should_read_RestClientConfig_correctly() throws IOException, InterruptedException {
        final Map<String, String> systemProperties = Map.ofEntries(
                entry("rest-client.enableAdditionalValidations", "true"),
                entry("rest-client.accessKey", "accessKey"),
                entry("rest-client.followRedirects", "false"),
                entry("rest-client.requestTimeout", "PT10S"),
                entry("rest-client.connectionString", "https://10.10.10.10:8443"),
                entry("rest-client.schema", "HTTPS"),
                entry("rest-client.port", "8443"),
                entry("rest-client.host", "10.10.10.10")
        );
        exec("RestClientConfig", systemProperties);

        final String out = systemOut.asString();
        assertRequestMessageExists(out, "io.rxmicro.rest.client.RestClientConfig.enableAdditionalValidations = true");
        assertRequestMessageExists(out, "io.rxmicro.rest.client.RestClientConfig.accessKey = accessKey");
        assertRequestMessageExists(out, "io.rxmicro.rest.client.RestClientConfig.followRedirects = false");
        assertRequestMessageExists(out, "io.rxmicro.rest.client.RestClientConfig.requestTimeout = PT10S");
        assertRequestMessageExists(out, "io.rxmicro.rest.client.RestClientConfig.connectionString = https://10.10.10.10:8443");
        assertRequestMessageExists(out, "io.rxmicro.rest.client.RestClientConfig.schema = HTTPS");
        assertRequestMessageExists(out, "io.rxmicro.rest.client.RestClientConfig.port = 8443");
        assertRequestMessageExists(out, "io.rxmicro.rest.client.RestClientConfig.host = 10.10.10.10");
    }

    @Order(5)
    @Test
    void Should_read_HttpServerConfig_correctly() throws IOException, InterruptedException {
        final Map<String, String> systemProperties = Map.ofEntries(
                entry("http-server.startTimeTrackerEnabled", "false"),
                entry("http-server.schema", "HTTPS"),
                entry("http-server.connectionString", "https://10.10.10.10:8443"),
                entry("http-server.port", "8443"),
                entry("http-server.host", "10.10.10.10")
        );
        exec("HttpServerConfig", systemProperties);

        final String out = systemOut.asString();
        assertRequestMessageExists(out, "io.rxmicro.rest.server.HttpServerConfig.startTimeTrackerEnabled = false");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.HttpServerConfig.schema = HTTPS");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.HttpServerConfig.connectionString = https://10.10.10.10:8443");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.HttpServerConfig.port = 8443");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.HttpServerConfig.host = 10.10.10.10");
    }

    @Order(6)
    @Test
    void Should_read_RestServerConfig_correctly() throws IOException, InterruptedException {
        final Map<String, String> systemProperties = Map.ofEntries(
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
        exec("RestServerConfig", systemProperties);

        final String out = systemOut.asString();
        assertRequestMessageExists(out, "io.rxmicro.rest.server.RestServerConfig.handlerNotFoundErrorStatusCode = 404");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.RestServerConfig.handlerNotFoundErrorMessage = No handler");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.RestServerConfig.corsNotAllowedErrorStatusCode = 400");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.RestServerConfig.corsNotAllowedErrorMessage = No CORS");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.RestServerConfig.humanReadableOutput = true");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.RestServerConfig.hideInternalErrorMessage = false");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.RestServerConfig.logHttpErrorExceptions = false");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.RestServerConfig.staticResponseHeaders = [SERVER]");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.RestServerConfig.requestIdGenerator = CustomRequestIdGenerator");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.RestServerConfig.returnGeneratedRequestId = false");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.RestServerConfig.disableLoggerMessagesForHttpHealthChecks = false");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.RestServerConfig.showRuntimeEnv = true");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.RestServerConfig.useFullClassNamesForRouterMappingLogMessages = false");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.RestServerConfig.enableAdditionalValidations = true");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.RestServerConfig.requestIdGeneratorInitTimeout = PT15M");
    }

    @Order(7)
    @Test
    void Should_read_NettyRuntimeConfig_correctly() throws IOException, InterruptedException {
        final Map<String, String> systemProperties = Map.ofEntries(
                entry("netty-runtime.shareWorkerThreads", "true"),
                entry("netty-runtime.acceptorThreadCount", "2"),
                entry("netty-runtime.acceptorThreadNameCategory", "acceptor1"),
                entry("netty-runtime.acceptorThreadPriority", "2"),
                entry("netty-runtime.workerThreadCount", "4"),
                entry("netty-runtime.workerThreadNameCategory", "worker1"),
                entry("netty-runtime.workerThreadPriority", "4"),
                entry("netty-runtime.channelIdType", "@io.rxmicro.netty.runtime.PredefinedNettyChannelIdType:LONG")
        );
        exec("NettyRuntimeConfig", systemProperties);

        final String out = systemOut.asString();
        assertRequestMessageExists(out, "io.rxmicro.netty.runtime.NettyRuntimeConfig.shareWorkerThreads = true");
        assertRequestMessageExists(out, "io.rxmicro.netty.runtime.NettyRuntimeConfig.acceptorThreadCount = 2");
        assertRequestMessageExists(out, "io.rxmicro.netty.runtime.NettyRuntimeConfig.acceptorThreadNameCategory = acceptor1");
        assertRequestMessageExists(out, "io.rxmicro.netty.runtime.NettyRuntimeConfig.acceptorThreadPriority = 2");
        assertRequestMessageExists(out, "io.rxmicro.netty.runtime.NettyRuntimeConfig.workerThreadCount = 4");
        assertRequestMessageExists(out, "io.rxmicro.netty.runtime.NettyRuntimeConfig.workerThreadNameCategory = worker1");
        assertRequestMessageExists(out, "io.rxmicro.netty.runtime.NettyRuntimeConfig.workerThreadPriority = 4");
        assertRequestMessageExists(out, "io.rxmicro.netty.runtime.NettyRuntimeConfig.channelIdType = LONG");
    }

    @Order(8)
    @Test
    void Should_read_NettyRestServerConfig_correctly() throws IOException, InterruptedException {
        final Map<String, String> systemProperties = Map.ofEntries(
                entry("netty-rest-server.maxHttpRequestInitialLineLength", "2"),
                entry("netty-rest-server.maxHttpRequestHeaderSize", "3"),
                entry("netty-rest-server.maxHttpRequestChunkSize", "4"),
                entry("netty-rest-server.validateHttpRequestHeaders", "true"),
                entry("netty-rest-server.initialHttpRequestBufferSize", "5"),
                entry("netty-rest-server.allowDuplicateHttpRequestContentLengths", "true"),
                entry("netty-rest-server.maxHttpRequestContentLength", "6"),
                entry("netty-rest-server.closeOnHttpRequestContentExpectationFailed", "false")
        );
        exec("NettyRestServerConfig", systemProperties);

        final String out = systemOut.asString();
        assertRequestMessageExists(out, "io.rxmicro.rest.server.netty.NettyRestServerConfig.maxHttpRequestInitialLineLength = 2");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.netty.NettyRestServerConfig.maxHttpRequestHeaderSize = 3");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.netty.NettyRestServerConfig.maxHttpRequestChunkSize = 4");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.netty.NettyRestServerConfig.validateHttpRequestHeaders = true");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.netty.NettyRestServerConfig.initialHttpRequestBufferSize = 5");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.netty.NettyRestServerConfig.allowDuplicateHttpRequestContentLengths = true");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.netty.NettyRestServerConfig.maxHttpRequestContentLength = 6");
        assertRequestMessageExists(out, "io.rxmicro.rest.server.netty.NettyRestServerConfig.closeOnHttpRequestContentExpectationFailed = false");
    }

    @Order(9)
    @Test
    void Should_read_custom_configs_correctly() throws IOException, InterruptedException {
        final Map<String, String> systemProperties = Map.of(
                "custom.value", "customValue",
                "dynamic.host", "10.10.10.10",
                "dynamic.port", "1234"
        );
        exec("CustomConfigs", systemProperties);

        final String out = systemOut.asString();
        assertRequestMessageExists(
                out,
                "io.rxmicro.examples.graalvm.nativeimage.config.config.CustomConfig.value = customValue"
        );
        assertRequestMessageExists(
                out,
                "io.rxmicro.examples.graalvm.nativeimage.config.config.DynamicConfig = DynamicConfig{map={host=10.10.10.10, port=1234}}"
        );
    }

    private void exec(final String argument,
                      final Map<String, String> systemProperties) throws InterruptedException, IOException {
        final String[] arguments =
                Stream.of(
                        Stream.of("./GetConfigLauncher"),
                        systemProperties.entrySet().stream().map(e -> format("-D?=?", e.getKey(), e.getValue())),
                        Stream.of("-DRX_MICRO_RUNTIME_STRICT_MODE=true"),
                        Stream.of(argument)
                ).flatMap(identity()).toArray(String[]::new);
        System.out.println(String.join(" ", arguments));
        final Process process = new TestedProcessBuilder()
                .setCommandWithArgs(arguments)
                .setRedirectStdOutAndStdErrToSysOut(true)
                .setWorkingDir(new File("."))
                .start();
        final int result = process.waitFor();
        assertEquals(0, result, "Invalid exit code");
    }

    private void assertRequestMessageExists(final String out,
                                            final String requiredMessage) {
        assertTrue(
                out.contains(requiredMessage),
                format("Console out does not contain required message: '?'. Full out is \n?", requiredMessage, out)
        );
    }
}

