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

package io.rxmicro.examples.graalvm.nativeimage.mongo.data;

import com.mongodb.client.result.InsertOneResult;
import io.rxmicro.config.Configs;
import io.rxmicro.examples.graalvm.nativeimage.mongo.data.model.Account;
import io.rxmicro.examples.graalvm.nativeimage.mongo.data.model.Report;
import io.rxmicro.examples.graalvm.nativeimage.mongo.data.model.Role;
import io.rxmicro.examples.graalvm.nativeimage.mongo.data.repository.MongoDataRepository;

import java.math.BigDecimal;
import java.util.List;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.data.RepositoryFactory.getRepository;
import static io.rxmicro.examples.graalvm.nativeimage.mongo.data.model.Role.CEO;
import static io.rxmicro.examples.graalvm.nativeimage.mongo.data.model.Role.Lead_Engineer;

public final class MongoLauncher {

    static {
        new Configs.Builder().build();
    }

    private static final MongoDataRepository DATA_REPOSITORY =
            getRepository(MongoDataRepository.class);

    private static void insert() {
        final Account account =
                new Account(100L, "test1@rxmicro.io", "First1", "Last1", new BigDecimal("5000.00"), Lead_Engineer);
        final InsertOneResult result = DATA_REPOSITORY.insert(account).join();
        System.out.println(format("STDOUT: Account created: ?, result=?", account, result.getInsertedId()));
    }

    private static void update() {
        final Account account =
                new Account(100L, "test2@rxmicro.io", "First2", "Last2", new BigDecimal("10000.00"), CEO);
        final Boolean result = DATA_REPOSITORY.update(account).join();
        System.out.println(format("STDOUT: Account updated: ?, result=?", account, result));
    }

    private static void countDocuments() {
        System.out.println(format("STDOUT: countDocuments: ?", DATA_REPOSITORY.countDocuments().join()));
    }

    private static void estimatedDocumentCount() {
        System.out.println(format("STDOUT: estimatedDocumentCount: ?", DATA_REPOSITORY.estimatedDocumentCount().join()));
    }

    private static void find() {
        final Account account = DATA_REPOSITORY.find(100L).join();
        System.out.println(format("STDOUT: Account found: ?", account));
    }

    private static void distinct() {
        final List<Role> roles = DATA_REPOSITORY.distinct().join();
        System.out.println(format("STDOUT: Distinct roles: ?", roles));
    }

    private static void aggregate() {
        final List<Report> reports = DATA_REPOSITORY.aggregate().join();
        System.out.println(format("STDOUT: Aggregate reports: ?", reports));
    }

    private static void delete() {
        final Account account =
                new Account(100L, null, null, null, null, null);
        final Boolean result = DATA_REPOSITORY.delete(account).join();
        System.out.println(format("STDOUT: Account deleted: ?, result=?", account, result));
    }

    public static void main(final String[] args) {
        insert();
        update();
        countDocuments();
        estimatedDocumentCount();
        find();
        distinct();
        aggregate();
        delete();
    }
}

