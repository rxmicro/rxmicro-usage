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

package io.rxmicro.util.doc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static io.rxmicro.common.util.Formats.format;
import static java.nio.charset.StandardCharsets.UTF_8;

public final class VerifyAllDocLinks {

    public static void main(final String[] args) throws IOException, InterruptedException {
        final Document document = Jsoup.parse(new File("documentation/target/generated-docs/user-guide.html"), UTF_8.name());
        final Set<String> links = new TreeSet<>((o1, o2) -> {
            if (o1.startsWith("https://github.com/") && o2.startsWith("https://github.com/")) {
                return o1.compareTo(o2);
            } else if (!o1.startsWith("https://github.com/") && !o2.startsWith("https://github.com/")) {
                return o1.compareTo(o2);
            } else if (o1.startsWith("https://github.com/") && !o2.startsWith("https://github.com/")) {
                return -1;
            } else {
                return 1;
            }
        });
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

        System.out.println("Found " + links.size() + " links");
        Files.write(Paths.get("all_found.txt"), links);

        if (Files.exists(Paths.get("processed.txt"))) {
            final List<String> processed = Files.readAllLines(Paths.get("processed.txt"));
            System.out.println("Already processed: " + processed.size());
            links.removeAll(processed);
        }

        System.out.println("To process: " + links.size());
        System.out.println("Start");

        final HttpClient httpClient = HttpClient.newBuilder().build();

        final Deque<String> processingLinks = new LinkedList<>(links);
        final ExecutorService executorService = Executors.newFixedThreadPool(10);
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("processed.txt", true)))) {
            for (int i = 0; i < 10; i++) {
                executorService.submit(() -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        final String link;
                        synchronized (processingLinks) {
                            link = processingLinks.poll();
                        }
                        if (link == null) {
                            System.out.println(Thread.currentThread().getName() + " terminated");
                            return;
                        }
                        try {
                            final HttpResponse<Void> response = httpClient.sendAsync(HttpRequest.newBuilder()
                                            .method("HEAD", HttpRequest.BodyPublishers.noBody())
                                            .timeout(Duration.ofSeconds(30))
                                            .uri(new URI(link))
                                            .build(),
                                    HttpResponse.BodyHandlers.discarding()).join();
                            if (response.statusCode() != 200) {
                                if (response.statusCode() == 404) {
                                    System.err.println(
                                            format("? -> NOT_FOUND", link)
                                    );
                                } else if (response.statusCode() / 100 == 3) {
                                    System.err.println(
                                            format("? -> ? (?)", link, response.headers().firstValue("Location"), response.statusCode())
                                    );
                                } else {
                                    System.err.println(
                                            format("? -> ?, ?", link, response.statusCode(), response.headers())
                                    );
                                }
                            } else {
                                writer.println(link);
                                writer.flush();
                            }
                            try {
                                TimeUnit.MILLISECONDS.sleep((new Random().nextInt(10) + 1) * 300);
                            } catch (final InterruptedException ignore) {
                                // do nothing
                            }
                        } catch (final CompletionException ex) {
                            if (ex.getCause() instanceof HttpTimeoutException) {
                                System.err.println("Get link failed (timeout): " + link);
                            } else {
                                ex.printStackTrace();
                            }
                        } catch (final RuntimeException | URISyntaxException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
            do {
                TimeUnit.SECONDS.sleep(5);
                int size;
                synchronized (processingLinks) {
                    size = processingLinks.size();
                }
                if (size == 0) {
                    System.out.println("Completed");
                    executorService.shutdown();
                    return;
                } else {
                    System.out.println("Waiting for " + size + " responses");
                }
            } while (true);
        }
    }

}
