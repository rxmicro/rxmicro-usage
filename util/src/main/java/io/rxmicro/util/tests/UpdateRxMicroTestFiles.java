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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.util.tests.Copies.copyDocs;
import static io.rxmicro.util.tests.Copies.copyInput;
import static io.rxmicro.util.tests.Copies.copyOutput;
import static io.rxmicro.util.tests.Names.defineRootPackage;
import static io.rxmicro.util.tests.Settings.CDI;
import static io.rxmicro.util.tests.Settings.DATA_MONGO;
import static io.rxmicro.util.tests.Settings.DATA_R2DBC_POSTGRESQL;
import static io.rxmicro.util.tests.Settings.DOCUMENTATION_ASCIIDOC;
import static io.rxmicro.util.tests.Settings.EXAMPLES_ROOT_DIR_PATH;
import static io.rxmicro.util.tests.Settings.MODULE_INFO_JAVA;
import static io.rxmicro.util.tests.Settings.RX_MICRO_MODULES;
import static io.rxmicro.util.tests.Settings.UNNAMED_MODULE_PREFIX;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.io.FileUtils.deleteDirectory;
import static org.apache.commons.io.FileUtils.forceMkdir;

public final class UpdateRxMicroTestFiles {

    private static void clearInputOutputDirs() throws IOException {
        for (final Map.Entry<String, String> entry : RX_MICRO_MODULES.entrySet()) {
            final String root = entry.getValue();
            createDirectoriesIfNeeded(root);
            clearInputDir(root);
            clearOutputDir(root);
        }
    }

    private static void createDirectoriesIfNeeded(final String rootPath) throws IOException {
        final File root = new File(rootPath);
        if (!root.exists()) {
            forceMkdir(root);
        }
        final File input = new File(root + "/input");
        if (!input.exists()) {
            forceMkdir(input);
        }
        final File output = new File(root + "/output");
        if (!output.exists()) {
            forceMkdir(output);
        }
        if (!rootPath.contains(DOCUMENTATION_ASCIIDOC)) {
            final File rxMicroOutput = new File(root + "/output/rxmicro");
            if (!rxMicroOutput.exists()) {
                forceMkdir(rxMicroOutput);
            }
        }
    }

    private static void clearInputDir(final String root) throws IOException {
        final File input = new File(root + "/input");
        for (final File packageName : requireNonNull(input.listFiles())) {
            deleteDirectory(packageName);
            System.out.println("Directory erased: " + packageName.getAbsolutePath());
        }
    }

    private static void clearOutputDir(final String root) throws IOException {
        final File output = new File(root + "/output");
        for (final File packageName : requireNonNull(output.listFiles())) {
            deleteDirectory(packageName);
            System.out.println("Directory erased: " + packageName.getAbsolutePath());
        }
    }

    private static void copyExamples() throws IOException {
        for (final File exampleProject : getExampleRootFolders()) {
            for (final Map.Entry<String, String> entry : RX_MICRO_MODULES.entrySet()) {
                if (exampleProject.getAbsolutePath().contains(entry.getKey())) {
                    if (!new File(exampleProject.getAbsolutePath(), "skip").exists()) {
                        final String srcRoot = format("?/src/main/java", exampleProject.getAbsolutePath());
                        if (new File(srcRoot).exists()) {
                            final String rootPackage = defineRootPackage(srcRoot);
                            copyOutputOrDocs(exampleProject, entry, rootPackage);
                            copyInput(srcRoot, rootPackage, entry.getValue() + "/input");
                            break;
                        }
                    }
                }
            }
        }
    }

    private static List<File> getExampleRootFolders() {
        final List<File> files = new ArrayList<>();
        for (final File exampleProject : requireNonNull(new File(EXAMPLES_ROOT_DIR_PATH).listFiles())) {
            if (exampleProject.getName().startsWith("group-")) {
                files.addAll(List.of(requireNonNull(exampleProject.listFiles())));
            } else {
                files.add(exampleProject);
            }
        }
        return files;
    }

    private static void copyOutputOrDocs(final File exampleProject,
                                         final Map.Entry<String, String> entry,
                                         final String rootPackage) throws IOException {
        final String destRoot = format("?/target/generated-sources", exampleProject.getAbsolutePath());
        if (!new File(destRoot).exists()) {
            throw new InvalidStateException("Generated source code not found: '?'. Run `mvn compile`", destRoot);
        }
        if (!entry.getKey().contains(DOCUMENTATION_ASCIIDOC)) {
            final List<String> exclude = new ArrayList<>();
            if (entry.getKey().contains(DATA_R2DBC_POSTGRESQL)) {
                exclude.add("$$RepositoryFactoryImpl.java");
            } else if (entry.getKey().contains(DATA_MONGO)) {
                exclude.add("$$RepositoryFactoryImpl.java");
            } else if (entry.getKey().contains(CDI)) {
                exclude.addAll(List.of(
                        "$$RestControllerAggregatorImpl.java",
                        "$$RestClientFactoryImpl.java",
                        "$$RepositoryFactoryImpl.java",
                        "RestController.java",
                        "Repository.java",
                        "RESTClient.java",
                        "RestClient.java"
                ));
            }
            /*if (!exampleProject.getName().contains(UNNAMED_MODULE_PREFIX) &&
                    !exampleProject.getName().contains("extendable-model")) {
                exclude.add("$$EnvironmentCustomizer.java");
            }*/
            final File moduleInfoFile = new File(format("?/src/main/java", exampleProject.getAbsolutePath()), MODULE_INFO_JAVA);
            copyOutput(moduleInfoFile, destRoot, rootPackage, entry.getValue() + "/output", exclude.toArray(new String[0]));
        }
        final String genDocsRoot = format("?/src/main/asciidoc", exampleProject.getAbsolutePath());
        if (new File(genDocsRoot).exists()) {
            copyDocs(
                    exampleProject.getAbsolutePath(),
                    rootPackage,
                    entry.getValue() + "/input",
                    entry.getValue() + "/output",
                    exampleProject.getName().contains("pomxml")
            );
        }
    }

    private UpdateRxMicroTestFiles() {
    }

    public static void main(final String[] args) throws IOException {
        clearInputOutputDirs();
        copyExamples();
        System.out.println("Completed");
    }

}
