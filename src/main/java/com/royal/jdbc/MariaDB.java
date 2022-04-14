package com.royal.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author richard
 */
public class MariaDB extends SQL {

    final Connection connection;

    public MariaDB(String url, String database, String user, String pass) throws SQLException{
		connection = DriverManager.getConnection("jdbc:" + "mariadb" + "://" + url + "/" + database, user, pass);
    }
    
    @Override
    protected Connection connection() {
        return connection;
    }

}
