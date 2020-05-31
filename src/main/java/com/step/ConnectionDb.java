package com.step;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDb {
    
    static final String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
    static final String user = "mihail";
    static final String password = "postgres";
    
    public ConnectionDb() {
        try {
            // Register JDBC driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    static Connection initConnection() {

        try {
            System.out.println("Connecting...");
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            System.out.println("ERROR! Cann't connect to db!" + ex.getMessage());
            return null;
        }
    }
}
