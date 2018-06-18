package com.ra.courses.project.utils;

public class DBUtils {

    /**
     * Method contains a query for table creation.
     * @return String.
     */
    public String createTable() {
        return "CREATE TABLE ORDERS("
                + "ID INT AUTO_INCREMENT PRIMARY KEY, "
                + "NUMBER INT, "
                + "PRICE DOUBLE, "
                + "DELIVERY_INCLUDED BOOLEAN, "
                + "DELIVERY_COST INT, "
                + "EXECUTED BOOLEAN)";
    }

    /**
     * Method contains a query which executes dropping table.
     * @return String;
     */
    public String dropTable() {
        return "DROP TABLE ORDERS IF EXISTS";
    }

}
