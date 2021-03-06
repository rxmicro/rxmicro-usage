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

package io.rxmicro.examples.testing.component.order;

import io.rxmicro.test.Alternative;
import io.rxmicro.test.SystemOut;
import io.rxmicro.test.junit.BeforeThisTest;
import io.rxmicro.test.junit.RxMicroComponentTest;
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
@RxMicroComponentTest(BusinessService.class)
final class BusinessServiceTest {

    @BeforeAll
    static void beforeAll() {
    }

    private BusinessService businessService;

    private SystemOut systemOut;

    @Mock
    @Alternative
    private BusinessService.ChildBusinessService childBusinessService;

    public BusinessServiceTest() {
    }

    @BeforeEach
    void beforeEach() {
    }

    void beforeTest1UserMethod() {
    }

    @Test
    @BeforeThisTest(method = "beforeTest1UserMethod")
    void test1() {
    }

    void beforeTest2UserMethod() {
    }

    @Test
    @BeforeThisTest(method = "beforeTest2UserMethod")
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
