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

package io.rxmicro.examples.cdi.all.user.components;

import io.rxmicro.examples.cdi.all.user.components.children.BusinessService;
import io.rxmicro.test.SystemOut;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("unused")
abstract class AbstractBusinessServiceFacadeTest {

    private SystemOut systemOut;

    @BeforeEach
    void beforeEach() {
        BusinessService.map.clear();
    }

    final void verify() {
        assertEquals(
                List.of(
                        "BusinessServiceImpl#1.postConstruct()",
                        "FactoryMethodBusinessService#1.postConstruct()",
                        "PrivateFactoryMethodBusinessService#1.postConstruct()",
                        "BusinessServiceFactory#1.postConstruct()",
                        "BusinessServiceImpl#1",
                        "FactoryMethodBusinessService#1",
                        "PrivateFactoryMethodBusinessService#1",
                        "BusinessServiceFactory$Proxy#1",
                        "BusinessServiceFactory#1",
                        "null",
                        "BusinessServiceImpl#1",
                        "FactoryMethodBusinessService#1",
                        "PrivateFactoryMethodBusinessService#1",
                        "BusinessServiceFactory#1",
                        "[BusinessServiceFactory$Proxy#1, BusinessServiceFactory#1, BusinessServiceImpl#1, FactoryMethodBusinessService#1, PrivateFactoryMethodBusinessService#1]"
                ),
                systemOut.asStrings()
        );
    }
}
