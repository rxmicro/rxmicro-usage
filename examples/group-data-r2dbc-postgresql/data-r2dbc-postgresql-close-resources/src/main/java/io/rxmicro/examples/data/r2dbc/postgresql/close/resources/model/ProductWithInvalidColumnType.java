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

package io.rxmicro.examples.data.r2dbc.postgresql.close.resources.model;

import io.rxmicro.data.Column;
import io.rxmicro.data.sql.Table;

import java.time.Instant;

import static io.rxmicro.examples.data.r2dbc.postgresql.close.resources.Constants.PRODUCT_TABLE_NAME;

@Table(name = PRODUCT_TABLE_NAME)
public class ProductWithInvalidColumnType {

    @Column(length = Column.UNLIMITED_LENGTH)
    Instant id;
}
