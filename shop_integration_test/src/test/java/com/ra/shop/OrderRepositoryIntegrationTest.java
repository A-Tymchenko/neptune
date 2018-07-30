package com.ra.shop;

import com.ra.shop.config.ConnectionFactory;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Order;
import com.ra.shop.repository.implementation.OrderRepositoryImpl;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.*;

import java.io.*;
import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class OrderRepositoryIntegrationTest {

    private static final String CREATE_TABLE_ORDER = "src/test/resources/create_table.sql";
    private static final String DROP_TABLE_ORDER = "src/test/resources/drop_table.sql";
    private static ConnectionFactory factory;
    private static OrderRepositoryImpl repository;
    private static Connection connection;

    @BeforeAll
    static void initGlobal() throws IOException, SQLException {
        factory = ConnectionFactory.getInstance();
        connection = factory.getConnection();
        repository = new OrderRepositoryImpl(factory);
    }

    @BeforeEach
    void init() throws FileNotFoundException, SQLException {
        RunScript.execute(connection, new FileReader(CREATE_TABLE_ORDER));
    }

    @AfterEach
    void tearDown() throws FileNotFoundException, SQLException {
        dropTable(connection);
    }

    @AfterAll
    static void tearDownGlobal() {
        repository = null;
        connection = null;
        factory = null;
    }

    @Test
    void whenCreateOrderThenReturnCreatedOrder() throws RepositoryException {
        Order order = new Order(10, 100d, false, 0, false);
        Order created = repository.create(order);
        assertNotNull(created);
        assertEquals(order, created);
    }

    @Test
    void whenOrderCreationFailsThenThrowRepositoryException() {
        Order order = new Order(404, 40d, false, 0, true);
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            dropTable(connection);
            repository.create(order);
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenGetOrderThenReturnOptionalOfOrder() throws RepositoryException {
        Order order = new Order(20, 200d, true, 50, true);
        Order created = repository.create(order);
        System.out.println(created.getId());
        Optional<Order> optional = repository.get(created.getId());
        assertNotNull(optional);
        assertTrue(optional.isPresent());
        assertEquals(order, optional.get());
    }

    @Test
    void whenGetOrderWithNonExistingIdThenReturnOptionalEmpty() throws RepositoryException {
        Optional<Order> optional = repository.get(getRandomId());
        assertNotNull(optional);
        assertFalse(optional.isPresent());
        assertEquals(Optional.empty(), optional);
    }

    @Test
    void whenDropOrdersTableAndGetOrderThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            dropTable(connection);
            repository.get(getRandomId());
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenUpdateOrderThenReturnUpdatedOrder() throws RepositoryException {
        Order order = new Order(30, 30d, false, 0, true);
        repository.create(order);
        order.setPrice(150d);
        order.setDeliveryIncluded(true);
        order.setDeliveryCost(70);
        Order updated = repository.update(order);
        assertNotNull(updated);
        assertAll(() -> {
            assertEquals(150d, (double) order.getPrice());
            assertTrue(order.getDeliveryIncluded());
            assertEquals(70, (int) order.getDeliveryCost());
        });
    }

    @Test
    void whenDropOrdersTableAndUpdateNotExistingOrderThenThrowRepositoryException() {
        Order order = new Order(404, 40d, false, 0, true);
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            dropTable(connection);
            repository.update(order);
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenDeleteOrderAndOperationIsSuccessfulThenReturnTrue() throws RepositoryException {
        Order order = new Order(404, 40d, false, 0, true);
        repository.create(order);
        boolean isDeleted = repository.delete(order.getId());
        assertTrue(isDeleted);
        assertEquals(Optional.empty(), repository.get(order.getId()));
    }

    @Test
    void whenDropTableAndDeleteNonExistingOrderThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            dropTable(connection);
            repository.delete(getRandomId());
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenGetAllOrdersThenReturnListOfExistingOrders() throws RepositoryException {
        Order[] orders = getOrders();
        List<Order> expected = new ArrayList<>();
        Collections.addAll(expected, orders);
        addOrdersToDB(orders);
        List<Order> actual = repository.getAll();
        assertNotNull(actual);
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void whenDropOrdersTableAndCallGetAllMethodThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            dropTable(connection);
            repository.getAll();
        });
        assertNotNull(repositoryException);
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    private void addOrdersToDB(Order[] orders) throws RepositoryException {
        for (int i = 0; i < orders.length; i++) {
            repository.create(orders[i]);
        }
    }

    private Order[] getOrders() {
        return new Order[]{
            new Order(505, 50d, true, 50, true),
            new Order(606, 60d, false, 0, false),
            new Order(707, 70d, true, 70, true),
            new Order(808, 80d, false, 0, false),
            new Order(909, 90d, true, 100, true)
        };
    }

    public void dropTable(final Connection connection) throws FileNotFoundException, SQLException {
        RunScript.execute(connection, new FileReader(DROP_TABLE_ORDER));
    }

    private Long getRandomId() {
        return 123L;
    }

}
