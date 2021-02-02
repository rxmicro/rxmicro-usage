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

package io.rxmicro.examples.testing.component.alternatives.rest.client;

import io.rxmicro.http.error.HttpErrorException;
import io.rxmicro.rest.client.detail.HttpClientFactory;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.BeforeThisTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.httpclient.HttpRequestMock;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static io.rxmicro.rest.model.HttpMethod.GET;
import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static io.rxmicro.test.mockito.httpclient.HttpClientMockFactory.prepareHttpClientMock;
import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
@InitMocks
@RxMicroComponentTest(BusinessService.class)
final class BusinessServiceWillAllGeneratedCodeTest {

    private BusinessService businessService;

    // <3>
    @Mock
    @Alternative
    private HttpClientFactory httpClientFactory;

    // <4>
    void prepareOneEntityFound() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(GET)
                        .setPath("/")
                        .build(),
                jsonObject("message", "mock") // <3>
        );
    }

    @Test
    @BeforeThisTest(method = "prepareOneEntityFound")
    void Should_return_Entity_data() {
        final Optional<String> result = businessService.get().join();
        assertEquals(Optional.of("mock"), result);
    }

    // <8>
    void prepareNoEntityFound() {
        prepareHttpClientMock(
                httpClientFactory,
                new HttpRequestMock.Builder()
                        .setMethod(GET)
                        .setPath("/")
                        .build(),
                new NotFoundException()
        );
    }

    @Test
    @BeforeThisTest(method = "prepareNoEntityFound")
    void Should_return_Not_Found_error() {
        final Optional<String> result = businessService.get().join();
        assertEquals(Optional.empty(), result);
    }

    private static final class NotFoundException extends HttpErrorException {

        private NotFoundException() {
            super(404);
        }
    }
}
// end::content[]
