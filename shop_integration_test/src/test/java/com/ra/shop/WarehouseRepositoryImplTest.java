package com.ra.shop;

import com.ra.shop.config.ConnectionFactory;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Warehouse;
import com.ra.shop.repository.implementation.WarehouseRepositoryImpl;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseRepositoryImplTest {

    private static final String CREATE_TABLE_WAREHOUSE = "src/test/resources/create_table.sql";
    private static final String DROP_TABLE_WAREHOUSE = "src/test/resources/drop_table.sql";

    private WarehouseRepositoryImpl IRepository;
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

    /**
     * testing new warehouse creation.
     *
     * @throws RepositoryException exception
     */
    @Test
    public void whenCreateTableThenNewWarehouseMustReturn() throws RepositoryException {
        Warehouse createdWarehouse = IRepository.create(warehouse);
        assertNotNull(createdWarehouse);
        Long warehouseId = createdWarehouse.getIdNumber();
        assertNotNull(warehouseId);
        warehouse.setIdNumber(warehouseId);

        assertEquals(warehouse, createdWarehouse);
    }

    /**
     * testing update warehouse.
     *
     * @throws RepositoryException exception
     */
    @Test
    public void whenUpdateThenUpdatedWarehouseReturns() throws RepositoryException {
        Warehouse createdWarehouse = IRepository.create(warehouse);
        Warehouse expectedWarehouse = changeWarehouse(createdWarehouse);

        Warehouse updatedWarehouse = IRepository.update(createdWarehouse);
        assertEquals(expectedWarehouse, updatedWarehouse);
    }

    /**
     * testing delete warehouse twice.
     *
     * @throws RepositoryException exception
     */
    @Test
    public void whenDeleteFalseThenReturnFalse() throws RepositoryException {
        Warehouse createdWarehouse = IRepository.create(warehouse);
        IRepository.delete(createdWarehouse.getIdNumber());
        boolean result = IRepository.delete(createdWarehouse.getIdNumber());

        assertFalse(result);
    }

    /**
     * testing delete warehouse.
     *
     * @throws RepositoryException exception
     */
    @Test
    public void whenDeleteCorrectlyThenDeleteAndReturnTrue() throws RepositoryException {
        Warehouse createdWarehouse = IRepository.create(warehouse);
        boolean result = IRepository.delete(createdWarehouse.getIdNumber());

        assertTrue(result);
    }

    /**
     * testing getAll warehouses.
     *
     * @throws RepositoryException exception
     */
    @Test
    public void whenGetAllThenWarehousesMustReturn() throws RepositoryException {
        List<Warehouse> expectedList = new ArrayList<>();
        Warehouse e1 = IRepository.create(warehouse);
        Warehouse e2 = IRepository.create(warehouse);
        Warehouse e3 = IRepository.create(warehouse);
        expectedList.add(e1);
        expectedList.add(e2);
        expectedList.add(e3);

        List<Warehouse> warehouses = IRepository.getAll();

        assertEquals(expectedList, warehouses);
    }

    private Warehouse changeWarehouse(Warehouse warehouse) {
        warehouse.setName("Aloha");
        warehouse.setPrice(2.2);
        warehouse.setAmount(2);
        return warehouse;
    }


    private void deleteDataBaseTable() throws IOException, SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        RunScript.execute(connection, new FileReader(DROP_TABLE_WAREHOUSE));
    }

    private void createWarehouse() throws IOException {
        IRepository = new WarehouseRepositoryImpl(ConnectionFactory.getInstance());
        warehouse = new Warehouse("Lola", Double.MIN_VALUE, 2);
        warehouse.setIdNumber(1L);
    }

    private void createDataBaseTable() throws IOException, SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        RunScript.execute(connection, new FileReader(CREATE_TABLE_WAREHOUSE));
    }
}
