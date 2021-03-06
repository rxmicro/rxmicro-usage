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

package io.rxmicro.examples.rest.client.model.field.access.params;

import io.rxmicro.examples.rest.client.model.field.access.params.reflection.BodyRequest;
import io.rxmicro.examples.rest.client.model.field.access.params.reflection.QueryRequest;
import io.rxmicro.examples.rest.client.model.field.access.params.reflection.Response;
import io.rxmicro.rest.client.RestClient;
import io.rxmicro.rest.method.GET;
import io.rxmicro.rest.method.PUT;

import java.util.concurrent.CompletionStage;

@RestClient
public interface ReflectionParamsRestClient {

    @GET("/params/direct")
    CompletionStage<Response> get(final QueryRequest request);

    @PUT("/params/reflection")
    CompletionStage<Response> put(final BodyRequest request);
}
