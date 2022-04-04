package com.royal.jdbc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author richard
 */
public abstract class SQL implements AutoCloseable {
	protected abstract Connection connection();
	
	public boolean run(String sql, Object... fields) throws SQLException {
			var stmt = connection().prepareStatement(sql);
			
			SQLHelperTreatMethods.marks(stmt, fields);

			return stmt.execute();
	}
	
	public ResultSet query(String sql, Object... fields) throws SQLException {
			var stmt = connection().prepareStatement(sql);
			
			SQLHelperTreatMethods.marks(stmt, fields);

			return stmt.executeQuery();
	}
	
	public int update(String sql, Object... fields) throws SQLException {
			var stmt = connection().prepareStatement(sql);
			
			SQLHelperTreatMethods.marks(stmt, fields);

			return stmt.executeUpdate();

	}

	@Override
	public void close() throws Exception {
		connection().close();
	}

	private static class SQLHelperTreatMethods {

		public static void marks(java.sql.PreparedStatement stmt, Object[] fields) throws SQLException {
			for (int i = 0, length = fields.length; i < length;) {
				final Object obj = fields[i++];

				if (obj instanceof String) {
					stmt.setString(i, (String) obj);
				} else if (obj instanceof Double) {
					stmt.setDouble(i, (Double) obj);
				} else if (obj instanceof Float) {
					stmt.setFloat(i, (Float) obj);
				} else if (obj instanceof Integer) {
					stmt.setInt(i, (Integer) obj);
				} else if (obj instanceof Long) {
					stmt.setLong(i, (Long) obj);
				} else if (obj instanceof Byte) {
					stmt.setByte(i, (Byte) obj);
				} else if (obj instanceof Short) {
					stmt.setShort(i, (Short) obj);
				} else if (obj instanceof Boolean) {
					stmt.setBoolean(i, (Boolean) obj);
				} else if (obj instanceof java.sql.Time) {
					stmt.setTime(i, (java.sql.Time) obj);
				} else if (obj instanceof java.sql.Timestamp) {
					stmt.setTimestamp(i, (java.sql.Timestamp) obj);
				} else if (obj instanceof java.util.Date) {
					stmt.setDate(i, new java.sql.Date(((java.util.Date) obj).getTime()));
				} else if (obj instanceof BigDecimal) {
					stmt.setBigDecimal(i, (BigDecimal) obj);
				} else if (obj == null) {
					stmt.setObject(i, null);
				} else {
					throw new RuntimeException("WTF");
				}
			}
		}
	}
	
	
}
