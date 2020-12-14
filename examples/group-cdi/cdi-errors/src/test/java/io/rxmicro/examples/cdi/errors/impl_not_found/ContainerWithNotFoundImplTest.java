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

package io.rxmicro.examples.cdi.errors.impl_not_found;

import io.rxmicro.common.InvalidStateException;
import io.rxmicro.test.junit.RxMicroComponentTest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RxMicroComponentTest(ContainerWithNotFoundImpl.class)
final class ContainerWithNotFoundImplTest {

    private ContainerWithNotFoundImpl container;

    @Test
    @Order(1)
    void Should_throw_InstanceNotFoundException() {
        final InvalidStateException exception = assertThrows(InvalidStateException.class, () -> container.getChild());
        assertEquals("Implementation not found: io.rxmicro.examples.cdi.errors.impl_not_found.Child", exception.getMessage());
    }
}