package com.ra.project.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.h2.tools.RunScript;

/**
 * Util class performs create and drop table operations.
 */
public class DatabaseUtils {

    /**
     * Method performs table creation.
     *
     * @param connection current connection to database.
     * @throws FileNotFoundException can be thrown if configuration file doesn`t exists.
     * @throws SQLException can be thrown if any operation with database can`t be performed.
     */
    public void createTable(final Connection connection) throws FileNotFoundException, SQLException {
        RunScript.execute(connection, new FileReader(".//src//main//resources//createOrdersTable.sql"));
    }

    /**
     * Method performs table dropping operation.
     *
     * @param connection current connection to database.
     * @throws FileNotFoundException can be thrown if configuration file doesn`t exists.
     * @throws SQLException can be thrown if any operation with database can`t be performed.
     */
    public void dropTable(final Connection connection) throws FileNotFoundException, SQLException {
        RunScript.execute(connection, new FileReader(".//src//main//resources//dropOrdersTable.sql"));
    }

}
