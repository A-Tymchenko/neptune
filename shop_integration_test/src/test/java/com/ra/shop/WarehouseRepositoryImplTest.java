package com.ra.shop;

import com.ra.shop.config.ConnectionFactory;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Warehouse;
import com.ra.shop.repository.implementation.WarehouseRepositoryImpl;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.*;

import java.io.*;
import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseRepositoryImplTest {

    private static final String CREATE_TABLE_WAREHOUSE = "src/test/resources/create_table.sql";
    private static final String DROP_TABLE_WAREHOUSE = "src/test/resources/drop_table.sql";

    private static Warehouse warehouse;
    private static WarehouseRepositoryImpl IRepository;

    @BeforeAll
    static void init() throws IOException {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        IRepository = new WarehouseRepositoryImpl(factory);
        warehouse = new Warehouse("Lol", 1.1, 2);
    }

    @BeforeEach
    void beforeTest() throws IOException, SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        RunScript.execute(connection, new FileReader(CREATE_TABLE_WAREHOUSE));
    }

    @AfterEach
    void afterTest() throws IOException, SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        RunScript.execute(connection, new FileReader(DROP_TABLE_WAREHOUSE));
    }

    /**
     * testing new warehouse creation.
     *
     * @throws RepositoryException exception
     */
    @Test
    void whenCreateTableThenNewWarehouseMustReturn() throws RepositoryException {
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
    void whenUpdateThenUpdatedWarehouseReturns() throws RepositoryException {
        Warehouse expectedWarehouse = IRepository.create(warehouse);
        expectedWarehouse.setName("AloGarage");

        Warehouse updatedWarehouse = IRepository.update(expectedWarehouse);
        assertEquals(expectedWarehouse, updatedWarehouse);
    }

    /**
     * testing delete warehouse twice.
     *
     * @throws RepositoryException exception
     */
    @Test
    void whenDeleteFalseThenReturnFalse() throws RepositoryException {
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
    void whenDeleteCorrectlyThenDeleteAndReturnTrue() throws RepositoryException {
        Warehouse createdWarehouse1 = IRepository.create(warehouse);
        boolean result = IRepository.delete(createdWarehouse1.getIdNumber());

        assertTrue(result);
    }

    /**
     * testing getAll warehouses.
     *
     * @throws RepositoryException exception
     */
    @Test
    void whenGetAllThenWarehousesMustReturn() throws RepositoryException {
        List<Warehouse> expectedList = new ArrayList<>();
        expectedList.add(IRepository.create(new Warehouse("loha", 1.1, 1)));
        expectedList.add(IRepository.create(new Warehouse("gosha", 1.1, 1)));
        expectedList.add(IRepository.create(new Warehouse("moisha", 1.1, 1)));
        List<Warehouse> warehouses = IRepository.getAll();
        assertEquals(expectedList, warehouses);
    }

    private Warehouse changeWarehouse(Warehouse warehouse) {
        warehouse.setName("Aloha");
        warehouse.setPrice(2.2);
        warehouse.setAmount(2);
        return warehouse;
    }
}