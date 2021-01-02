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

package io.rxmicro.examples.rest.controller.static_resources.basic;

import io.rxmicro.config.Configs;
import io.rxmicro.test.ClientHttpResponse;
import io.rxmicro.rest.server.HttpServerConfig;
import io.rxmicro.rest.server.ServerInstance;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.BlockingHttpClientSettings;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static io.rxmicro.rest.server.RxMicro.startRestServer;
import static io.rxmicro.test.HttpServers.getRandomFreePort;
import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.createTempDirectory;
import static java.nio.file.Files.delete;
import static java.nio.file.Files.walkFileTree;
import static java.nio.file.Files.writeString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RxMicroIntegrationTest
final class StaticResourcesTest {

    private static final int PORT = getRandomFreePort();

    private static ServerInstance serverInstance;

    private static Path tempRootPath;

    @BlockingHttpClientSettings(randomPortProvider = "PORT")
    private BlockingHttpClient blockingHttpClient;

    @BeforeAll
    static void beforeAll() throws IOException {
        tempRootPath = createTempDirectory("static-resources-basic-integration-test");
        createDirectories(tempRootPath.resolve("module"));
        createDirectories(tempRootPath.resolve("launcher"));
        createDirectories(tempRootPath.resolve("provider"));

        writeString(tempRootPath.resolve("module/resource1.txt"), "/module/resource1.txt");
        writeString(tempRootPath.resolve("module/resource2.txt"), "/module/resource2.txt");

        writeString(tempRootPath.resolve("launcher/resource1.txt"), "/launcher/resource1.txt");
        writeString(tempRootPath.resolve("launcher/resource2.txt"), "/launcher/resource2.txt");

        writeString(tempRootPath.resolve("provider/resource1.txt"), "/provider/resource1.txt");
        writeString(tempRootPath.resolve("provider/resource2.txt"), "/provider/resource2.txt");

        new Configs.Builder()
                .withConfigs(new HttpServerConfig()
                        .setPort(PORT)
                        .setRootDirectory(tempRootPath)
                )
                .build();
        serverInstance = startRestServer(Launcher.class.getPackageName());
    }

    @ParameterizedTest
    @Order(1)
    @ValueSource(strings = {
            "/module/resource1.txt",
            "/module/resource2.txt",

            "/launcher/resource1.txt",
            "/launcher/resource2.txt",

            "/provider/resource1.txt",
            "/provider/resource2.txt"
    })
    void Static_resources_should_be_accessible(final String url) {
        final ClientHttpResponse response = blockingHttpClient.get(url);

        assertEquals(url, response.getBodyAsUTF8String());
        assertEquals(200, response.getStatusCode());
    }

    @Test
    @Order(2)
    void Should_return_404(){
        final ClientHttpResponse response = blockingHttpClient.get("/not_found.txt");

        assertEquals("Resource 'HttpServerConfig.getRootDirectory() + /not_found.txt' not found!", response.getBodyAsUTF8String());
        assertEquals(404, response.getStatusCode());
    }

    @AfterAll
    static void afterAll() throws IOException {
        if (serverInstance != null) {
            serverInstance.shutdown();
        }
        if (tempRootPath != null) {
            walkFileTree(tempRootPath, new SimpleFileVisitor<>() {

                @Override
                public FileVisitResult visitFile(final Path file,
                                                 final BasicFileAttributes attrs) throws IOException {
                    delete(file);
                    return CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(final Path dir,
                                                          final IOException exc) throws IOException {
                    delete(dir);
                    return CONTINUE;
                }
            });
        }
    }
}