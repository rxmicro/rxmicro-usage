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

package io.rxmicro.examples.cdi.errors.not_found_bean;

import io.rxmicro.cdi.BeanFactory;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
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
@RxMicroIntegrationTest
final class ContainerWithNotFoundBeanTest {

    @Test
    @Order(1)
    void Should_throw_InstanceNotFoundException() {
        final RuntimeException exception = assertThrows(RuntimeException.class, () -> BeanFactory.getBean(ContainerWithNotFoundBean.class));
        assertEquals(
                "io.rxmicro.runtime.local.error.InstanceNotFoundException",
                exception.getClass().getName()
        );
        assertEquals(
                "Can't inject bean qualified by '[" +
                        "@ByTypeAndName(io.rxmicro.examples.cdi.errors.not_found_bean.Child, 'child'), " +
                        "@ByType(io.rxmicro.examples.cdi.errors.not_found_bean.Child)]' into " +
                        "'io.rxmicro.examples.cdi.errors.not_found_bean.ContainerWithNotFoundBean.child': Bean not found.",
                exception.getMessage()
        );
    }
}