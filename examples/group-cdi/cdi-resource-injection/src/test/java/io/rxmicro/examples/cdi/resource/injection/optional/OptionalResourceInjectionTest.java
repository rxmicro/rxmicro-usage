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

package io.rxmicro.examples.cdi.resource.injection.optional;

import io.rxmicro.test.junit.RxMicroComponentTest;
import org.junit.jupiter.api.Test;

import static io.rxmicro.json.JsonTypes.asJsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RxMicroComponentTest(OptionalResourceInjection.class)
final class OptionalResourceInjectionTest {

    private OptionalResourceInjection resourceInjectionComponent;

    @Test
    void Should_the_resources_be_injected() {
        assertEquals("object", resourceInjectionComponent.getRequiredJsonObject().get("type"));
        assertEquals("array", asJsonObject(resourceInjectionComponent.getRequiredJsonArray().get(0)).get("type"));
        assertEquals("properties", resourceInjectionComponent.getRequiredProperties().get("type"));

        assertNull(resourceInjectionComponent.getOptionalJsonObject());
        assertNull(resourceInjectionComponent.getOptionalJsonArray());
        assertNull(resourceInjectionComponent.getOptionalProperties());
    }
}