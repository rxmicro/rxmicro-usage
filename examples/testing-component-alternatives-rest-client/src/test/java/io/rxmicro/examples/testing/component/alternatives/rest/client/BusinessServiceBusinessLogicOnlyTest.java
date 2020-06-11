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

import io.rxmicro.examples.testing.component.alternatives.rest.client.model.Response;
import io.rxmicro.http.error.HttpErrorException;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.concurrent.CompletableFuture.failedFuture;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// tag::content[]
@InitMocks
@RxMicroComponentTest(BusinessService.class)
final class BusinessServiceBusinessLogicOnlyTest {

    private BusinessService businessService;

    @Mock
    @Alternative
    private ExternalMicroService externalMicroService;

    @Mock
    private Response response;

    @Test
    void Should_return_String_message() {
        when(externalMicroService.get()).thenReturn(completedFuture(response));
        when(response.getMessage()).thenReturn("test");
        final Optional<String> result = businessService.get().join();
        assertEquals(Optional.of("test"), result);
    }

    @Test
    void Should_return_Not_Found_error() {
        when(externalMicroService.get()).thenReturn(failedFuture(new NotFoundException()));
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
