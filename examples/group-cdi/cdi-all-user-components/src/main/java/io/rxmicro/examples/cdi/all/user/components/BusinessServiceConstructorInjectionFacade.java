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

import io.rxmicro.cdi.Inject;
import io.rxmicro.cdi.Named;
import io.rxmicro.examples.cdi.all.user.components.children.BusinessService;
import io.rxmicro.examples.cdi.all.user.components.children.BusinessServiceFactory;
import io.rxmicro.examples.cdi.all.user.components.children.BusinessServiceImpl;
import io.rxmicro.examples.cdi.all.user.components.children.FactoryMethodBusinessService;
import io.rxmicro.examples.cdi.all.user.components.children.PrivateFactoryMethodBusinessService;

import java.util.Set;

public final class BusinessServiceConstructorInjectionFacade {

    private final BusinessService businessService1;

    private final BusinessService businessService2;

    private final BusinessService businessService3;

    private final BusinessService businessService4;

    private final BusinessService businessService5;

    private final BusinessService businessService6;

    private final BusinessServiceImpl businessService7;

    private final FactoryMethodBusinessService businessService8;

    private final PrivateFactoryMethodBusinessService businessService9;

    private final BusinessServiceFactory businessService10;

    private final Set<BusinessService> businessServices;

    @Inject
    public BusinessServiceConstructorInjectionFacade(@Named("impl") final BusinessService businessService1,
                                                     @Named("factoryMethod") final BusinessService businessService2,
                                                     @Named("privateFactoryMethod") final BusinessService businessService3,
                                                     @Named("proxy") final BusinessService businessService4,
                                                     @Named("factoryClass") final BusinessService businessService5,
                                                     @Inject(optional = true) @Named("not-found") final BusinessService businessService6,
                                                     final BusinessServiceImpl businessService7,
                                                     final FactoryMethodBusinessService businessService8,
                                                     final PrivateFactoryMethodBusinessService businessService9,
                                                     final BusinessServiceFactory businessService10,
                                                     final Set<BusinessService> businessServices) {
        this.businessService1 = businessService1;
        this.businessService2 = businessService2;
        this.businessService3 = businessService3;
        this.businessService4 = businessService4;
        this.businessService5 = businessService5;
        this.businessService6 = businessService6;
        this.businessService7 = businessService7;
        this.businessService8 = businessService8;
        this.businessService9 = businessService9;
        this.businessService10 = businessService10;
        this.businessServices = businessServices;
    }

    void postConstruct() {
        System.out.println(businessService1);
        System.out.println(businessService2);
        System.out.println(businessService3);
        System.out.println(businessService4);
        System.out.println(businessService5);
        System.out.println(businessService6);
        System.out.println(businessService7);
        System.out.println(businessService8);
        System.out.println(businessService9);
        System.out.println(businessService10);
        System.out.println(businessServices);
    }
}
