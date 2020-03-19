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

package io.rxmicro.examples.docker.image.mongo.test.db.model;

import io.rxmicro.data.mongo.DocumentId;

import java.math.BigDecimal;

// tag::content[]
public class Account {
// end::content[]

    // tag::content[]
    @DocumentId
    Long id;

    String email;

    String firstName;

    String lastName;

    BigDecimal balance;

    Role role;

    // end::content[]
    public Account() {
    }

    public Account(final Long id,
                   final String email,
                   final String firstName,
                   final String lastName,
                   final BigDecimal balance,
                   final Role role) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.role = role;
    }
// tag::content[]
}
// end::content[]
