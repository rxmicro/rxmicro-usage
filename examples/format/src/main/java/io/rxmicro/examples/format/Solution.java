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

package io.rxmicro.examples.format;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.Result;
import io.rxmicro.common.InvalidStateException;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import reactor.core.publisher.Mono;

public final class Solution {

    private static final Logger RX_MICRO_LOGGER = LoggerFactory.getLogger(Solution.class);

    // tag::content[]
    Mono<? extends Result> executeQuery(final Connection connection,
                                        final Long id) {
        final String sql = "SELECT * FROM account WHERE id = ?"; // <1>
        RX_MICRO_LOGGER.info("SQL: ?", sql); // <2>
        return Mono.from(connection.createStatement(sql)
                .bind(0, id)
                .execute())
                .onErrorResume(e -> Mono.error(
                        new InvalidStateException(
                                "SQL '?' contains syntax error: ?", sql, e.getMessage()) // <3>
                        )
                );
    }
    // end::content[]
}
