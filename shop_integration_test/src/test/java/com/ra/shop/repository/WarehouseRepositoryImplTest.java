package com.ra.shop.repository;

import com.ra.shop.config.ShopConfiguration;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Warehouse;
import com.ra.shop.repository.implementation.WarehouseRepositoryImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ShopConfiguration.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/create_table.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/drop_table.sql")
public class WarehouseRepositoryImplTest {

    @Autowired
    private WarehouseRepositoryImpl warehouseRepository;

    private static Warehouse warehouse;

    @BeforeAll
    static void init() {
        warehouse = new Warehouse("Lol", 1.1, 2);
    }

    @Test
    void whenCreateTableThenNewWarehouseMustReturn() throws RepositoryException {
        warehouseRepository.create(warehouse);
        assertEquals(1L, (long) warehouse.getIdNumber());
    }

    @Test
    void whenUpdateThenUpdatedWarehouseReturns() throws RepositoryException {
        Warehouse expectedWarehouse = warehouseRepository.create(warehouse);
        expectedWarehouse.setName("AloGarage");

        assertEquals(expectedWarehouse, warehouseRepository.update(expectedWarehouse));
    }

    @Test
    void whenDeleteFalseThenReturnFalse() throws RepositoryException {
        Warehouse createdWarehouse = warehouseRepository.create(warehouse);
        warehouseRepository.delete(createdWarehouse.getIdNumber());

        assertFalse(warehouseRepository.delete(createdWarehouse.getIdNumber()));
    }

    @Test
    void whenDeleteCorrectlyThenDeleteAndReturnTrue() throws RepositoryException {
        Warehouse createdWarehouse1 = warehouseRepository.create(warehouse);

        assertTrue(warehouseRepository.delete(createdWarehouse1.getIdNumber()));
    }

    @Test
    void whenGetAllThenWarehousesMustReturn() throws RepositoryException {
        Warehouse[] warehouses = getWarehouses();
        List<Warehouse> expectedList = new ArrayList<>();
        Collections.addAll(expectedList, warehouses);
        addWarehouses(warehouses);

        assertEquals(expectedList.size(), warehouseRepository.getAll().size());
    }

    private void addWarehouses(Warehouse[] warehouses) throws RepositoryException {
        for (int i = 0; i < warehouses.length; i++) {
            warehouseRepository.create(warehouses[i]);
        }
    }

    private Warehouse[] getWarehouses() {
        return new Warehouse[]{
                new Warehouse("loha", 1.1, 1),
                new Warehouse("gosha", 1.1, 1),
                new Warehouse("moisha", 1.1, 1)
        };
    }
}
