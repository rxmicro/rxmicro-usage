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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.util.tests.Settings.MODULE_INFO_JAVA;
import static io.rxmicro.util.tests.Settings.POM_XML;
import static io.rxmicro.util.tests.Settings.VIRTUAL_MODULE_INFO_JAVA;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.io.FileUtils.forceMkdir;

public final class Copies {

    public static void copyInput(final String srcRoot,
                                 final String rootPackage,
                                 final String destRoot) throws IOException {
        final File sourceDir = new File(srcRoot + "/" + rootPackage.replace(".", "/"));
        final File destRootDir = new File(destRoot + "/" + rootPackage);
        forceMkdir(destRootDir);
        final File moduleInfo = new File(srcRoot, MODULE_INFO_JAVA);
        if (moduleInfo.exists()) {
            copyFile(moduleInfo, new File(destRootDir, MODULE_INFO_JAVA.replace("-", "")));
        }
        final File virtualModuleInfo = new File(srcRoot, VIRTUAL_MODULE_INFO_JAVA);
        if (virtualModuleInfo.exists()) {
            copyFile(virtualModuleInfo, new File(destRootDir, VIRTUAL_MODULE_INFO_JAVA));
        }
        copyAllFiles(sourceDir, destRootDir);
    }

    public static void copyOutput(final String srcRoot,
                                  final String rootPackage,
                                  final String destRoot,
                                  final String... excludes) throws IOException {
        final File sourceDir = new File(srcRoot + "/" + rootPackage.replace(".", "/"));
        final File destRootDir = new File(destRoot + "/" + rootPackage);

        forceMkdir(destRootDir);
        copyAllFiles(sourceDir, destRootDir, excludes);
        copyRxMicroFolder(srcRoot, destRootDir, excludes);
    }

    public static void copyDocs(final String projectRoot,
                                final String rootPackage,
                                final String destInputRoot,
                                final String destOutputRoot,
                                final boolean copyPomXml) throws IOException {
        if (copyPomXml) {
            copyFile(new File(projectRoot, POM_XML), new File(destInputRoot + "/" + rootPackage, POM_XML));
        }

        final File genDocsRoot = new File(format("?/src/main/asciidoc", projectRoot));
        final File destRootDir = new File(destOutputRoot + "/" + rootPackage);

        forceMkdir(destRootDir);
        copyAllFiles(genDocsRoot, destRootDir, "_fragment");

        final File sourceFragments = new File(genDocsRoot.getAbsolutePath() + "/_fragment");
        if (sourceFragments.exists()) {
            copyAllFiles(sourceFragments, new File(format("?/src/main/asciidoc/_fragment", destInputRoot)));
        }
    }

    private static void copyAllFiles(final File sourceDir,
                                     final File destRootDir,
                                     final String... excludes) throws IOException {
        if (sourceDir.exists()) {
            for (final File file : requireNonNull(
                    sourceDir.listFiles((dir, name) -> Arrays.stream(excludes).noneMatch(f -> new File(dir, name).getAbsolutePath().contains(f))),
                    "Directory not found: " + sourceDir.getAbsolutePath())) {
                if (file.isDirectory()) {
                    copyAllFiles(file, new File(destRootDir.getAbsolutePath() + "/" + file.getName()), excludes);
                } else {
                    copyFile(file, new File(destRootDir, file.getName()));
                }
            }
        }
    }

    private static void copyRxMicroFolder(final String srcRoot,
                                          final File destRootDir,
                                          final String... excludes) throws IOException {
        final File rxMicroPackage = new File(srcRoot + "/rxmicro");
        if (rxMicroPackage.exists()) {
            for (final File file : requireNonNull(
                    rxMicroPackage.listFiles(
                            (dir, name) -> Arrays.stream(excludes).noneMatch(f -> new File(dir, name).getAbsolutePath().contains(f))
                    ),
                    "Directory not found: " + rxMicroPackage.getAbsolutePath())) {
                copyFile(file, new File(destRootDir, file.getName()));
            }
        }
    }

    private static void copyFile(final File srcFile,
                                 final File destFile) throws IOException {
        FileUtils.copyFile(srcFile, destFile);
        System.out.println(format("File '?' copied", srcFile.getAbsolutePath()));
    }

    private Copies() {
    }

}
