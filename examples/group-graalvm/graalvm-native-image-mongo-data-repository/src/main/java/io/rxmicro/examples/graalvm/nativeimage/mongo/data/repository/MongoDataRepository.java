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

package io.rxmicro.examples.graalvm.nativeimage.mongo.data.repository;

import com.mongodb.client.result.InsertOneResult;
import io.rxmicro.config.DefaultConfigValue;
import io.rxmicro.data.mongo.MongoConfig;
import io.rxmicro.data.mongo.MongoRepository;
import io.rxmicro.data.mongo.operation.Aggregate;
import io.rxmicro.data.mongo.operation.CountDocuments;
import io.rxmicro.data.mongo.operation.Delete;
import io.rxmicro.data.mongo.operation.Distinct;
import io.rxmicro.data.mongo.operation.EstimatedDocumentCount;
import io.rxmicro.data.mongo.operation.Find;
import io.rxmicro.data.mongo.operation.Insert;
import io.rxmicro.data.mongo.operation.Update;
import io.rxmicro.examples.graalvm.nativeimage.mongo.data.model.Account;
import io.rxmicro.examples.graalvm.nativeimage.mongo.data.model.Report;
import io.rxmicro.examples.graalvm.nativeimage.mongo.data.model.Role;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@MongoRepository(collection = "account")
@DefaultConfigValue(configClass = MongoConfig.class, name = "database", value = "rxmicro")
public interface MongoDataRepository {

    @CountDocuments
    CompletableFuture<Long> countDocuments();

    @EstimatedDocumentCount
    CompletableFuture<Long> estimatedDocumentCount();

    @Insert
    CompletableFuture<InsertOneResult> insert(Account account);

    @Update
    CompletableFuture<Boolean> update(Account account);

    @Delete
    CompletableFuture<Boolean> delete(Account account);

    @Find(query = "{_id:?}")
    CompletableFuture<Account> find(long id);

    @Distinct(field = "role")
    CompletableFuture<List<Role>> distinct();

    @Aggregate(pipeline = {
            "{ $group : { _id: '$role', total : { $sum: 'balance'}} }",
            "{ $sort: { total: -1, _id: -1} }"
    })
    CompletableFuture<List<Report>> aggregate();
}
