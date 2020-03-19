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

package io.rxmicro.examples.data.r2dbc.postgresql.close.resources;

import io.r2dbc.spi.Connection;
import org.mockito.invocation.Invocation;
import org.mockito.stubbing.Answer;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class Spies {

    private static Connection spyConnection;

    private static List<Publisher<Void>> closeConnectionPublishers = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public static Connection decorateConnection(final Connection realConnection) {
        spyConnection = spy(realConnection);
        when(spyConnection.close()).thenAnswer((Answer<Publisher<Void>>) invocation -> {
            final Publisher<Void> realPublisher = (Publisher<Void>) invocation.callRealMethod();
            final Publisher<Void> publisherSpy = spy(realPublisher);
            closeConnectionPublishers.add(publisherSpy);
            return publisherSpy;
        });
        return spyConnection;
    }

    public static void verifyCloseConnection() {
        verify(spyConnection, atLeast(1)).close();
        verifyAtLeastOneSuccessfulClose();
        resetSpies();
    }

    private static void verifyAtLeastOneSuccessfulClose() {
        for (final Publisher<Void> closeConnectionPublisher : closeConnectionPublishers) {
            for (final Invocation invocation : mockingDetails(closeConnectionPublisher).getInvocations()) {
                if ("subscribe".equals(invocation.getMethod().getName()) &&
                        Arrays.equals(invocation.getMethod().getParameterTypes(), new Class[]{Subscriber.class})) {
                    return;
                }
            }
        }
        fail("Connection is not closed");
    }

    private static void resetSpies() {
        spyConnection = null;
        closeConnectionPublishers.clear();
    }

    private Spies() {
    }
}
