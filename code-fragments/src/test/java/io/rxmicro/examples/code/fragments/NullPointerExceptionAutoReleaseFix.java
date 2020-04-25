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

package io.rxmicro.examples.code.fragments;

import io.rxmicro.http.client.HttpClientFactory;
import io.rxmicro.test.Alternative;
import org.mockito.Answers;
import org.mockito.Mock;

public class NullPointerExceptionAutoReleaseFix {

    // tag::content[]
    @Alternative
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) // <1>
    private HttpClientFactory httpClientFactory;
    // end::content[]
}
