package com.ra.shop.service;

public final class GoodSchemaSql {

    public GoodSchemaSql() {
    }
    public static final String CREATE_SCHEMA_SQL = "CREATE TABLE GOODS (ID NUMBER, NAME VARCHAR(100), "
            + "PRICE FLOAT)";

    public static final String DELETE_SCHEMA_SQL = "DROP TABLE GOODS";

}
