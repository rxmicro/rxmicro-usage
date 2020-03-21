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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.rxmicro.util.tests.Settings.MODULE_INFO_JAVA;
import static io.rxmicro.util.tests.Settings.VIRTUAL_MODULE_INFO_JAVA;

public final class Names {

    public static String defineRootPackage(final String srcRoot) {
        final List<String> paths = new ArrayList<>();
        defineRootPackage(paths, new File(srcRoot));
        return String.join(".", paths);
    }

    private static void defineRootPackage(final List<String> paths,
                                          final File srcRoot) {
        final File[] files = srcRoot.listFiles((dir, name) ->
                !MODULE_INFO_JAVA.equals(name) && !VIRTUAL_MODULE_INFO_JAVA.equals(name));
        if (files == null || files.length != 1 || !files[0].isDirectory()) {
            return;
        }
        paths.add(files[0].getName());
        defineRootPackage(paths, files[0]);
    }

    private Names() {
    }

}
