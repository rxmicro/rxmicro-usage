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

package io.rxmicro.examples.monitoring.request.tracing.model.db;

import io.rxmicro.data.Column;
import io.rxmicro.data.ColumnMappingStrategy;
import io.rxmicro.data.sql.NotUpdatable;
import io.rxmicro.data.sql.PrimaryKey;
import io.rxmicro.data.sql.SequenceGenerator;
import io.rxmicro.data.sql.Table;
import io.rxmicro.model.BaseModel;

@SuppressWarnings("unused")
// tag::content[]
@Table
@ColumnMappingStrategy
public final class Account extends BaseModel {

    @PrimaryKey
    @SequenceGenerator
    Long id;

    @Column(length = Column.UNLIMITED_LENGTH)
    @NotUpdatable
    String email;

    @Column(length = Column.UNLIMITED_LENGTH)
    String firstName;

    @Column(length = Column.UNLIMITED_LENGTH)
    String lastName;

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
// end::content[]
