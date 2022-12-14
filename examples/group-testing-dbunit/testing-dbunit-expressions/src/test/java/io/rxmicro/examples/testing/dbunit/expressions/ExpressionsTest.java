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

package io.rxmicro.examples.testing.dbunit.expressions;

import io.rxmicro.test.dbunit.ExpectedDataSet;
import io.rxmicro.test.dbunit.InitialDataSet;
import io.rxmicro.test.dbunit.TestDatabaseConfig;
import io.rxmicro.test.dbunit.junit.DbUnitTest;
import io.rxmicro.test.junit.RxMicroIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;

import static io.rxmicro.test.dbunit.TestDatabaseConfig.getCurrentTestDatabaseConfig;
import static io.rxmicro.test.junit.ExAssertions.assertInstantEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

@RxMicroIntegrationTest
@Testcontainers
@DbUnitTest
final class ExpressionsTest {

    private static final Calendar GREGORIAN_CALENDAR_WITH_UTC_TIME_ZONE = new GregorianCalendar(TimeZone.getTimeZone("UTC"));

    @Container
    private static final GenericContainer<?> POSTGRESQL_TEST_DB =
            new GenericContainer<>("rxmicro/postgres-test-db")
                    .withExposedPorts(5432);

    @BeforeAll
    static void beforeAll() {
        getCurrentTestDatabaseConfig()
                .setHost(POSTGRESQL_TEST_DB.getHost())
                .setPort(POSTGRESQL_TEST_DB.getFirstMappedPort());
    }

    // tag::null[]
    @Test
    @InitialDataSet(
            // <1>
            value = "dataset/with-null-expression-dataset.xml",
            // <2>
            executeStatementsBefore = "ALTER TABLE account ALTER COLUMN balance DROP NOT NULL")
    // <4>
    @ExpectedDataSet("dataset/with-null-expression-dataset.xml")
    void Should_set_and_compare_null_correctly() throws SQLException {
        try (Connection connection = getJDBCConnection()) {
            try (Statement st = connection.createStatement()) {
                try (ResultSet rs = st.executeQuery("SELECT balance FROM account WHERE id=1")) {
                    if (rs.next()) {
                        // <3>
                        assertNull(rs.getTimestamp(1, GREGORIAN_CALENDAR_WITH_UTC_TIME_ZONE));
                    } else {
                        fail("Test database does not contain account with id=1");
                    }
                }
            }
        }
    }
    // end::null[]

    // tag::now1[]
    @Test
    @InitialDataSet(
            // <1>
            value = "dataset/with-now-expression-dataset.xml",
            // <2>
            executeStatementsBefore = {
                    "ALTER TABLE \"order\" DROP CONSTRAINT IF EXISTS order_fk_account",
                    "ALTER TABLE \"order\" DROP CONSTRAINT IF EXISTS order_fk_product"
            })
    // <3>
    @ExpectedDataSet("dataset/with-now-expression-dataset.xml")
    void Should_set_and_compare_now_correctly() throws SQLException {
        try (Connection connection = getJDBCConnection()) {
            try (Statement st = connection.createStatement()) {
                try (ResultSet rs = st.executeQuery("SELECT created FROM \"order\" WHERE id=1")) {
                    if (rs.next()) {
                        // <4>
                        final Instant actual =
                                rs.getTimestamp(1, GREGORIAN_CALENDAR_WITH_UTC_TIME_ZONE).toInstant();
                        // <5>
                        assertInstantEquals(Instant.now(), actual);
                    } else {
                        fail("Test database does not contain order with id=1");
                    }
                }
            }
        }
    }
    // end::now1[]

    // tag::now2[]
    @Test
    @InitialDataSet(
            // <1>
            value = "dataset/empty-database.xml",
            // <2>
            executeStatementsBefore = {
                    "ALTER TABLE \"order\" DROP CONSTRAINT IF EXISTS order_fk_account",
                    "ALTER TABLE \"order\" DROP CONSTRAINT IF EXISTS order_fk_product"
            })
    // <4>
    @ExpectedDataSet("dataset/with-now-expression-dataset.xml")
    void Should_compare_now_correctly() throws SQLException {
        final String sql = "INSERT INTO \"order\" VALUES(?,?,?,?,?)";
        try (Connection connection = getJDBCConnection()) {
            try (PreparedStatement st = connection.prepareStatement(sql)) {
                st.setInt(1, 1);
                st.setInt(2, 1);
                st.setInt(3, 1);
                st.setInt(4, 10);
                final Timestamp now = Timestamp.from(Instant.now()); // <3>
                st.setTimestamp(5, now, GREGORIAN_CALENDAR_WITH_UTC_TIME_ZONE);
                st.executeUpdate();
            }
        }
    }
    // end::now2[]

    // tag::instant-interval[]
    @Test
    @InitialDataSet(
            // <1>
            value = "dataset/empty-database.xml",
            // <2>
            executeStatementsBefore = {
                    "ALTER TABLE \"order\" DROP CONSTRAINT IF EXISTS order_fk_account",
                    "ALTER TABLE \"order\" DROP CONSTRAINT IF EXISTS order_fk_product"
            })
    // <4>
    @ExpectedDataSet("dataset/with-instant-interval-expression-dataset.xml")
    void Should_compare_instant_interval_correctly() throws SQLException {
        final String sql = "INSERT INTO \"order\" VALUES(?,?,?,?,?)";
        try (Connection connection = getJDBCConnection()) {
            try (PreparedStatement st = connection.prepareStatement(sql)) {
                st.setInt(1, 1);
                st.setInt(2, 1);
                st.setInt(3, 1);
                st.setInt(4, 10);
                final Duration duration = Duration.ofSeconds(new Random().nextInt(10) + 1);
                final Timestamp now = Timestamp.from(Instant.now().plus(duration)); // <3>
                st.setTimestamp(5, now, GREGORIAN_CALENDAR_WITH_UTC_TIME_ZONE);
                st.executeUpdate();
            }
        }
    }
    // end::instant-interval[]

    // tag::int-interval[]
    @Test
    @InitialDataSet("dataset/empty-database.xml")
    // <2>
    @ExpectedDataSet("dataset/with-int-interval-expression-dataset.xml")
    void Should_compare_int_interval_correctly() throws SQLException {
        final String sql = "INSERT INTO product VALUES(?,?,?,?)";
        try (Connection connection = getJDBCConnection()) {
            try (PreparedStatement st = connection.prepareStatement(sql)) {
                st.setInt(1, 1);
                st.setString(2, "name");
                st.setBigDecimal(3, new BigDecimal("6200.00"));
                st.setInt(4, new Random().nextInt(10)); // <1>
                st.executeUpdate();
            }
        }
    }
    // end::int-interval[]

    private Connection getJDBCConnection() throws SQLException {
        final TestDatabaseConfig config = getCurrentTestDatabaseConfig();
        return DriverManager.getConnection(config.getJdbcUrl(), config.getUser(), config.getPassword().toString());
    }
}