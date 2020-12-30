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

package io.rxmicro.util.graalvm.processor;

import io.rxmicro.util.graalvm.Example;
import io.rxmicro.util.graalvm.GraalVmProcessor;
import io.rxmicro.util.graalvm.MissingProcessingLogicException;
import io.rxmicro.util.graalvm.RxMicroNativeImageResource;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ProxyProcessor implements GraalVmProcessor {

    @Override
    public List<RxMicroNativeImageResource> process(final File rxMicroHome,
                                                    final List<Map.Entry<Example, Object>> data) {
        if (!data.isEmpty()) {
            throw new MissingProcessingLogicException("TODO proxy must be implemented!");
        }
        return List.of();
    }
}

