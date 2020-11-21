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

import io.rxmicro.test.junit.BeforeThisTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(InitMocksEmulationTestExtension.class)
@ExtendWith(RxMicroMicroServiceEmulationTestExtension.class)
final class MicroServiceEmulationTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("USER-TEST: '@org.junit.jupiter.api.BeforeAll' invoked.");
    }

    public MicroServiceEmulationTest() {
        System.out.println("USER-TEST: new instance of the REST-based microservice test class created.");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("USER-TEST: '@org.junit.jupiter.api.BeforeEach' invoked.");
    }

    void beforeEachPreparer1() {
        System.out.println("USER-TEST: 'beforeTest1UserMethod' invoked.");
    }

    @Test
    @BeforeThisTest(method = "beforeEachPreparer1")
    void test1() {
        System.out.println("USER-TEST: 'test1()' invoked.");
    }

    void beforeEachPreparer2() {
        System.out.println("USER-TEST: 'beforeTest2UserMethod' invoked.");
    }

    @Test
    @BeforeThisTest(method = "beforeEachPreparer2")
    void test2() {
        System.out.println("USER-TEST: 'test2()' invoked.");
    }

    @AfterEach
    void afterEach() {
        System.out.println("USER-TEST: '@org.junit.jupiter.api.AfterEach' invoked.");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("USER-TEST: '@org.junit.jupiter.api.AfterAll' invoked.");
    }

}