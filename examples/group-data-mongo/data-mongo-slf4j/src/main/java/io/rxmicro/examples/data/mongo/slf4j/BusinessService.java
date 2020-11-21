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

package io.rxmicro.examples.data.mongo.slf4j;

import io.rxmicro.examples.data.mongo.slf4j.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static io.rxmicro.data.RepositoryFactory.getRepository;

public final class BusinessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessService.class);

    private final DataRepository repository = getRepository(DataRepository.class);

    public Account getAdminAccount() {
        final Optional<Account> optionalAccount =
                repository.findByEmail("richard.hendricks@piedpiper.com").blockOptional();
        if (optionalAccount.isEmpty()) {
            LOGGER.warn("Admin account not found");
            throw new IllegalStateException("Admin account not found");
        } else {
            LOGGER.debug("Admin account exists");
            return optionalAccount.get();
        }
    }
}
