package com.ra.shop;

public final class GoodsSchemaSql {

    public GoodsSchemaSql() {
    }
    public static final String CREATE_SCHEMA_SQL = "CREATE TABLE GOODS (ID IDENTITY, NAME VARCHAR(100), "
            + "BARCODE BIGINT, PRICE FLOAT)";

    public static final String DELETE_SCHEMA_SQL = "DROP TABLE GOODS";

}
