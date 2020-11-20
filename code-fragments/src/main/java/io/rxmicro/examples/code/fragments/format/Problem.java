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

package io.rxmicro.examples.code.fragments.format;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public final class Problem {

    private static final Logger SLF4J_LOGGER = LoggerFactory.getLogger(Problem.class);

    // tag::content[]
    Mono<? extends Result> executeQuery(final Connection connection,
                                        final Long id) {
        final String sql = "SELECT * FROM account WHERE id = $1"; // <1>
        SLF4J_LOGGER.info("SQL: {}", sql); // <2>
        return Mono.from(connection.createStatement(sql)
                .bind(0, id)
                .execute())
                .onErrorResume(e -> Mono.error(
                        new IllegalArgumentException(
                                String.format(
                                        "SQL '%s' contains syntax error: %s", sql, e.getMessage() // <3>
                                ))
                        )
                );
    }
    // end::content[]
}
