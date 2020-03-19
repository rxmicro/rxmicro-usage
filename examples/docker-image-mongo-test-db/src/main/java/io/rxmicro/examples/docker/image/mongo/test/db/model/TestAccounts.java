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

package io.rxmicro.examples.docker.image.mongo.test.db.model;

import java.math.BigDecimal;
import java.util.List;

public final class TestAccounts {

    public static final Account RICHARD_HENDRICKS = new Account(
            1L,
            "richard.hendricks@piedpiper.com",
            "Richard",
            "Hendricks",
            new BigDecimal("70000.00"),
            Role.CEO
    );

    public static final Account BERTRAM_GILFOYLE = new Account(
            2L,
            "bertram.gilfoyle@piedpiper.com",
            "Bertram",
            "Gilfoyle",
            new BigDecimal("20000.00"),
            Role.Systems_Architect
    );

    public static final Account DINESH_CHUGTAI = new Account(
            3L,
            "dinesh.chugtai@piedpiper.com",
            "Dinesh",
            "Chugtai",
            new BigDecimal("10000.00"),
            Role.Lead_Engineer
    );

    public static final Account CARLA_WALTON = new Account(
            4L,
            "carla.walton@piedpiper.com",
            "Carla",
            "Walton",
            new BigDecimal("5000.00"),
            Role.Engineer
    );

    public static final Account SANJAY_BASU = new Account(
            5L,
            "sanjay.basu@piedpiper.com",
            "Sanjay",
            "Basu",
            new BigDecimal("2000.00"),
            Role.Engineer
    );

    public static final Account ELIZABET_KIRSIPUU = new Account(
            6L,
            "elizabet.kirsipuu@piedpiper.com",
            "Elizabet",
            "Kirsipuu",
            new BigDecimal("3000.00"),
            Role.Engineer
    );

    public static List<Account> testAccounts() {
        return List.of(
                RICHARD_HENDRICKS,
                BERTRAM_GILFOYLE,
                DINESH_CHUGTAI,
                CARLA_WALTON,
                SANJAY_BASU,
                ELIZABET_KIRSIPUU
        );
    }

    private TestAccounts() {
    }
}
