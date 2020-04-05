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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static io.rxmicro.common.util.Formats.format;
import static java.nio.charset.StandardCharsets.UTF_8;

public final class VerifyAllDocLinks {

    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException {
        final Document document = Jsoup.parse(new File("documentation/target/generated-docs/user-guide.html"), UTF_8.name());
        final Set<String> links = new HashSet<>();
        for (final Element a : document.select("a")) {
            final String href = a.attr("href");
            if (href.startsWith("http://") || href.startsWith("https://")) {
                if (href.contains("#")) {
                    links.add(href.substring(0, href.indexOf('#')));
                } else {
                    links.add(href);
                }
            }
        }

        System.out.println("Start");

        final HttpClient httpClient = HttpClient.newBuilder().build();

        final AtomicInteger counter = new AtomicInteger(links.size());
        for (final String link : links) {
            httpClient.sendAsync(HttpRequest.newBuilder()
                            .method("HEAD", HttpRequest.BodyPublishers.noBody())
                            .uri(new URI(link))
                            .build(),
                    HttpResponse.BodyHandlers.discarding()).thenApply(r -> {
                counter.decrementAndGet();
                if (r.statusCode() != 200) {
                    if (r.statusCode() == 404) {
                        System.err.println(format("? -> NOT_FOUND", link));
                    } else if (r.statusCode() == 301) {
                        System.err.println(format("? -> ? (301)", link, r.headers().firstValue("Location")));
                    } else {
                        System.err.println(format("? -> ?, ?", link, r.statusCode(), r.headers()));
                    }
                } /*else {
                    System.out.println(format("OK -> ?", link));
                }*/
                return null;
            });
        }

        while (counter.get() > 0) {
            TimeUnit.SECONDS.sleep(10);
        }
    }

}
