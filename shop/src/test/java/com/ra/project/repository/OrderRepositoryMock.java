package com.ra.project.repository;

import com.ra.project.config.ConnectionFactory;
import com.ra.project.exceptions.InvalidOrderIdException;
import com.ra.project.model.Order;
import com.ra.project.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderRepositoryMock {

    private static ConnectionFactory mockConnectionFactory;
    private Connection mockConnection;
    private static OrderServiceImpl orderServiceMock;

    @BeforeAll
    public static void initGlobal() {
        mockConnectionFactory = mock(ConnectionFactory.class);
        orderServiceMock = mock(OrderServiceImpl.class);
    }

    @Test
    public void createOrder() {
        Order order = new Order(21L, 897, 200d, false, 50, true);

        when(orderServiceMock.create(order)).thenReturn(1);
        int rowsInserted = orderServiceMock.create(order);
        assertEquals(1, rowsInserted);
    }

    @Test
    public void getOrder() throws InvalidOrderIdException {
        Order order = new Order(22L, 337, 250d, true, 50, false);
        when(orderServiceMock.get(order.getId())).thenReturn(Optional.of(order));
        Optional<Order> optional = orderServiceMock.get(order.getId());
        assertEquals(order, optional.get());
    }

    @Test
    public void update() throws InvalidOrderIdException {
        Order order = new Order(21L, 897, 200d, false, 50, true);
        Order forUpdate = new Order(order.getId(), 90, 1200d, false, 0, false);

        when(orderServiceMock.update(forUpdate)).thenReturn(1);
        int rowsUpdated = orderServiceMock.update(forUpdate);
        assertEquals(1, rowsUpdated);
    }

    @Test
    public void delete() throws InvalidOrderIdException {
        Order order = new Order(23L, 547, 21d, false, 0, true);
        when(orderServiceMock.delete(order.getId())).thenReturn(1);
        int rowsDeleted = orderServiceMock.delete(order.getId());
        assertEquals(1, rowsDeleted);
    }

    @Test
    public void getAllOrders() {
        Order[] orders = new Order[]{
                new Order(21L, 897, 200d, false, 50, true),
                new Order(21L, 897, 200d, false, 50, true),
                new Order(21L, 897, 200d, false, 50, true)
        };
        List<Order> expected = new ArrayList<>();
        Collections.addAll(expected, orders);
        when(orderServiceMock.getAll()).thenReturn(expected);
        List<Order> actual = orderServiceMock.getAll();
        assertArrayEquals(expected.toArray(), actual.toArray());
    }
}
