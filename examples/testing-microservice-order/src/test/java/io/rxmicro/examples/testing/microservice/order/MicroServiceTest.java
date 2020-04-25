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

package io.rxmicro.examples.testing.microservice.order;

import io.rxmicro.test.Alternative;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.SystemOut;
import io.rxmicro.test.junit.BeforeTest;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

@SuppressWarnings("EmptyMethod")
// tag::content[]
@InitMocks
@RxMicroRestBasedMicroServiceTest(MicroService.class)
final class MicroServiceTest {

    @BeforeAll
    static void beforeAll() {
    }

    private BlockingHttpClient blockingHttpClient;

    private SystemOut systemOut;

    @Mock
    @Alternative
    private BusinessService businessService;

    public MicroServiceTest() {
    }

    @BeforeEach
    void beforeEach() {
    }

    void beforeEachPreparer1() {
    }

    @Test
    @BeforeTest(method = "beforeEachPreparer1")
    void test1() {
    }

    void beforeEachPreparer2() {
    }

    @Test
    @BeforeTest(method = "beforeEachPreparer2")
    void test2() {
    }

    @AfterEach
    void afterEach() {
    }

    @AfterAll
    static void afterAll() {
    }

}
// end::content[]
