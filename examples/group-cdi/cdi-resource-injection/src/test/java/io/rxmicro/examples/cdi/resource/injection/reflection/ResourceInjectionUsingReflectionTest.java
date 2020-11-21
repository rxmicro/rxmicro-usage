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

package io.rxmicro.examples.cdi.resource.injection.reflection;

import io.rxmicro.test.junit.RxMicroComponentTest;
import org.junit.jupiter.api.Test;

import static io.rxmicro.json.JsonTypes.asJsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RxMicroComponentTest(ResourceInjectionUsingReflection.class)
final class ResourceInjectionUsingReflectionTest {

    private ResourceInjectionUsingReflection resourceInjectionComponent;

    @Test
    void Should_the_resources_be_injected() {
        assertEquals("object", resourceInjectionComponent.getJsonObject().get("type"));
        assertEquals("array", asJsonObject(resourceInjectionComponent.getJsonArray().get(0)).get("type"));
        assertEquals("properties", resourceInjectionComponent.getProperties().get("type"));
    }
}