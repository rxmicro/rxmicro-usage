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

import io.rxmicro.examples.base.model.model.package1.ModelWithToStringImpl;
import io.rxmicro.examples.base.model.model.package2.ModelWithoutToStringImpl;
import io.rxmicro.examples.base.model.model.package3.ModelThatExtendsParentBaseModel;
import io.rxmicro.examples.base.model.model.package4.ModelWithAlreadyOpenedPackage;
import io.rxmicro.rest.method.GET;

public class MicroService {

    @GET("/")
    void test() {
        System.out.println(new ModelWithToStringImpl());
        System.out.println(new ModelWithoutToStringImpl());
        System.out.println(new ModelThatExtendsParentBaseModel());
        System.out.println(new ModelWithAlreadyOpenedPackage());
    }
}
