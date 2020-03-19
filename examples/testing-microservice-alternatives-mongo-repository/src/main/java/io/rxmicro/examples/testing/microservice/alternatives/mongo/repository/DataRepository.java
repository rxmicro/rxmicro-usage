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

package io.rxmicro.examples.testing.microservice.alternatives.mongo.repository;

import io.rxmicro.data.mongo.MongoRepository;
import io.rxmicro.data.mongo.operation.Find;
import io.rxmicro.examples.testing.microservice.alternatives.mongo.repository.model.Entity;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

// tag::content[]
@MongoRepository(collection = "collection")
public interface DataRepository {

    @Find(query = "{_id: ?}")
    CompletableFuture<Optional<Entity>> findById(long id);
}
// end::content[]
