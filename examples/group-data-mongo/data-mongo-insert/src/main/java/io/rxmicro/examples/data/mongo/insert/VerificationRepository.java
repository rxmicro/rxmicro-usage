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

package io.rxmicro.examples.data.mongo.insert;

import io.rxmicro.data.mongo.MongoRepository;
import io.rxmicro.data.mongo.operation.Find;
import io.rxmicro.examples.data.mongo.insert.model.Account;
import reactor.core.publisher.Mono;

@MongoRepository(collection = DataRepository.COLLECTION_NAME)
public interface VerificationRepository {

    @Find(query = "{_id: ?}")
    Mono<Account> findById(long id);
}
