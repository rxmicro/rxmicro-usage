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

import io.rxmicro.rest.client.RestClientGeneratorConfig;
import io.rxmicro.rest.model.GenerateOption;
import io.rxmicro.rest.server.RestServerGeneratorConfig;

// tag::content[]
@RestServerGeneratorConfig(
        generateRequestValidators = GenerateOption.DISABLED,    // <1>
        generateResponseValidators = GenerateOption.DISABLED    // <2>
)
@RestClientGeneratorConfig(
        generateRequestValidators = GenerateOption.DISABLED,    // <3>
        generateResponseValidators = GenerateOption.DISABLED    // <4>
)
module examples.validation {
    requires rxmicro.rest.server;
    requires rxmicro.rest.client;
    requires rxmicro.validation;
}
// end::content[]