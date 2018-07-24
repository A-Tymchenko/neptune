package com.ra.shop;

import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Order;
import com.ra.shop.repository.implementation.OrderRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderRepositoryMockTest {

    private static OrderRepositoryImpl mockOrderRepository;

    @BeforeAll
    static void initGlobal() {
        mockOrderRepository = mock(OrderRepositoryImpl.class);
    }

    @Test
    void whenCreateOrderThenReturnCreatedOrder() throws RepositoryException {
        Order order = new Order(10, 100D, false, 0, true);
        when(mockOrderRepository.create(order)).thenReturn(order);
        Order created = mockOrderRepository.create(order);
        assertEquals(order, created);
    }

    @Test
    void whenGetOrderThenReturnOptionalOfOrder() throws RepositoryException {
        Order order = new Order(20, 220D,false, 0, true);
        when(mockOrderRepository.get(order.getId())).thenReturn(Optional.of(order));
        Optional<Order> optional = mockOrderRepository.get(order.getId());
        assertTrue(optional.isPresent());
        assertEquals(order, optional.get());
    }

    @Test
    void whenUpdateOrderThenReturnUpdatedOrder() throws RepositoryException {
        Order order = new Order(120, 1240D,true, 50, false);
        order.setPrice(1000D);
        when(mockOrderRepository.update(order)).thenReturn(order);
        Order updated = mockOrderRepository.update(order);
        assertEquals(order, updated);
        assertEquals(order.getPrice(), updated.getPrice());
    }

    @Test
    void whenDeleteOrderIsSuccessfulThenReturnTrue() throws RepositoryException {
        Order order = new Order(1320, 330D,
                true, 50, true);
        when(mockOrderRepository.delete(order.getId())).thenReturn(Boolean.TRUE);
        Boolean isRowDeleted = mockOrderRepository.delete(order.getId());
        assertTrue(isRowDeleted);
    }

    @Test
    void whenGetAllOrdersThenReturnListOfOrders() throws RepositoryException {
        Order[] orders = getOrders();
        List<Order> expected = new ArrayList<>();
        Collections.addAll(expected, orders);
        when(mockOrderRepository.getAll()).thenReturn(expected);
        List<Order> actual = mockOrderRepository.getAll();
        assertEquals(expected.size(), actual.size());
    }

    private long getRandomId() {
        return 123L;
    }

    private Order[] getOrders() {
        return new Order[]{
                new Order(90, 6330D, false, 0, true),
                new Order(80, 2330D, true, 50, true),
                new Order(70, 1330D, true, 50, false)
        };
    }
}
