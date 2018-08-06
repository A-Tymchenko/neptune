package com.ra.shop.repository;

import com.ra.shop.config.ShopConfiguration;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Order;
import com.ra.shop.repository.implementation.OrderRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ShopConfiguration.class})
@Sql(scripts = "classpath:create_table.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:drop_table.sql", executionPhase = AFTER_TEST_METHOD)
public class OrderRepositoryIntegrationTest {

    @Autowired
    private OrderRepositoryImpl repository;

    @Test
    void whenCreateOrderThenReturnCreatedOrder() throws RepositoryException {
        Order order = new Order(10, 90d, false, 0, false);
        Order created = repository.create(order);
        assertEquals(order, created);
    }

    @Test
    @Sql(scripts = "classpath:drop_table.sql", executionPhase = BEFORE_TEST_METHOD)
    void whenCreateOrderThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.create(new Order(1, 10d, false, 0, false));
        });
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenGetOrderThenReturnCorrectEntity() throws RepositoryException {
        Order order = new Order(10, 90d, false, 0, false);
        Order created = repository.create(order);
        Order found = repository.get(created.getId());
        assertEquals(order, created);
    }

    @Test
    @Sql(scripts = "classpath:drop_table.sql", executionPhase = BEFORE_TEST_METHOD)
    void whenGetOrderThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.get(1L);
        });
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenUpdateOrderThenReturnUpdatedOrder() throws RepositoryException {
        Order order = new Order(10, 90d, false, 0, false);
        Order created = repository.create(order);
        order.setPrice(900d);
        order.setDeliveryIncluded(true);
        order.setDeliveryCost(120);
        Order updated = repository.update(order);
        assertAll(() -> {
            assertEquals(created.getPrice(), updated.getPrice());
            assertEquals(created.getDeliveryIncluded(), updated.getDeliveryIncluded());
            assertEquals(created.getDeliveryCost(), updated.getDeliveryCost());
        });
    }

    @Test
    @Sql(scripts = "classpath:drop_table.sql", executionPhase = BEFORE_TEST_METHOD)
    void whenUpdateOrderThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.update(new Order(1, 10d, false, 0, false));
        });
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenDeleteOrderThenReturnTrueOnSuccessfulExecution() throws RepositoryException {
        Order order = new Order(10, 90d, false, 0, false);
        Order created = repository.create(order);
        boolean isDeleted = repository.delete(created.getId());
        assertTrue(isDeleted);
    }

    @Test
    @Sql(scripts = "classpath:drop_table.sql", executionPhase = BEFORE_TEST_METHOD)
    void whenDeleteOrderThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.delete(1L);
        });
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    @Test
    void whenGetAllOrdersThenReturnListOfOrders() throws RepositoryException {
        Order[] orders = getOrders();
        addAllOrdersToDB(orders);
        List<Order> actual = repository.getAll();
        assertEquals(3, actual.size());
    }

    @Test
    @Sql(scripts = "classpath:drop_table.sql", executionPhase = BEFORE_TEST_METHOD)
    void whenGetAllOrdersThenThrowRepositoryException() {
        Throwable repositoryException = assertThrows(RepositoryException.class, () -> {
            repository.getAll();
        });
        assertEquals(RepositoryException.class, repositoryException.getClass());
    }

    private void addAllOrdersToDB(Order[] orders) throws RepositoryException {
        for (int i = 0; i < orders.length; i++) {
            repository.create(orders[i]);
        }
    }

    private Order[] getOrders() {
        return new Order[]{
                new Order(110, 390d, true, 30, false),
                new Order(210, 490d, true, 50, true),
                new Order(310, 590d, false, 0, true)
        };
    }

}
