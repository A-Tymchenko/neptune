package com.ra.project.service;

import com.ra.project.config.ConnectionFactory;
import com.ra.project.exceptions.InvalidOrderIdException;
import com.ra.project.model.Order;
import com.ra.project.repository.IRepository;
import com.ra.project.repository.OrderRepositoryImpl;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName(value = "Integration test for order services.")
class OrdersServiceIT {

    private IRepository<Order> orderIRepository;
    private IService<Order> service;
    private static ConnectionFactory connectionFactory;

    @BeforeAll
    static void init() throws IOException {
        connectionFactory = ConnectionFactory.getInstance();
    }

    @BeforeEach
    void initForEach() throws SQLException, IOException {
        orderIRepository = new OrderRepositoryImpl(connectionFactory);
        service = new OrderServiceImpl(orderIRepository);
        RunScript.execute(connectionFactory.getConnection(), new FileReader(new File("/home/reed/IdeaProjects/neptune/shop/src/main/resources/createOrdersTable.sql")));
    }

    @AfterEach
    void tearDown() throws SQLException, FileNotFoundException {
        RunScript.execute(connectionFactory.getConnection(), new FileReader(new File("/home/reed/IdeaProjects/neptune/shop/src/main/resources/dropOrdersTable.sql")));
        service = null;
        orderIRepository = null;
    }

    @AfterAll
    static void tearDownAfterClass() {
        connectionFactory = null;
    }

    @Test
    void createOrderPositive() {
        Order order =
                new Order(1L, 100, 567d, false,
                        0, true);
        Integer created = service.create(order);
        assertNotNull(created);
        int insertedRowsNumber = 1;
        assertEquals(insertedRowsNumber, (int) created);
    }

    @Test
    void createNullOrderAndThrowNullPointerException() {
        Throwable throwable = assertThrows(NullPointerException.class, () -> {
            service.create(null);
            throw new NullPointerException();
        });
        assertNull(throwable.getMessage());
    }

    @Test
    void getOrderPositive() throws IllegalAccessException, InvalidOrderIdException {
        Order order = new Order(2L, 100, 567d, false, 0, true);
        service.create(order);
        Optional<Order> found = service.get(order.getId());
        assertNotNull(found);
        found.ifPresent(orderFromDB -> assertEquals(orderFromDB, order));
    }

    @Test
    void getOrderWithWrongIdAndThrowInvalidOrderIdException() {
        Throwable throwable = assertThrows(InvalidOrderIdException.class, () -> {
            service.get(0L);
            throw new InvalidOrderIdException("Invalid id : 0");
        });
        assertEquals("Invalid id : 0", throwable.getMessage());
    }

    @Test
    void updateOrderPositive() throws IllegalAccessException, InvalidOrderIdException {
        service.create(
                new Order(5L, 100, 567d, false, 0, true));
        Optional<Order> optional = service.get(5L);
        Order found = null;
        if (optional.isPresent()) {
            found = optional.get();
            found.setNumber(222);
            found.setPrice(999d);
            found.setDeliveryIncluded(true);
            found.setDeliveryCost(30);
            found.setExecuted(false);
            found.setId(optional.get().getId());
        }
        Integer updated = service.update(found);
        assertNotNull(updated);
        assertEquals(1, (int) updated);
    }

    @Test
    void updateOrderWithNullArgumentAndThrowNullPointerException() {
        Throwable throwable = assertThrows(NullPointerException.class, () -> {
            service.create(
                    new Order(6L, 100, 567d, false, 0, true));
            service.update(null);
            throw new NullPointerException();
        });
        assertNull(throwable.getMessage());
    }

    @Test
    void updateOrderWithWrongIdAndThrowInvalidOrderIdException() {
        Throwable throwable = assertThrows(InvalidOrderIdException.class, () -> {
            service.create(
                    new Order(7L, 100, 567d, false, 0, true));
            Optional<Order> optional = service.get(0L);
            throw new InvalidOrderIdException("Invalid id : 0");
        });
        assertEquals("Invalid id : 0", throwable.getMessage());
    }

    @Test
    void deleteOrderPositive() throws IllegalAccessException, InvalidOrderIdException {
        Order order = new Order(8L, 100, 567d, false, 0, true);
        service.create(order);
        Integer removed = service.delete(order.getId());
        assertEquals(1, (int) removed);
    }

    @Test
    void deleteOrderWithWrongIdAndThrowInvalidOrderIdException() {
        Throwable throwable = assertThrows(InvalidOrderIdException.class, () -> {
            service.delete(0L);
            throw new InvalidOrderIdException("Invalid id : 0");
        });
        assertEquals("Invalid id : 0", throwable.getMessage());
    }

    @Test
    void getAllOrders() throws InvalidOrderIdException {
        service.create(new Order(10L, 123, 997d, false, 0, false));
        service.create(new Order(11L, 156, 417d, true, 30, true));
        service.create(new Order(12L, 897, 200d, false, 50, true));
        List<Order> expected = new ArrayList<>();
        expected.add(service.get(10L).get());
        expected.add(service.get(11L).get());
        expected.add(service.get(12L).get());
        List<Order> actual = service.getAll();
        assertArrayEquals(expected.toArray(), actual.toArray());
    }
}