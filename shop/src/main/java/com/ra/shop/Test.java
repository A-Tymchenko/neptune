package com.ra.shop;

import com.ra.shop.connection.ConnectionFactory;
import com.ra.shop.dao.WarehouseDao;
import com.ra.shop.dao.exception.WarehouseDaoException;
import com.ra.shop.dao.implementation.WarehouseDaoImpl;
import com.ra.shop.wharehouse.Warehouse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {

    public static void main(String[] args) throws IOException, SQLException, WarehouseDaoException {
        ConnectionFactory factory = ConnectionFactory.buildConnectionFactory();
        Connection connection = factory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS warehouse (\n" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "name VARCHAR(255),\n" +
                "price DOUBLE,\n" +
                "amount INT" +
                ");");
        System.out.println(preparedStatement.executeUpdate());
        Warehouse w1= new Warehouse("l", 2.0, 1);
        WarehouseDaoImpl warehouseDao = new WarehouseDaoImpl(factory);
        Warehouse warehouse = warehouseDao.create(w1);
        System.out.println(warehouse);
    }

}
