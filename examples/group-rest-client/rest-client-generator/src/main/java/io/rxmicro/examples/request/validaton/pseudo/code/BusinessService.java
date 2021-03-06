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

package io.rxmicro.examples.request.validaton.pseudo.code;

import io.rxmicro.http.error.ValidationException;

import static io.rxmicro.rest.client.RestClientFactory.getRestClient;

public final class BusinessService {

    private final RESTClient restClient = getRestClient(RESTClient.class);

    public void validateUsingException() {
        // tag::THROW_EXCEPTION[]
        try {
            restClient.put("not_valid_email");
        } catch (final ValidationException e) {
            // do something with ValidationException
        }
        // end::THROW_EXCEPTION[]
    }

    public void validateUsingReactiveSignal() {
        // tag::RETURN_ERROR_SIGNAL[]
        restClient.put("not_valid_email")
                .exceptionally(throwable -> {
                    // do something with ValidationException
                    return null;
                });
        // end::RETURN_ERROR_SIGNAL[]
    }
}
