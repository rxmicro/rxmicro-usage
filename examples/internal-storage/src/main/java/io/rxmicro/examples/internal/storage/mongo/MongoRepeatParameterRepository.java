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

package io.rxmicro.examples.internal.storage.mongo;

import io.rxmicro.data.RepeatParameter;
import io.rxmicro.data.mongo.MongoRepository;
import io.rxmicro.data.mongo.operation.Find;
import io.rxmicro.examples.internal.storage.model.mongo.Account;
import reactor.core.publisher.Mono;

@MongoRepository(collection = "account")
public interface MongoRepeatParameterRepository {

    // tag::paramMappingExample[]
    @Find(query = "{email: ?}")
    Mono<Account> findByEmail(String email);
    // end::paramMappingExample[]

    // tag::problem[]
    @Find(query = "{$or: [{firstName: ?}, {lastName: ?}]}")
    Mono<Account> findByFirstOrLastNames(String name1, String name2);
    // end::problem[]

    // tag::solution[]
    @Find(query = "{$or: [{firstName: ?}, {lastName: ?}]}")
    Mono<Account> findByFirstOrLastNames(@RepeatParameter(2) String name);
    // end::solution[]
}
