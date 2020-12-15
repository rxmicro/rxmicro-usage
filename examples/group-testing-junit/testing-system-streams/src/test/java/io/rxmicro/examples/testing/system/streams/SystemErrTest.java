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

package io.rxmicro.examples.testing.system.streams;

import io.rxmicro.test.SystemErr;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RxMicroIntegrationTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class SystemErrTest {

    private SystemErr systemErr;

    @Test
    void Should_catch_system_out(){
        System.err.println(" Hello ");
        System.err.println("world");

        assertEquals(List.of("Hello", "world"), systemErr.asStrings(StandardCharsets.US_ASCII));
        assertEquals(List.of("Hello", "world"), systemErr.asStrings());
        assertEquals(List.of(" Hello ", "world"), systemErr.asStrings(false));

        assertEquals("Hello \nworld", systemErr.asString(StandardCharsets.US_ASCII));
        assertEquals(" Hello \nworld\n", systemErr.asString(false));
    }
}

