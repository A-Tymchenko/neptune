package com.ra.shop;

import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.repository.implementation.WarehouseRepositoryImpl;
import com.ra.shop.model.Warehouse;
import org.junit.jupiter.api.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.*;

import static com.ra.shop.enums.ExceptionMessage.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WarehouseRepositoryMockTest {

    private JdbcTemplate mockJdbcTemplate;
    private WarehouseRepositoryImpl warehouseDao;
    private PreparedStatement mockStatement;
    private KeyHolder mockKeyHolder;
    private Warehouse warehouse;

    @BeforeEach
    void init() {
        mockJdbcTemplate = mock(JdbcTemplate.class);
        warehouseDao = new WarehouseRepositoryImpl(mockJdbcTemplate);
        mockStatement = mock(PreparedStatement.class);
        mockKeyHolder = mock(GeneratedKeyHolder.class);
        warehouse = new Warehouse("Lola", Double.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * testing create method to return the warehouse.
     *
     * @throws RepositoryException exception
     */
    @Test
    void whenCreateMethodExecutedThenCorrectEntityReturns() throws RepositoryException {
        when(mockJdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);
        when(mockKeyHolder.getKey()).thenReturn(1L);
        Warehouse warehouseCreated = warehouseDao.create(warehouse);
        warehouse.setIdNumber((Long) mockKeyHolder.getKey());
        assertAll(
                () -> assertEquals(warehouseCreated.getIdNumber(), warehouse.getIdNumber()),
                () -> assertEquals(warehouseCreated.getAmount(), warehouse.getAmount()),
                () -> assertEquals(warehouseCreated.getName(), warehouse.getName()),
                () -> assertEquals(warehouseCreated.getPrice(), warehouse.getPrice())
        );
    }

    @Test
    void whenUpdateMethodExecutedThenCorrectEntityReturns() throws RepositoryException {
        final String updateQuery = "UPDATE warehouse SET name = ?, price = ?, amount = ? WHERE id = ?";
        doAnswer(invocation -> {
            ((PreparedStatementSetter) invocation.getArguments()[1]).setValues(mockStatement);
            return null;
        }).when(mockJdbcTemplate).update(eq(updateQuery), any(PreparedStatementSetter.class));
        warehouse.setIdNumber(1L);
        Warehouse updatedWarehouse = warehouseDao.update(warehouse);
        updatedWarehouse.setIdNumber(1L);
        assertAll(
                () -> assertEquals(warehouse.getIdNumber(), updatedWarehouse.getIdNumber()),
                () -> assertEquals(warehouse.getName(), updatedWarehouse.getName()),
                () -> assertEquals(warehouse.getPrice(), updatedWarehouse.getPrice()),
                () -> assertEquals(warehouse.getAmount(), updatedWarehouse.getAmount())
        );
    }

    @Test
    void whenDeleteCorrectlyExecutedThenReturnTrue() throws RepositoryException {
        warehouse.setIdNumber(1L);
        when(mockJdbcTemplate.update("DELETE FROM warehouse WHERE id = ?", warehouse.getIdNumber()))
                .thenReturn(1);
        boolean result = warehouseDao.delete(1L);
        assertTrue(result);
    }

    @Test
    void whenDeleteCorrectlyExecutedThenReturnFalse() throws RepositoryException {
        warehouse.setIdNumber(1L);
        when(mockJdbcTemplate.update("DELETE FROM warehouse WHERE id = ?", warehouse.getIdNumber()))
                .thenReturn(0);
        boolean result = warehouseDao.delete(1L);
        assertFalse(result);
    }

    @Test
    void whenGetByIdCorrectlyExecutedThenReturnWarehouse() throws RepositoryException {
        warehouse.setIdNumber(1L);
        when(mockJdbcTemplate.queryForObject(eq("SELECT * FROM warehouse WHERE id = ?"), any(Object[].class),
                any(RowMapper.class))).thenReturn(warehouse);
        Warehouse warehouseGet = warehouseDao.get(warehouse.getIdNumber());
        assertEquals(warehouse, warehouseGet);
    }

    @Test
    void whenGetAllCorrectlyThenReturnList() throws RepositoryException {
        List<Map<String, Object>> list = getListFromGetAllMethod();
        when(mockJdbcTemplate.queryForList("SELECT * FROM warehouse")).thenReturn(list);

        List<Warehouse> createdList = warehouseDao.getAll();
        List<Warehouse> expectedList = warehouseDao.getWarehouseFromListOfMap(list);
        System.out.println(expectedList);
        System.out.println(createdList);
        assertEquals(createdList, expectedList);
    }

    /**
     * testing create method to throw FAILED_TO_CREATE_NEW_SHOP Exception.
     */
    @Test
    void whenCreateMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(RepositoryException.class, () -> {
            when(mockJdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenThrow(new DataAccessException(""){});
            warehouseDao.create(warehouse);
        });

        assertEquals(exception.getMessage(), FAILED_TO_CREATE_NEW_WAREHOUSE.getMessage());
    }

    /**
     * testing update method to throw FAILED_TO_UPDATE_SHOP Exception.
     */
    @Test
    void whenUpdateMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(RepositoryException.class, () -> {
            when(mockJdbcTemplate.update(any(String.class), any(PreparedStatementSetter.class))).thenThrow(new DataAccessException(""){});
            warehouseDao.update(warehouse);
        });

        assertEquals(exception.getMessage(), FAILED_TO_UPDATE_WAREHOUSE.getMessage());
    }

    /**
     * testing delete method to throw FAILED_TO_DELETE_SHOP Exception.
     */
    @Test
    void whenDeleteMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(RepositoryException.class, () -> {
            when(mockJdbcTemplate.update(any(String.class), any(Long.class))).thenThrow(new DataAccessException(""){});
            warehouse.setIdNumber(1L);
            warehouseDao.delete(warehouse.getIdNumber());
        });

        assertEquals(exception.getMessage(), FAILED_TO_DELETE_WAREHOUSE.getMessage());
    }

    /**
     * testing getAll method to throw FAILED_TO_GET_ALL_SHOP Exception.
     */
    @Test
    void whenGetAllMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(RepositoryException.class, () -> {
            when(mockJdbcTemplate.queryForList(any(String.class))).thenThrow(new DataAccessException(""){});
            warehouseDao.getAll();
        });

        assertEquals(exception.getMessage(), FAILED_TO_GET_ALL_WAREHOUSE.getMessage());
    }

    /**
     * testing get method to throw FAILED_TO_GET_SHOP_BY_ID Exception.
     */
    @Test
    void whenGetByIdThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(RepositoryException.class, () -> {
            when(mockJdbcTemplate.queryForObject(any(String.class), any(Object[].class), any(RowMapper.class))).thenThrow(new DataAccessException(""){});
            warehouseDao.get(1L);
        });

        assertEquals(exception.getMessage(), FAILED_TO_GET_WAREHOUSE_BY_ID.getMessage() + " 1");
    }

    private List getListFromGetAllMethod() {
        List<Map> listFromQuery = new LinkedList<>();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("idNumber", warehouse.getIdNumber());
        map.put("name", warehouse.getName());
        map.put("price", warehouse.getPrice());
        map.put("amount", warehouse.getAmount());
        listFromQuery.add(map);
        return listFromQuery;
    }
}
