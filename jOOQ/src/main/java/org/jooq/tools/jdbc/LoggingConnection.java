/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Other licenses:
 * -----------------------------------------------------------------------------
 * Commercial licenses for this work are available. These replace the above
 * ASL 2.0 and offer limited warranties, support, maintenance, and commercial
 * database integrations.
 *
 * For more information, please visit: https://www.jooq.org/legal/licensing
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package org.jooq.tools.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.jooq.tools.JooqLogger;

/**
 * @author Lukas Eder
 */
public class LoggingConnection extends DefaultConnection {

    private static final JooqLogger log = JooqLogger.getLogger(LoggingConnection.class);

    public LoggingConnection(Connection delegate) {
        super(delegate);
    }

    @Override
    public Statement createStatement() throws SQLException {
        return new LoggingStatement(super.createStatement());
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return new LoggingStatement(super.createStatement(resultSetType, resultSetConcurrency));
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return new LoggingStatement(super.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability));
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        if (log.isDebugEnabled())
            log.debug("prepareStatement", sql);

        return super.prepareStatement(sql);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
        throws SQLException {
        if (log.isDebugEnabled())
            log.debug("prepareStatement", sql);

        return super.prepareStatement(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
        int resultSetHoldability) throws SQLException {
        if (log.isDebugEnabled())
            log.debug("prepareStatement", sql);

        return super.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        if (log.isDebugEnabled())
            log.debug("prepareStatement", sql);

        return super.prepareStatement(sql, autoGeneratedKeys);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        if (log.isDebugEnabled())
            log.debug("prepareStatement", sql);

        return super.prepareStatement(sql, columnIndexes);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        if (log.isDebugEnabled())
            log.debug("prepareStatement", sql);

        return super.prepareStatement(sql, columnNames);
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        if (log.isDebugEnabled())
            log.debug("prepareCall", sql);

        return super.prepareCall(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        if (log.isDebugEnabled())
            log.debug("prepareCall", sql);

        return super.prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        if (log.isDebugEnabled())
            log.debug("prepareCall", sql);

        return super.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        if (log.isDebugEnabled())
            log.debug("nativeSQL", sql);

        return super.nativeSQL(sql);
    }
}
