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

package io.rxmicro.util.tests;

import io.rxmicro.common.InvalidStateException;

import java.io.File;
import java.util.Map;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.util.CommonSettings.RX_MICRO_WORKSPACE_HOME;
import static java.util.Map.entry;

public final class Settings {

    public static final String MODULE_INFO_JAVA = "module-info.java";

    public static final String VIRTUAL_MODULE_INFO_JAVA = "ModuleInfo.java";

    public static final String POM_XML = "pom.xml";

    public static final String DOCUMENTATION_ASCIIDOC = "documentation-asciidoctor-";

    public static final String CDI = "cdi-";

    public static final String DATA_R2DBC_POSTGRESQL = "data-r2dbc-postgresql-";

    public static final String DATA_MONGO = "data-mongo-";

    public static final String UNNAMED_MODULE_PREFIX = "unnamed-module-";

    public static final String RX_MICRO_ROOT_DIR_PATH;

    public static final String EXAMPLES_ROOT_DIR_PATH;

    public static final Map<String, String> RX_MICRO_MODULES;

    static {
        RX_MICRO_ROOT_DIR_PATH = format("?/rxmicro", RX_MICRO_WORKSPACE_HOME);
        EXAMPLES_ROOT_DIR_PATH = format("?/rxmicro-usage/examples", RX_MICRO_WORKSPACE_HOME);
        for (final String path : new String[]{RX_MICRO_ROOT_DIR_PATH, EXAMPLES_ROOT_DIR_PATH}) {
            if (!new File(path).exists()) {
                throw new InvalidStateException("Directory not found: '?'", path);
            }
        }
        RX_MICRO_MODULES = createMapping();
    }

    private static Map<String, String> createMapping() {
        return Map.ofEntries(
                // REST controller
                entry(
                        "group-rest-controller/rest-controller-",
                        RX_MICRO_ROOT_DIR_PATH + "/rxmicro-annotation-processor-rest-server/src/test/resources"
                ),
                entry(
                        "group-unnamed-module/unnamed-module-rest-controller-",
                        RX_MICRO_ROOT_DIR_PATH + "/rxmicro-annotation-processor-rest-server/src/test/resources"
                ),
                entry(
                        "group-validation/validation-server-",
                        RX_MICRO_ROOT_DIR_PATH + "/rxmicro-annotation-processor-rest-server/src/test/resources"
                ),
                // REST client
                entry(
                        "group-rest-client/rest-client-",
                        RX_MICRO_ROOT_DIR_PATH + "/rxmicro-annotation-processor-rest-client/src/test/resources"
                ),
                entry(
                        "group-unnamed-module/unnamed-module-rest-client-",
                        RX_MICRO_ROOT_DIR_PATH + "/rxmicro-annotation-processor-rest-client/src/test/resources"
                ),
                entry(
                        "group-validation/validation-client-",
                        RX_MICRO_ROOT_DIR_PATH + "/rxmicro-annotation-processor-rest-client/src/test/resources"
                ),
                // Documentation
                entry(
                        "group-documentation-asciidoctor/documentation-asciidoctor-",
                        RX_MICRO_ROOT_DIR_PATH + "/rxmicro-annotation-processor-documentation-asciidoctor/src/test/resources"
                ),
                entry(
                        "group-unnamed-module/unnamed-module-documentation-asciidoctor-",
                        RX_MICRO_ROOT_DIR_PATH + "/rxmicro-annotation-processor-documentation-asciidoctor/src/test/resources"
                ),
                // CDI
                entry(
                        "group-cdi/cdi-",
                        RX_MICRO_ROOT_DIR_PATH + "/rxmicro-annotation-processor-cdi/src/test/resources"
                ),
                entry(
                        "group-unnamed-module/unnamed-module-cdi-",
                        RX_MICRO_ROOT_DIR_PATH + "/rxmicro-annotation-processor-cdi/src/test/resources"
                ),
                // PROCESSOR
                entry(
                        "group-processor",
                        RX_MICRO_ROOT_DIR_PATH + "/rxmicro-annotation-processor/src/test/resources"
                ),
                // Data postgres
                entry(
                        "group-data-r2dbc-postgresql/data-r2dbc-postgresql-",
                        RX_MICRO_ROOT_DIR_PATH + "/rxmicro-annotation-processor-data-sql-r2dbc-postgresql/src/test/resources"
                ),
                // Data mongo
                entry(
                        "group-data-mongo/data-mongo-",
                        RX_MICRO_ROOT_DIR_PATH + "/rxmicro-annotation-processor-data-mongo/src/test/resources"
                )
        );
    }

}
