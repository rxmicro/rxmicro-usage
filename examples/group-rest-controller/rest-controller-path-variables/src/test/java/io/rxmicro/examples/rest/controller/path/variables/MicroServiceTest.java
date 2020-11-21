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

package io.rxmicro.examples.rest.controller.path.variables;

import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.SystemOut;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

// tag::content[]
@RxMicroRestBasedMicroServiceTest(MicroService.class)
final class MicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    private SystemOut systemOut;

    @ParameterizedTest
    @CsvSource({
            "/1/category/class/subType,     category-class-subType",
            "/1/category/class_subType,     category-class-subType",
            "/1/category-class-subType,     category-class-subType",
            "/1-category-class-subType,     category-class-subType",

            "/2/category/class/subType,     category-class-subType",
            "/2/category/class_subType,     category-class-subType",
            "/2/category-class-subType,     category-class-subType",
            "/2-category-class-subType,     category-class-subType",

            "/1/5/6/7,                      5-6-7",
            "/1/5/6_7,                      5-6-7",
            "/1/5-6-7,                      5-6-7",
            "/1-5-6-7,                      5-6-7",

            "/2/5/6/7,                      5-6-7",
            "/2/5/6_7,                      5-6-7",
            "/2/5-6-7,                      5-6-7",
            "/2-5-6-7,                      5-6-7",
    })
    void Should_support_path_variables(final String path,           // <1>
                                       final String expectedOut) {  // <2>
        blockingHttpClient.get(path);

        assertEquals(expectedOut, systemOut.asString());
    }
}
// end::content[]
