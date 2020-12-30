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

package io.rxmicro.util.graalvm;

public enum Example {

    config,

    mongo,

    postgres,

    rest_server,

    rest_client;

    public static Example of(final String projectName) {
        if ("graalvm-native-image-config".equals(projectName)) {
            return config;
        } else if ("graalvm-native-image-mongo-data-repository".equals(projectName)) {
            return mongo;
        } else if ("graalvm-native-image-postgres-data-repository".equals(projectName)) {
            return postgres;
        } else if ("graalvm-native-image-quick-start".equals(projectName)) {
            return rest_server;
        } else if ("graalvm-native-image-rest-client".equals(projectName)) {
            return rest_client;
        } else {
            throw new IllegalArgumentException("Not implemented: " + projectName);
        }
    }
}

