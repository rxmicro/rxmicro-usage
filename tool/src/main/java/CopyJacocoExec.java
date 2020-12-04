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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CopyJacocoExec {

    private static int copiedFiles;

    public static void main(final String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println("Missing source and destination directories");
            System.exit(1);
        }
        final File sourceDirectory = new File(args[0]).getAbsoluteFile();
        if (!sourceDirectory.exists()) {
            System.err.println("Source directory not found: " + sourceDirectory.getAbsolutePath());
            System.exit(2);
        }
        final File destinationDirectory = new File(args[1]).getAbsoluteFile();
        if (!destinationDirectory.exists()) {
            System.err.println("Destination directory not found: " + destinationDirectory.getAbsolutePath());
            System.exit(3);
        }

        System.out.println("Source directory: " + sourceDirectory.getAbsolutePath());
        System.out.println("Destination directory: " + destinationDirectory.getAbsolutePath());

        findAndCopyJacocoFiles(sourceDirectory, destinationDirectory);

        System.out.println("Copy successful. Copied " + copiedFiles + " file(s)");
    }

    private static void findAndCopyJacocoFiles(final File sourceDirectory,
                                               final File destinationDirectory) throws IOException {
        final File[] files = sourceDirectory.listFiles();
        if (files != null) {
            for (final File file : files) {
                if (file.isDirectory()) {
                    findAndCopyJacocoFiles(file, destinationDirectory);
                } else if ("jacoco.exec".equals(file.getName())) {
                    copyJacocoFile(file, destinationDirectory);
                }
            }
        }
    }

    private static void copyJacocoFile(final File jacocoFile,
                                       final File destinationDirectory) throws IOException {
        final File exampleProjectDirectory = jacocoFile.getParentFile().getParentFile();
        final String destinationFileName = exampleProjectDirectory.getName() + "-" + jacocoFile.getName();
        Files.copy(
                jacocoFile.toPath(),
                Paths.get(destinationDirectory.getAbsolutePath() + "/" + destinationFileName),
                REPLACE_EXISTING
        );
        copiedFiles++;
    }
}

