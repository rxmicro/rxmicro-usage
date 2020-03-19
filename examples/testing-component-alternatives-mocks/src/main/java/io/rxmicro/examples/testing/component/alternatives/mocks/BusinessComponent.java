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

package io.rxmicro.examples.testing.component.alternatives.mocks;

import static java.util.Objects.requireNonNull;

// tag::content[]
public final class BusinessComponent {

    private final BusinessChildComponent childComponent;

    public BusinessComponent() {
        childComponent = new BusinessChildComponent();
    }

    public BusinessComponent(final BusinessChildComponent childComponent) {
        this.childComponent = requireNonNull(childComponent);
    }

    public void execute() {
        System.out.println(childComponent.getEnvironmentName());
    }

    public static final class BusinessChildComponent {

        public BusinessChildComponent() {
            System.out.println("BusinessChildComponent created");
        }

        public String getEnvironmentName() {
            return "production";
        }
    }
}
// end::content[]
