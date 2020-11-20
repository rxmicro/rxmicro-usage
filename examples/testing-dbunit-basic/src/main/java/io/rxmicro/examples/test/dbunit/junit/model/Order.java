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

package io.rxmicro.examples.test.dbunit.junit.model;

import io.rxmicro.data.ColumnMappingStrategy;
import io.rxmicro.data.sql.NotInsertable;
import io.rxmicro.data.sql.PrimaryKey;
import io.rxmicro.data.sql.Table;

import java.time.Instant;

// tag::content[]
@Table
@ColumnMappingStrategy
public final class Order {

    @PrimaryKey
    Long id;

    Long idAccount;

    Integer idProduct;

    Integer count;

    @NotInsertable
    Instant created;
}
// end::content[]
