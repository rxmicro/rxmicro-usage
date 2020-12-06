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

package io.rxmicro.examples.graalvm.nativeimage.postgres.data;

import io.rxmicro.config.Configs;
import io.rxmicro.examples.graalvm.nativeimage.postgres.data.model.Account;
import io.rxmicro.examples.graalvm.nativeimage.postgres.data.repository.PostgresDataRepository;

import java.math.BigDecimal;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.data.RepositoryFactory.getRepository;
import static io.rxmicro.examples.graalvm.nativeimage.postgres.data.model.Role.CEO;
import static io.rxmicro.examples.graalvm.nativeimage.postgres.data.model.Role.Lead_Engineer;

public final class PostgresLauncher {

    static {
        new Configs.Builder().build();
    }

    private static final PostgresDataRepository DATA_REPOSITORY =
            getRepository(PostgresDataRepository.class);

    public static void main(final String[] args) {
        insert();
        update();
        select();
        delete();
    }

    private static void insert() {
        final Account account =
                new Account(100L, "test1@rxmicro.io", "First1", "Last1", new BigDecimal("5000.00"), Lead_Engineer);
        final Boolean result = DATA_REPOSITORY.insert(account).join();
        System.out.println(format("STDOUT: Account created: ?, result=?", account, result));
    }

    private static void update() {
        final Account account =
                new Account(100L, "test2@rxmicro.io", "First2", "Last2", new BigDecimal("10000.00"), CEO);
        final Boolean result = DATA_REPOSITORY.update(account).join();
        System.out.println(format("STDOUT: Account updated: ?, result=?", account, result));
    }

    private static void select() {
        final Account account = DATA_REPOSITORY.findById(100L).join();
        System.out.println(format("STDOUT: Account selected: ?", account));
    }

    private static void delete() {
        final Account account =
                new Account(100L, null, null, null, null, null);
        final Boolean result = DATA_REPOSITORY.delete(account).join();
        System.out.println(format("STDOUT: Account deleted: ?, result=?", account, result));
    }
}

