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

package io.rxmicro.examples.config.custom.type;

import io.rxmicro.config.Configs;
import io.rxmicro.examples.config.custom.type.config.ExampleConfig;

import static io.rxmicro.config.Configs.getConfig;

public final class Launcher {

    public static void main(final String[] args) {
        setJavaSystemProperties();

        new Configs.Builder().build();

        // tag::getConfig[]
        System.out.println(
                "Default constant: " +
                        getConfig(ExampleConfig.class).getType().getValue()
        );
        System.out.println(
                "Enum constant: " +
                        getConfig("enum-constant", ExampleConfig.class).getType().getValue()
        );
        System.out.println(
                "Class constant: " +
                        getConfig("class-constant", ExampleConfig.class).getType().getValue()
        );
        System.out.println(
                "Interface constant: " +
                        getConfig("interface-constant", ExampleConfig.class).getType().getValue()
        );
        System.out.println(
                "Annotation constant: " +
                        getConfig("annotation-constant", ExampleConfig.class).getType().getValue()
        );
        // end::getConfig[]
    }

    private static void setJavaSystemProperties() {
        // tag::setProperties[]
        System.setProperty(
                "enum-constant.type",
                "@io.rxmicro.examples.config.custom.type._enum.CustomEnum:ENUM_CONSTANT"
        );
        System.setProperty(
                "class-constant.type",
                "@io.rxmicro.examples.config.custom.type._class.CustomClass:CLASS_CONSTANT"
        );
        System.setProperty(
                "interface-constant.type",
                "@io.rxmicro.examples.config.custom.type._interface.CustomInterface:INTERFACE_CONSTANT"
        );
        System.setProperty(
                "annotation-constant.type",
                "@io.rxmicro.examples.config.custom.type._annotation.CustomAnnotation:ANNOTATION_CONSTANT"
        );
        // end::setProperties[]
    }

    private Launcher(){
    }
}

