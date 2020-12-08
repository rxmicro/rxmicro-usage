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

package io.rxmicro.examples.graalvm.nativeimage.mongo.data.model;

import io.rxmicro.data.mongo.DocumentId;

import java.math.BigDecimal;

public class Report {

    @DocumentId
    Role id;

    BigDecimal total;

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", total=" + total +
                '}';
    }
}

