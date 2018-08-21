package com.ra.shop.repository.implementation;

import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Warehouse;
import org.junit.jupiter.api.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.*;

import static com.ra.shop.enums.ExceptionMessage.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class WarehouseRepositoryMockTest {

    private JdbcTemplate mockJdbcTemplate;
    private WarehouseRepositoryImpl warehouseDao;
    private PreparedStatement mockStatement;
    private KeyHolder mockKeyHolder;
    private static Warehouse TEST_WAREHOUSE = new Warehouse("Lola", Double.MIN_VALUE, Integer.MAX_VALUE);
    private static final Long DEFAULT_ID = 1L;

    @BeforeAll
    static void createSchema() {
        TEST_WAREHOUSE.setIdNumber(DEFAULT_ID);
    }

    @BeforeEach
    void init() {
        mockJdbcTemplate = mock(JdbcTemplate.class);
        warehouseDao = new WarehouseRepositoryImpl(mockJdbcTemplate);
        mockStatement = mock(PreparedStatement.class);
        mockKeyHolder = mock(GeneratedKeyHolder.class);
    }

    @Test
    void whenCreateMethodExecutedThenCorrectEntityReturns() throws RepositoryException {
        when(mockJdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);
        when(mockKeyHolder.getKey()).thenReturn(DEFAULT_ID);
        Warehouse warehouseCreated = warehouseDao.create(TEST_WAREHOUSE);
        TEST_WAREHOUSE.setIdNumber((Long) mockKeyHolder.getKey());

        assertTrue(warehouseCreated.equals(TEST_WAREHOUSE));
    }

    @Test
    void whenUpdateMethodExecutedThenCorrectEntityReturns() throws RepositoryException {
        final String updateQuery = "UPDATE warehouse SET name = ?, price = ?, amount = ? WHERE id = ?";
        doAnswer(invocation -> {
            ((PreparedStatementSetter) invocation.getArguments()[1]).setValues(mockStatement);
            return null;
        }).when(mockJdbcTemplate).update(eq(updateQuery), any(PreparedStatementSetter.class));
        Warehouse updatedWarehouse = warehouseDao.update(TEST_WAREHOUSE);
        updatedWarehouse.setIdNumber(DEFAULT_ID);

        assertTrue(TEST_WAREHOUSE.equals(updatedWarehouse));
    }

    @Test
    void whenDeleteCorrectlyExecutedThenReturnTrue() throws RepositoryException {
        when(mockJdbcTemplate.update("DELETE FROM warehouse WHERE id = ?", TEST_WAREHOUSE.getIdNumber()))
                .thenReturn(1);

        assertTrue(warehouseDao.delete(DEFAULT_ID));
    }

    @Test
    void whenDeleteCorrectlyExecutedThenReturnFalse() throws RepositoryException {
        when(mockJdbcTemplate.update("DELETE FROM warehouse WHERE id = ?", TEST_WAREHOUSE.getIdNumber()))
                .thenReturn(0);

        assertFalse(warehouseDao.delete(DEFAULT_ID));
    }

    @Test
    void whenGetByIdCorrectlyExecutedThenReturnWarehouse() throws RepositoryException {
        when(mockJdbcTemplate.queryForObject(eq("SELECT * FROM warehouse WHERE id = ?"), any(RowMapper.class),
                any(Object.class))).thenReturn(TEST_WAREHOUSE);

        assertEquals(TEST_WAREHOUSE, warehouseDao.get(TEST_WAREHOUSE.getIdNumber()));
    }

    @Test
    void whenGetAllCorrectlyThenReturnList() throws RepositoryException {
        when(mockJdbcTemplate.query(eq("SELECT * FROM warehouse"), any(BeanPropertyRowMapper.class)))
                .thenReturn(new ArrayList<>());

        assertTrue(warehouseDao.getAll().isEmpty());
    }

    @Test
    void whenCreateMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(RepositoryException.class, () -> {
            when(mockJdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class)))
                    .thenThrow(new DataAccessException("") {});
            warehouseDao.create(TEST_WAREHOUSE);
        });
        assertEquals(exception.getMessage(), FAILED_TO_CREATE_NEW_WAREHOUSE.getMessage());
    }

    @Test
    void whenUpdateMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(RepositoryException.class, () -> {
            when(mockJdbcTemplate.update(any(String.class), any(PreparedStatementSetter.class)))
                    .thenThrow(new DataAccessException("") {});
            warehouseDao.update(TEST_WAREHOUSE);
        });
        assertEquals(exception.getMessage(), FAILED_TO_UPDATE_WAREHOUSE.getMessage());
    }

    @Test
    void whenDeleteMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(RepositoryException.class, () -> {
            when(mockJdbcTemplate.update(any(String.class), any(Long.class))).thenThrow(new DataAccessException("") {});
            warehouseDao.delete(TEST_WAREHOUSE.getIdNumber());
        });
        assertEquals(exception.getMessage(), FAILED_TO_DELETE_WAREHOUSE.getMessage());
    }

    @Test
    void whenGetAllMethodCalledThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        doThrow(new DataAccessException("") {
        })
                .when(mockJdbcTemplate).query(anyString(), any(BeanPropertyRowMapper.class));
        Throwable exception = assertThrows(RepositoryException.class, () -> warehouseDao.getAll());

        assertEquals(exception.getMessage(), FAILED_TO_GET_ALL_WAREHOUSE.getMessage());
    }

    @Test
    void whenGetByIdThrowsSQLExceptionThenDaoExceptionMustBeThrown() {
        Throwable exception = assertThrows(RepositoryException.class, () -> {
            when(mockJdbcTemplate.queryForObject(any(String.class), any(RowMapper.class), any(Object.class)))
                    .thenThrow(new DataAccessException("") {});
            warehouseDao.get(DEFAULT_ID);
        });
        assertEquals(exception.getMessage(), FAILED_TO_GET_WAREHOUSE_BY_ID.getMessage() + " 1");
    }

}
