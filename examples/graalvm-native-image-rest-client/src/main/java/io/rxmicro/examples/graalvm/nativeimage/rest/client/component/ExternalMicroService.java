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

package io.rxmicro.examples.graalvm.nativeimage.rest.client.component;

import io.rxmicro.config.DefaultConfigValue;
import io.rxmicro.examples.graalvm.nativeimage.rest.client.model.Model;
import io.rxmicro.rest.client.RestClient;
import io.rxmicro.rest.method.GET;

import java.util.concurrent.CompletableFuture;

@RestClient
@DefaultConfigValue(name = "http-client.schema", value = "https")
@DefaultConfigValue(name = "http-client.host", value = "rxmicro.io")
@DefaultConfigValue(name = "http-client.port", value = "443")
public interface ExternalMicroService {

    @GET("/mock-api/say-hello.json")
    CompletableFuture<Model> getMessage();
}
