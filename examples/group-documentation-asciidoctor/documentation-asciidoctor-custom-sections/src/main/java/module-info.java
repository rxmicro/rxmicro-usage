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

import io.rxmicro.documentation.DocumentationDefinition;
import io.rxmicro.documentation.IncludeMode;
import io.rxmicro.documentation.IntroductionDefinition;
import io.rxmicro.documentation.ResourceDefinition;
import io.rxmicro.documentation.ResourceGroupDefinition;

// tag::content[]
@DocumentationDefinition(
        withGeneratedDate = false,
        introduction = @IntroductionDefinition(
                sectionOrder = {
                        IntroductionDefinition.Section.LICENSES,
                        IntroductionDefinition.Section.SPECIFICATION,
                        IntroductionDefinition.Section.CUSTOM_SECTION
                },
                customSection = {
                        "${PROJECT-DIR}/src/main/asciidoc/_fragment/" +
                                "custom-introduction-content.asciidoc"
                },
                includeMode = IncludeMode.INLINE_CONTENT
        ),
        resourceGroup = @ResourceGroupDefinition(
                sectionOrder = {
                        ResourceGroupDefinition.Section.CUSTOM_SECTION
                },
                customSection = {
                        "${PROJECT-DIR}/src/main/asciidoc/_fragment/" +
                                "custom-resource-group-content.asciidoc"
                },
                includeMode = IncludeMode.INLINE_CONTENT
        ),
        resource = @ResourceDefinition(
                withInternalErrorResponse = false,
                withJsonSchema = false,
                withRequestIdResponseHeader = false,
                withQueryParametersDescriptionTable = false,
                withBodyParametersDescriptionTable = false
        )
)
module examples.documentation.asciidoctor.custom.sections {
    requires rxmicro.rest.server.netty;
    requires rxmicro.rest.server.exchange.json;
    requires static rxmicro.documentation.asciidoctor;
}
// end::content[]