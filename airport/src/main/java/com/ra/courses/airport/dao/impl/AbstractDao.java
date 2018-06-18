package com.ra.courses.airport.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by anbo06131 on 6/15/2018.
 */
public class AbstractDao {

   private static final String JDBC_DRIVER = "org.h2.Driver";
   private static final String DB_URL = "jdbc:h2:mem:airport";

   private static final String USER = "";
   private static final String PASSWORD = "";

    protected Connection getConnection() throws Exception {
        try {
            Class.forName(JDBC_DRIVER);
            Connection result = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            result.setAutoCommit(false);
            return result;
        } catch (SQLException e) {
            throw new Exception("Can't create connection", e);
        }
    }
    
}
