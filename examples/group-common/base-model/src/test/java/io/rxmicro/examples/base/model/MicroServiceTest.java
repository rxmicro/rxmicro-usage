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

package io.rxmicro.examples.base.model;

import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.SystemOut;
import io.rxmicro.test.junit.RxMicroRestBasedMicroServiceTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RxMicroRestBasedMicroServiceTest(MicroService.class)
final class MicroServiceTest {

    private BlockingHttpClient blockingHttpClient;

    private SystemOut systemOut;

    @Test
    void test(){
        blockingHttpClient.get("/");

        assertEquals(
                List.of(
                        "ModelWithToStringImpl{string='string', bool=true, integer=34}",
                        "ModelWithoutToStringImpl{string='string', bool=true, integer=34}",
                        "ParentBaseModel{string='string', bool=true, integer=34}",
                        "ModelWithAlreadyOpenedPackage{string='string', bool=true, integer=34}"
                ),
                systemOut.asStrings()
        );
    }
}