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

package io.rxmicro.examples.graalvm.nativeimage.postgres.data.repository;

import io.rxmicro.data.sql.operation.Select;
import io.rxmicro.data.sql.r2dbc.postgresql.PostgreSQLRepository;

import java.util.concurrent.CompletableFuture;

@PostgreSQLRepository
public interface PostgresDataRepository {

    @Select("SELECT 2 + 2")
    CompletableFuture<Integer> select();
}
