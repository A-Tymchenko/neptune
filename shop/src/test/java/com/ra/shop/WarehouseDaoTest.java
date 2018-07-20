package com.ra.shop;

import com.ra.shop.connection.ConnectionFactory;
import com.ra.shop.dao.WarehouseDao;
import com.ra.shop.dao.exception.WarehouseDaoException;
import com.ra.shop.dao.implementation.WarehouseDaoImpl;
import com.ra.shop.tools.Tools;
import com.ra.shop.wharehouse.Warehouse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseDaoTest {

    private static final String CREATE_TABLE_WAREHOUSE = getSql("src/test/resources/warehouse_create_table.sql");
    private static final String DROP_TABLE_WAREHOUSE = getSql("src/test/resources/drop_table.sql");

    private static final String getSql(final String path) {
        String sqlQuery = "";
        try {
            Scanner sc = new Scanner(new File(path));

            while (sc.hasNextLine()) {
                sqlQuery += sc.nextLine();
            }
            return sqlQuery;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private WarehouseDao<Warehouse> warehouseDao;

    private Warehouse warehouse;

    @BeforeEach
    public void beforeTest() throws IOException, SQLException {
        createDataBaseTable();
        createWarehouse();
    }

    @AfterEach
    public void afterTest() throws IOException, SQLException {
        deleteDataBaseTable();
    }

    @Test
    public void whenCreateTableThenNewWarehouseMustReturn() throws WarehouseDaoException {
        Warehouse createdWarehouse = warehouseDao.create(warehouse);
        assertNotNull(createdWarehouse);
        Integer warehouseId = createdWarehouse.getIdNumber();
        assertNotNull(warehouseId);
        warehouse.setIdNumber(warehouseId);
        assertEquals(warehouse, createdWarehouse);
    }

    @Test
    public void whenUpdateThenUpdatedWarehouseReturns() throws WarehouseDaoException {
        Warehouse createdWarehouse = warehouseDao.create(warehouse);
        Warehouse expectedWarehouse = changeWarehouse(createdWarehouse);

        Warehouse updatedWarehouse = warehouseDao.update(createdWarehouse);
        assertEquals(expectedWarehouse, updatedWarehouse);
    }

    @Test
    public void whenDeleteFalseThenReturnFalse() throws WarehouseDaoException {
        Warehouse createdWarehouse = warehouseDao.create(warehouse);
        warehouseDao.delete(createdWarehouse);
        boolean result = warehouseDao.delete(createdWarehouse);

        assertFalse(result);
    }

    @Test
    public void whenDeleteThenDeleteAndReturnTrue() throws WarehouseDaoException {
        Warehouse createdWarehouse = warehouseDao.create(warehouse);
        boolean result = warehouseDao.delete(createdWarehouse);

        assertTrue(result);
    }

    @Test
    public void whenGetAllThenWarehousesMustReturn() throws WarehouseDaoException {
        List<Warehouse> expectedList = new ArrayList<>();
        Warehouse e1 = warehouseDao.create(warehouse);
        Warehouse e2 = warehouseDao.create(warehouse);
        Warehouse e3 = warehouseDao.create(warehouse);
        expectedList.add(e1);
        expectedList.add(e2);
        expectedList.add(e3);

        List<Warehouse> warehouses = warehouseDao.getAll();

        assertEquals(expectedList, warehouses);
    }

    @Test
    public void whenFillInWarehouseCalledThenWarehouseIsSetCorrectlyFromTheObject() throws WarehouseDaoException, IOException, SQLException {




    }

    private Warehouse changeWarehouse(Warehouse warehouse) {
        warehouse.setName("Aloha");
        warehouse.setPrice(2.2);
        warehouse.setAmount(2);
        return warehouse;
    }


    private void deleteDataBaseTable() throws IOException, SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE_WAREHOUSE);
        preparedStatement.executeUpdate();
    }

    private void createWarehouse() throws IOException {
        warehouseDao = new WarehouseDaoImpl(ConnectionFactory.getInstance());
        warehouse = Tools.creteWarehouse();
    }

    private void createDataBaseTable() throws IOException, SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_WAREHOUSE);
        preparedStatement.executeUpdate();
    }
}
