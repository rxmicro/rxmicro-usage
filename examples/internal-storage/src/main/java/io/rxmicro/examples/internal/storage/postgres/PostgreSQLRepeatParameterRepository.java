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

package io.rxmicro.examples.internal.storage.postgres;

import io.rxmicro.data.RepeatParameter;
import io.rxmicro.data.sql.operation.Select;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;
import io.rxmicro.examples.internal.storage.model.postgres.Account;
import reactor.core.publisher.Mono;

@PostgreSQLRepository
public interface PostgreSQLRepeatParameterRepository {

    // tag::paramMappingExample[]
    @Select("SELECT * FROM ${table} WHERE email=?")
    Mono<Account> findByEmail(String email);
    // end::paramMappingExample[]

    // tag::problem[]
    @Select("SELECT * FROM ${table} WHERE firstName=? OR lastName=?")
    Mono<Account> findByFirstOrLastNames(String name1, String name2);
    // end::problem[]

    // tag::solution[]
    @Select("SELECT * FROM ${table} WHERE firstName=? OR lastName=?")
    Mono<Account> findByFirstOrLastNames(@RepeatParameter(2) String name);
    // end::solution[]
}
