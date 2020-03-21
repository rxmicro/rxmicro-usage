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

package io.rxmicro.examples.data.mongo.supported.types;

import io.rxmicro.data.Pageable;
import io.rxmicro.data.SortOrder;
import io.rxmicro.data.mongo.IndexUsage;
import io.rxmicro.data.mongo.ProjectionType;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;

import static io.rxmicro.examples.data.mongo.supported.types.model.TestSupportedTypesInstance.SUPPORTED_TYPES;
import static io.rxmicro.examples.data.mongo.supported.types.model.TestSupportedTypesInstance.SUPPORTED_TYPES_WITH_BIG_DECIMAL_PROJECTION;
import static java.util.Map.entry;

abstract class AbstractDataRepositoryIntegrationTest {

    static final Map<String, Map.Entry<Function<GetDataRepository, Optional<?>>, Object>> METHOD_MAPPING = new TreeMap<>(Map.ofEntries(
            entry(
                    "aggregate1",
                    entry(
                            dataRepository -> dataRepository.aggregate1().blockOptional(),
                            SUPPORTED_TYPES
                    )
            ),
            entry(
                    "aggregate2",
                    entry(
                            dataRepository -> dataRepository.aggregate2(
                                    SUPPORTED_TYPES.getId(),
                                    SUPPORTED_TYPES.getStatus(),
                                    SUPPORTED_TYPES.getaBoolean(),
                                    SUPPORTED_TYPES.getaByte(),
                                    SUPPORTED_TYPES.getaShort(),
                                    SUPPORTED_TYPES.getaInteger(),
                                    SUPPORTED_TYPES.getaLong(),
                                    SUPPORTED_TYPES.getBigDecimal(),
                                    SUPPORTED_TYPES.getCharacter(),
                                    SUPPORTED_TYPES.getString(),
                                    SUPPORTED_TYPES.getInstant(),
                                    SUPPORTED_TYPES.getUuid(),
                                    SortOrder.ASCENDING,
                                    SortOrder.DESCENDING,
                                    10,
                                    0,
                                    IndexUsage.INCLUDE
                            ).blockOptional(),
                            SUPPORTED_TYPES
                    )
            ),
            entry(
                    "aggregate3",
                    entry(
                            dataRepository -> dataRepository.aggregate3(new Pageable(10)).blockOptional(),
                            SUPPORTED_TYPES
                    )
            ),

            // ---------------------------------------------------------------------------------------------------------

            entry(
                    "find1",
                    entry(
                            dataRepository -> dataRepository.find1().blockOptional(),
                            SUPPORTED_TYPES
                    )
            ),
            entry(
                    "find2",
                    entry(
                            dataRepository -> dataRepository.find2(
                                    SUPPORTED_TYPES.getId(),
                                    SUPPORTED_TYPES.getStatus(),
                                    SUPPORTED_TYPES.getaBoolean(),
                                    SUPPORTED_TYPES.getaByte(),
                                    SUPPORTED_TYPES.getaShort(),
                                    SUPPORTED_TYPES.getaInteger(),
                                    SUPPORTED_TYPES.getaLong(),
                                    SUPPORTED_TYPES.getBigDecimal(),
                                    SUPPORTED_TYPES.getCharacter(),
                                    SUPPORTED_TYPES.getString(),
                                    SUPPORTED_TYPES.getInstant(),
                                    SUPPORTED_TYPES.getUuid(),
                                    IndexUsage.INCLUDE,
                                    SortOrder.ASCENDING,
                                    SortOrder.DESCENDING,
                                    10,
                                    0
                            ).blockOptional(),
                            SUPPORTED_TYPES
                    )
            ),
            entry(
                    "find3",
                    entry(
                            dataRepository -> dataRepository.find3(0, 10).blockOptional(),
                            SUPPORTED_TYPES
                    )
            ),
            entry(
                    "find4",
                    entry(
                            dataRepository -> dataRepository.find4(new Pageable(10)).blockOptional(),
                            SUPPORTED_TYPES
                    )
            ),
            entry(
                    "find5",
                    entry(
                            dataRepository -> dataRepository.find5(
                                    SUPPORTED_TYPES.getId(),
                                    SUPPORTED_TYPES.getStatus(),
                                    SUPPORTED_TYPES.getaBoolean(),
                                    SUPPORTED_TYPES.getaByte(),
                                    SUPPORTED_TYPES.getaShort(),
                                    SUPPORTED_TYPES.getaInteger(),
                                    SUPPORTED_TYPES.getaLong(),
                                    SUPPORTED_TYPES.getBigDecimal(),
                                    SUPPORTED_TYPES.getCharacter(),
                                    SUPPORTED_TYPES.getString(),
                                    SUPPORTED_TYPES.getInstant(),
                                    SUPPORTED_TYPES.getUuid(),
                                    ProjectionType.INCLUDE,
                                    IndexUsage.INCLUDE,
                                    SortOrder.ASCENDING,
                                    SortOrder.DESCENDING,
                                    10,
                                    0
                            ).blockOptional(),
                            SUPPORTED_TYPES_WITH_BIG_DECIMAL_PROJECTION
                    )
            ),

            // ---------------------------------------------------------------------------------------------------------

            entry(
                    "countDocuments1",
                    entry(
                            dataRepository -> dataRepository.countDocuments1().blockOptional(),
                            1L
                    )
            ),
            entry(
                    "countDocuments2",
                    entry(
                            dataRepository -> dataRepository.countDocuments2().blockOptional(),
                            1L
                    )
            ),
            entry(
                    "countDocuments3",
                    entry(
                            dataRepository -> dataRepository.countDocuments3(0, 10).blockOptional(),
                            1L
                    )
            ),
            entry(
                    "countDocuments4",
                    entry(
                            dataRepository -> dataRepository.countDocuments4(new Pageable(10)).blockOptional(),
                            1L
                    )
            ),
            entry(
                    "countDocuments5",
                    entry(
                            dataRepository -> dataRepository.countDocuments5(
                                    SUPPORTED_TYPES.getId(),
                                    SUPPORTED_TYPES.getStatus(),
                                    SUPPORTED_TYPES.getaBoolean(),
                                    SUPPORTED_TYPES.getaByte(),
                                    SUPPORTED_TYPES.getaShort(),
                                    SUPPORTED_TYPES.getaInteger(),
                                    SUPPORTED_TYPES.getaLong(),
                                    SUPPORTED_TYPES.getBigDecimal(),
                                    SUPPORTED_TYPES.getCharacter(),
                                    SUPPORTED_TYPES.getString(),
                                    SUPPORTED_TYPES.getInstant(),
                                    SUPPORTED_TYPES.getUuid(),
                                    IndexUsage.INCLUDE,
                                    10,
                                    0
                            ).blockOptional(),
                            1L
                    )
            ),

            // ---------------------------------------------------------------------------------------------------------

            entry(
                    "estimatedDocumentCount",
                    entry(
                            dataRepository -> dataRepository.estimatedDocumentCount().blockOptional(),
                            1L
                    )
            ),

            // ---------------------------------------------------------------------------------------------------------

            entry(
                    "distinct1",
                    entry(
                            dataRepository -> dataRepository.distinct1().blockOptional(),
                            SUPPORTED_TYPES.getBigDecimal()
                    )
            ),
            entry(
                    "distinct2",
                    entry(
                            dataRepository -> dataRepository.distinct2().blockOptional(),
                            SUPPORTED_TYPES.getBigDecimal()
                    )
            ),
            entry(
                    "distinct3",
                    entry(
                            dataRepository -> dataRepository.distinct3(
                                    SUPPORTED_TYPES.getId(),
                                    SUPPORTED_TYPES.getStatus(),
                                    SUPPORTED_TYPES.getaBoolean(),
                                    SUPPORTED_TYPES.getaByte(),
                                    SUPPORTED_TYPES.getaShort(),
                                    SUPPORTED_TYPES.getaInteger(),
                                    SUPPORTED_TYPES.getaLong(),
                                    SUPPORTED_TYPES.getBigDecimal(),
                                    SUPPORTED_TYPES.getCharacter(),
                                    SUPPORTED_TYPES.getString(),
                                    SUPPORTED_TYPES.getInstant(),
                                    SUPPORTED_TYPES.getUuid()
                            ).blockOptional(),
                            SUPPORTED_TYPES.getBigDecimal()
                    )
            )

    ));
}
