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

package io.rxmicro.examples.testing.microservice.alternatives.mocks;

import io.rxmicro.test.Alternative;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.ClientHttpResponse;
import io.rxmicro.test.SystemOut;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

// tag::content[]
// <1>
// @ExtendWith(MockitoExtension.class)
// <2>
@InitMocks
// <3>
@RxMicroRestBasedMicroServiceTest(MicroService.class)
final class MicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    private SystemOut systemOut;

    @Mock
    @Alternative
    private BusinessService businessService; // <4>

    @Test
    void Should_use_mocks() {
        when(businessService.doSomething())
                .thenReturn(CompletableFuture.completedFuture("mock")); // <5>

        final ClientHttpResponse response = blockingHttpClient.patch("/");

        assertEquals("Result: mock", systemOut.asString()); // <6>
        assertEquals(200, response.getStatusCode());
        assertTrue(response.isBodyEmpty(), "Body not empty: " + response.getBody());
    }
}
// end::content[]
