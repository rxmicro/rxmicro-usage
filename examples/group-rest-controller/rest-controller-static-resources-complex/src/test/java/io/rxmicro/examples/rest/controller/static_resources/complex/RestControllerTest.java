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

package io.rxmicro.examples.rest.controller.static_resources.complex;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.server.HttpServerConfig;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.ClientHttpResponse;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;

import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.http.HttpStandardHeaderNames.IF_MODIFIED_SINCE;
import static io.rxmicro.test.json.JsonFactory.jsonErrorObject;
import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.createSymbolicLink;
import static java.nio.file.Files.createTempDirectory;
import static java.nio.file.Files.delete;
import static java.nio.file.Files.getLastModifiedTime;
import static java.nio.file.Files.walkFileTree;
import static java.nio.file.Files.writeString;
import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RxMicroRestBasedMicroServiceTest(RestController.class)
final class RestControllerTest {

    private BlockingHttpClient blockingHttpClient;

    private static Path tempRootPath;

    @BeforeAll
    static void beforeAll() throws IOException {
        tempRootPath = createTempDirectory("static-resources-complex-microservice-test");

        // @StaticResources(urls = "/custom1", filePaths = "custom.txt")
        // @StaticResources(urls = "/custom2", filePaths = "custom.txt")
        writeString(tempRootPath.resolve("custom.txt"), "custom.txt");

        // @StaticResources("/static*")
        writeString(tempRootPath.resolve("static-nested-file.log"), "/static*");
        writeString(tempRootPath.resolve("static-file.txt"), "/static*");
        writeString(tempRootPath.resolve("static.txt"), "/static*");
        writeString(tempRootPath.resolve("static.log"), "/static*");

        // @StaticResources("*.txt")
        writeString(tempRootPath.resolve("any-txt-file.txt"), "*.txt");
        writeString(tempRootPath.resolve("0.txt"), "*.txt");

        // @StaticResources("/static/**")
        createDirectories(tempRootPath.resolve("static/nested"));
        writeString(tempRootPath.resolve("static/nested/file.log"), "/static/**");
        writeString(tempRootPath.resolve("static/file.log"), "/static/**");
        writeString(tempRootPath.resolve("static/file.txt"), "/static/**");

        // @StaticResources("**.log")
        createDirectories(tempRootPath.resolve("static-dir/nested"));
        writeString(tempRootPath.resolve("static-dir/nested/file.log"), "**.log");
        writeString(tempRootPath.resolve("static-dir/file.log"), "**.log");
        createDirectories(tempRootPath.resolve("logs"));
        writeString(tempRootPath.resolve("logs/file.log"), "**.log");
        writeString(tempRootPath.resolve("file.log"), "**.log");

        // @StaticResources("/*")
        writeString(tempRootPath.resolve("test.text"), "/*");
        writeString(tempRootPath.resolve("custom.conf"), "/*");
        writeString(tempRootPath.resolve("test.list"), "/*");
        writeString(tempRootPath.resolve("test.urls"), "/*");

        // @StaticResources("/**")
        createDirectories(tempRootPath.resolve("parent/child"));
        writeString(tempRootPath.resolve("parent/child/file.txt"), "/**");
        createDirectories(tempRootPath.resolve("a/b/c/d/e/f/g/h/i/j/k"));
        writeString(tempRootPath.resolve("a/b/c/d/e/f/g/h/i/j/k/l.txt"), "/**");

        createSymbolicLink(tempRootPath.resolve("parent/child/link.txt"), tempRootPath.resolve("/dev/null"));

        getConfig(HttpServerConfig.class).setRootDirectory(tempRootPath);
    }

    @Test
    @Order(1)
    void Should_support_REST_handlers() {
        final ClientHttpResponse response = blockingHttpClient.get("/say-hello-world");

        assertEquals(jsonObject("message", "Hello world!"), response.getBody());
        assertEquals(200, response.getStatusCode());
    }

    @ParameterizedTest
    @Order(2)
    @CsvSource({
            "/custom1,                      custom.txt",
            "/custom2,                      custom.txt",

            "/static-nested-file.log,       /static*",
            "/static-file.txt,              /static*",
            "/static.txt,                   /static*",
            "/static.log,                   /static*",

            "/any-txt-file.txt,             *.txt",
            "/0.txt,                        *.txt",

            "/static/nested/file.log,       /static/**",
            "/static/file.log,              /static/**",
            "/static/file.txt,              /static/**",

            "/static-dir/nested/file.log,   **.log",
            "/static-dir/file.log,          **.log",
            "/logs/file.log,                **.log",
            "/file.log,                     **.log",

            "test.text,                     /*",
            "custom.conf,                   /*",
            "test.list,                     /*",
            "test.urls,                     /*",

            "/parent/child/file.txt,        /**",
            "/a/b/c/d/e/f/g/h/i/j/k/l.txt,  /**"
    })
    void Should_send_file_correctly(final String url,
                                    final String expectedContent) {
        final ClientHttpResponse response = blockingHttpClient.get(url);
        assertEquals(expectedContent, response.getBodyAsUTF8String());
        assertEquals(200, response.getStatusCode());
    }

    @Test
    @Order(3)
    void Should_return_404_if_file_not_found() {
        final ClientHttpResponse response = blockingHttpClient.get("/parent/child/not_found.txt");
        assertEquals(
                jsonErrorObject("Resource 'HttpServerConfig.getRootDirectory() + /parent/child/not_found.txt' not found!"),
                response.getBody()
        );
        assertEquals(404, response.getStatusCode());
    }

    @Test
    @Order(4)
    void Should_return_404_if_path_is_directory() {
        final ClientHttpResponse response = blockingHttpClient.get("/parent/child");
        assertEquals(
                jsonErrorObject("Resource 'HttpServerConfig.getRootDirectory() + /parent/child' is directory!"),
                response.getBody()
        );
        assertEquals(404, response.getStatusCode());
    }

    @Test
    @Order(5)
    void Should_return_403_if_path_is_not_file() {
        final ClientHttpResponse response = blockingHttpClient.get("/parent/child/link.txt");
        assertEquals(
                jsonErrorObject("Resource 'HttpServerConfig.getRootDirectory() + /parent/child/link.txt' is not file!"),
                response.getBody()
        );
        assertEquals(403, response.getStatusCode());
    }

    @Test
    @Order(6)
    void Should_return_304_if_file_not_modified() throws IOException {
        final FileTime lastModifiedTime = getLastModifiedTime(tempRootPath.resolve("test.text"));
        final String ifModifiedSinceValue = RFC_1123_DATE_TIME.format(lastModifiedTime.toInstant().atZone(ZoneId.of("UTC")));
        final ClientHttpResponse response = blockingHttpClient.get(
                "/test.text",
                HttpHeaders.of(
                        IF_MODIFIED_SINCE,
                        ifModifiedSinceValue
                ));
        assertEquals(304, response.getStatusCode());
        assertTrue(response.isBodyEmpty(), () -> "Response body is not empty: " + response.getBodyAsUTF8String());
    }

    @AfterAll
    static void afterAll() throws IOException {
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