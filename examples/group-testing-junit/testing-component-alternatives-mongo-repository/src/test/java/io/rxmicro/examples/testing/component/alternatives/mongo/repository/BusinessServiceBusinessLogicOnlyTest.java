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

package io.rxmicro.examples.testing.component.alternatives.mongo.repository;

import io.rxmicro.examples.testing.component.alternatives.mongo.repository.model.Entity;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.junit.RxMicroComponentTest;
import io.rxmicro.test.mockito.junit.InitMocks;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// tag::content[]
@InitMocks
@RxMicroComponentTest(BusinessService.class)
final class BusinessServiceBusinessLogicOnlyTest {

    private BusinessService businessService;

    @Mock
    @Alternative
    private DataRepository dataRepository;

    @Mock
    private Entity entity;

    @Test
    void Should_return_Entity_data() {
        when(dataRepository.findById(1))
                .thenReturn(completedFuture(Optional.of(entity)));
        final Optional<Entity> result = businessService.findById(1).join();
        assertEquals(Optional.of(entity), result);
    }

    @Test
    void Should_return_Not_Found_error() {
        when(dataRepository.findById(1))
                .thenReturn(completedFuture(Optional.empty()));
        final Optional<Entity> result = businessService.findById(1).join();
        assertEquals(Optional.empty(), result);
    }
}
// end::content[]
