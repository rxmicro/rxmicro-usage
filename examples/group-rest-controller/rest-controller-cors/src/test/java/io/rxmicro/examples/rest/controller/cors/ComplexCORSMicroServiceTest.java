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

package io.rxmicro.examples.rest.controller.cors;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.ClientHttpResponse;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.rxmicro.http.HttpStandardHeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS;
import static io.rxmicro.http.HttpStandardHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS;
import static io.rxmicro.http.HttpStandardHeaderNames.ACCESS_CONTROL_ALLOW_METHODS;
import static io.rxmicro.http.HttpStandardHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN;
import static io.rxmicro.http.HttpStandardHeaderNames.ACCESS_CONTROL_MAX_AGE;
import static io.rxmicro.http.HttpStandardHeaderNames.ACCESS_CONTROL_REQUEST_METHOD;
import static io.rxmicro.http.HttpStandardHeaderNames.ORIGIN;
import static io.rxmicro.http.HttpStandardHeaderNames.VARY;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RxMicroRestBasedMicroServiceTest(ComplexCORSMicroService.class)
final class ComplexCORSMicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    @ParameterizedTest
    @CsvSource({
            "http://localhost:8080,     GET",
            "http://localhost:8080,     HEAD",
            "http://localhost:8080,     POST",
            "http://localhost:8080,     PUT",
            "https://rxmicro.io,         GET",
            "https://rxmicro.io,         HEAD",
            "https://rxmicro.io,         POST",
            "https://rxmicro.io,         PUT"
    })
    void Should_allow_access_for_handler1_using_http_localhost_origin_only(final String origin,
                                                                           final String method) {
        final ClientHttpResponse response = blockingHttpClient.options(
                "/handler1",
                HttpHeaders.of(
                        ORIGIN, origin,
                        ACCESS_CONTROL_REQUEST_METHOD, method
                )
        );
        assertEquals("http://localhost:8080", response.getHeaders().getValue(ACCESS_CONTROL_ALLOW_ORIGIN));
        assertEquals("GET, HEAD, POST, PUT", response.getHeaders().getValue(ACCESS_CONTROL_ALLOW_METHODS));
        assertCommonHeaders(response);
    }

    @ParameterizedTest
    @CsvSource({
            "https://localhost:8443,     GET",
            "https://localhost:8443,     POST"
    })
    void Should_allow_access_for_handler2_using_https_localhost_origin_only(final String origin,
                                                                            final String method) {
        final ClientHttpResponse response = blockingHttpClient.options(
                "/handler2",
                HttpHeaders.of(
                        ORIGIN, origin,
                        ACCESS_CONTROL_REQUEST_METHOD, method
                )
        );
        assertEquals("https://localhost:8443", response.getHeaders().getValue(ACCESS_CONTROL_ALLOW_ORIGIN));
        assertEquals("GET, POST", response.getHeaders().getValue(ACCESS_CONTROL_ALLOW_METHODS));
        assertCommonHeaders(response);
    }

    @ParameterizedTest
    @CsvSource({
            "/handler3,     http://localhost:8080,     GET",
            "/handler4,     http://localhost:8080,     HEAD",
            "/handler5,     http://localhost:8080,     POST",
            "/get,          https://rxmicro.io,         GET",
            "/HEAD,         https://rxmicro.io,         HEAD",
            "/post,         https://rxmicro.io,         POST"
    })
    void Should_allow_access_for_handler3_using_http_localhost_origin_only(final String path,
                                                                           final String origin,
                                                                           final String method) {
        final ClientHttpResponse response = blockingHttpClient.options(
                path,
                HttpHeaders.of(
                        ORIGIN, origin,
                        ACCESS_CONTROL_REQUEST_METHOD, method
                )
        );
        assertEquals("http://localhost:8080", response.getHeaders().getValue(ACCESS_CONTROL_ALLOW_ORIGIN));
        assertEquals("GET, HEAD, POST", response.getHeaders().getValue(ACCESS_CONTROL_ALLOW_METHODS));
        assertCommonHeaders(response);
    }

    private void assertCommonHeaders(final ClientHttpResponse response) {
        assertEquals(ORIGIN, response.getHeaders().getValue(VARY));
        assertEquals("Ex-Mode", response.getHeaders().getValue(ACCESS_CONTROL_ALLOW_HEADERS));
        assertEquals("true", response.getHeaders().getValue(ACCESS_CONTROL_ALLOW_CREDENTIALS));
        assertEquals("600", response.getHeaders().getValue(ACCESS_CONTROL_MAX_AGE));
    }
}