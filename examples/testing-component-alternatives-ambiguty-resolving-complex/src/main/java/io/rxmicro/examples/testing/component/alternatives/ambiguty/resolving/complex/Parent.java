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

package io.rxmicro.examples.testing.component.alternatives.ambiguty.resolving.complex;

// tag::content[]
public final class Parent {

    private final Child child = new Child();

    private final BusinessService parentBusinessService =
            () -> "ParentBusinessService";

    private final ExtendedBusinessService parentExtendedBusinessService =
            () -> "ParentExtendedBusinessService";

    private final ExtendedBusinessServiceImpl parentExtendedBusinessServiceImpl =
            new ExtendedBusinessServiceImpl("ParentExtendedBusinessServiceImpl");

    public String getValue() {
        return String.format(
                "{%s, %s, %s} : %s",
                parentBusinessService.getValue(),
                parentExtendedBusinessService.getValue(),
                parentExtendedBusinessServiceImpl.getValue(),
                child.getValue()
        );
    }
}
// end::content[]
