package com.royal.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author richard
 */
public abstract class SQL implements AutoCloseable {
    @Override
    public synchronized void close() throws Exception {
        connection().close();
    }


    public synchronized ResultSet query(String sql, Object... fields) throws SQLException {
        var stmt = connection().prepareStatement(sql);

        SQLHelperTreatMethods.marks(stmt, fields);

        return stmt.executeQuery();
    }

    public synchronized boolean run(String sql, Object... fields) throws SQLException {
        var stmt = connection().prepareStatement(sql);

        SQLHelperTreatMethods.marks(stmt, fields);

        return stmt.execute();
    }

    public synchronized int update(String sql, Object... fields) throws SQLException {
        var stmt = connection().prepareStatement(sql);

        SQLHelperTreatMethods.marks(stmt, fields);

        return stmt.executeUpdate();

    }

    protected abstract Connection connection();

    private static class SQLHelperTreatMethods {

        private SQLHelperTreatMethods() {
        }

        public static void marks(java.sql.PreparedStatement stmt, Object[] fields) throws SQLException {
            for (int i = 0, length = fields.length; i < length; ) {
                final Object obj = fields[i++];

                if (obj instanceof String string) {
                    stmt.setString(i, string);
                } else if (obj instanceof Double double1) {
                    stmt.setDouble(i, double1);
                } else if (obj instanceof Float float1) {
                    stmt.setFloat(i, float1);
                } else if (obj instanceof Integer integer) {
                    stmt.setInt(i, integer);
                } else if (obj instanceof Long long1) {
                    stmt.setLong(i, long1);
                } else if (obj instanceof Byte byte1) {
                    stmt.setByte(i, byte1);
                } else if (obj instanceof Short short1) {
                    stmt.setShort(i, short1);
                } else if (obj instanceof Boolean boolean1) {
                    stmt.setBoolean(i, boolean1);
                } else if (obj instanceof java.sql.Time time) {
                    stmt.setTime(i, time);
                } else if (obj instanceof java.sql.Timestamp timestamp) {
                    stmt.setTimestamp(i, timestamp);
                } else if (obj instanceof java.sql.Date date) {
                    stmt.setDate(i, date);
                } else if (obj instanceof BigDecimal bigDecimal) {
                    stmt.setBigDecimal(i, bigDecimal);
                } else if (obj == null) {
                    stmt.setObject(i, null);
                } else {
                    throw new RuntimeException("WTF: tipo " + obj.getClass().getName() + ": não aceito essa porra");
                }
            }
        }
    }


}
