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

package io.rxmicro.util.doc;

import java.util.ArrayList;
import java.util.List;

public final class WordUtils {

    public static List<String> getWords(final String line) {
        final List<String> words = new ArrayList<>();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            final char ch = line.charAt(i);
            if (" ;:.,/?|~`!@#$%^&*()_+=-{}[]<>\\\"'\n\t\r".indexOf(ch) != -1) {
                if (sb.length() > 0) {
                    addWord(sb.toString(), words);
                    sb.delete(0, sb.length());
                }
            } else {
                sb.append(ch);
            }
        }
        return words;
    }

    private static void addWord(final String word,
                                final List<String> words) {
        for (int i = 0; i < word.length(); i++) {
            final char ch = word.charAt(i);
            if (ch > 127) {
                words.add(word);
                return;
            }
        }
    }

    private WordUtils() {
    }
}
