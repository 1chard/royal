package com.royal.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author suporte
 */
public class MySQL extends SQL{
    final Connection connection;
    
    public MySQL(String url, String database, String user, String pass) {
        try {
	    Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:" + "mysql" + "://" + url + "/" + database, user, pass);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
	}
    
    }
    
    @Override
    protected Connection connection() {
        return connection;
    }
}
