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

package io.rxmicro.examples.testing.microservice.alternatives.rest.client;

import io.rxmicro.examples.testing.microservice.alternatives.rest.client.model.Response;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.ClientHttpResponse;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static io.rxmicro.test.json.JsonFactory.jsonObject;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// tag::content[]
@InitMocks
@RxMicroRestBasedMicroServiceTest(MicroService.class)
final class MicroServiceBusinessLogicOnlyTest {

    private BlockingHttpClient blockingHttpClient;

    @Mock
    @Alternative
    private ExternalMicroService externalMicroService;

    @Test
    void Should_delegate_call_to_ExternalMicroService() {
        when(externalMicroService.get()).thenReturn(completedFuture(new Response("mock")));

        final ClientHttpResponse response = blockingHttpClient.get("/");

        assertEquals(jsonObject("message", "mock"), response.getBody());
    }
}
// end::content[]
