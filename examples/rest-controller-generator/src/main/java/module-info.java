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
// tag::content[]
import io.rxmicro.rest.model.GenerateOption;
import io.rxmicro.rest.model.ServerExchangeFormatModule;
import io.rxmicro.rest.server.RestServerGeneratorConfig;

@RestServerGeneratorConfig(
        exchangeFormat = ServerExchangeFormatModule.AUTO_DETECT, // <1>
        generateRequestValidators = GenerateOption.AUTO_DETECT,  // <2>
        generateResponseValidators = GenerateOption.DISABLED     // <3>
)
module rest.controller.generator {
    requires rxmicro.rest.server.netty;
}
// end::content[]