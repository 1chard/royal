package com.royal.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

/**
 *
 * @author suporte
 */
public class MySQL extends SQL {

	final Connection connection;

	public MySQL(String url, String database, String user, String pass) throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new SQLException(e);
		}
		connection = DriverManager.getConnection("jdbc:" + "mysql" + "://" + url + "/" + database + "?autoReconnect=true", user, pass);

	}

	@Override
	protected Connection connection() {
		return connection;
	}
}
