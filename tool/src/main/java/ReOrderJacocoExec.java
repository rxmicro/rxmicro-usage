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
import java.util.ArrayList;
import java.util.List;

public final class ReOrderJacocoExec {

    private static int errorCount;

    private static void findAllJacocoExec(final List<File> jacocoExecList,
                                          final File dir) {
        final File[] files = dir.listFiles();
        if (files != null) {
            for (final File item : files) {
                if (item.isDirectory()) {
                    findAllJacocoExec(jacocoExecList, item);
                } else if (item.isFile() && item.getName().endsWith("-jacoco.exec")) {
                    jacocoExecList.add(item.getAbsoluteFile());
                }
            }
        }
    }

    private static void moveAllFoundJacocoExecFilesToCurrentDir(final List<File> jacocoExecList,
                                                                final File currentDir) {
        for (final File file : jacocoExecList) {
            if (!file.getParent().equals(currentDir.getAbsolutePath())) {
                final File newName = new File(currentDir, file.getName());
                if (!file.renameTo(newName)) {
                    System.err.println("Can't rename the '" + file.getAbsolutePath() + "' file to '" + newName.getAbsolutePath() + "'");
                    errorCount++;
                }
            }
        }
    }

    private ReOrderJacocoExec() {
    }

    public static void main(final String[] args) {
        final File currentDir = new File(".").getAbsoluteFile();
        System.out.println("Current directory is " + currentDir);

        final List<File> jacocoExecList = new ArrayList<>();
        findAllJacocoExec(jacocoExecList, currentDir);
        System.out.println("Found " + jacocoExecList.size() + " jacoco.exec files");

        moveAllFoundJacocoExecFilesToCurrentDir(jacocoExecList, currentDir);
        if (errorCount == 0) {
            System.out.println("Reorder successful");
        } else {
            System.err.println("Reorder failed");
        }
        System.exit(errorCount);
    }
}

