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

package io.rxmicro.examples.cdi.ambiguty.resolving;

import io.rxmicro.examples.cdi.ambiguty.resolving.using.custom.named.annotation.BusinessServiceFacade;
import io.rxmicro.test.junit.RxMicroComponentTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
@RxMicroComponentTest(BusinessServiceFacade.class)
final class BusinessServiceFacadeTest {

    private BusinessServiceFacade businessServiceFacade;

    @Test
    void Should_return_PRODUCTION() {
        assertEquals("PRODUCTION", businessServiceFacade.getValue(true));
    }

    @Test
    void Should_return_DEVELOPMENT() {
        assertEquals("DEVELOPMENT", businessServiceFacade.getValue(false));
    }
}
// end::content[]