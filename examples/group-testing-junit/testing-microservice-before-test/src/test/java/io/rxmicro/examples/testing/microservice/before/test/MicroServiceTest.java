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

package io.rxmicro.examples.testing.microservice.before.test;

import io.rxmicro.test.Alternative;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.SystemOut;
import io.rxmicro.test.junit.BeforeIterationMethodSource;
import io.rxmicro.test.junit.BeforeThisTest;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// tag::content[]
@InitMocks
@RxMicroRestBasedMicroServiceTest(MicroService.class)
final class MicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    private SystemOut systemOut;

    @Mock
    @Alternative
    private BusinessService businessService;

    void beforeEachPreparer() { // <1>
        when(businessService.getValue()).thenReturn(5);
    }

    @Test
    // <2>
    @BeforeThisTest(method = "beforeEachPreparer")
    void test() {
        // when(businessService.getValue()).thenReturn(5); // <3>
        blockingHttpClient.patch("/");

        assertEquals("5", systemOut.asString());
    }

    // <4>
    void beforeEachPreparer1() {
        when(businessService.getValue()).thenReturn(1 + 1);
    }

    // <5>
    void beforeEachPreparer2() {
        when(businessService.getValue()).thenReturn(2);
    }

    @ParameterizedTest
    // <6>
    @BeforeIterationMethodSource(methods = {
            "beforeEachPreparer1",
            "beforeEachPreparer2"
    })
    void parameterizedTest(final String method) { // <7> method parameter
        blockingHttpClient.patch("/");

        assertEquals("2", systemOut.asString());
    }
}
// end::content[]
