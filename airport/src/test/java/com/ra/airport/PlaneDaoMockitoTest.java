package com.ra.airport;

import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.helper.DataCreationHelper;
import com.ra.airport.dao.impl.PlaneDao;
import com.ra.airport.entity.Plane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.KeyHolder;


import static com.ra.airport.dao.exception.ExceptionMessage.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PlaneDaoMockitoTest {

    private static final String INSERT_PLANE_SQL = "INSERT INTO plane "
            + "(owner, model, type, platenumber) "
            + " VALUES(?,?,?,?)";
    private static final String UPDATE_PLANE_SQL = "UPDATE plane ";

    private static final String SELECT_PLANE_BY_ID_SQL = "SELECT * FROM plane WHERE planeId = ?";
    private static final String DELETE_PLANE_BY_ID_SQL = "DELETE FROM plane WHERE planeId = ?";
    private static final String SELECT_LAST_GENERATED_ID_SQL = "SELECT SCOPE_IDENTITY()";
    private static final String SELECT_ALL_PLANES_BY_ID_SQL = "SELECT * FROM plane";

    private PlaneDao planeDao;
    private Plane plane;
    private JdbcTemplate mockJdbcTemplate;
    private PreparedStatement mockPreparedStatement;
    private Connection mockConnection;


    @BeforeEach
    public void init() throws SQLException  {
        plane = DataCreationHelper.createPlane();
        mockPreparedStatement = mock(PreparedStatement.class);
        mockJdbcTemplate = mock(JdbcTemplate.class);
        planeDao = new PlaneDao(mockJdbcTemplate);
        mockConnection=mock(Connection.class);

        when(mockConnection.prepareStatement(INSERT_PLANE_SQL)).thenReturn(mockPreparedStatement);
        when(mockConnection.prepareStatement(UPDATE_PLANE_SQL)).thenReturn(mockPreparedStatement);
        when(mockJdbcTemplate.queryForObject(SELECT_LAST_GENERATED_ID_SQL, Integer.class)).thenReturn(1);
        when(mockJdbcTemplate.queryForObject(eq(SELECT_PLANE_BY_ID_SQL), any(Object[].class), any(RowMapper.class))).thenReturn(plane);

    }



    @Test
    public void whenCreateThenCorrectSQLShouldBeExecutedAndCorrectEntityShouldBeReturned() throws AirPortDaoException {

        doAnswer(invocation -> {
            ((PreparedStatementCreator) invocation.getArguments()[0]).createPreparedStatement(mockConnection);
            return null; }).when(mockJdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
        Plane flightWithoutId = plane;
        flightWithoutId.setPlaneId(null);
        Plane result = planeDao.create(flightWithoutId);

        assertEquals(plane, result);
    }

    @Test
    public void whenUpdateThenCorrectSQLShouldBeExecutedAndCorrectEntityShouldBeReturned() throws AirPortDaoException {
        doAnswer(invocation -> {
            ((PreparedStatementCreator) invocation.getArguments()[1]).createPreparedStatement(mockConnection);
            return null;
        }).when(mockJdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
        Plane result = planeDao.update(plane);

        assertEquals(plane, result);
    }

    @Test
    public void whenDeleteThenCorrectSQLShouldBeExecutedAndTrueShouldBeReturned() throws AirPortDaoException{
        when(mockJdbcTemplate.update(DELETE_PLANE_BY_ID_SQL,1)).thenReturn(1);
        boolean result = planeDao.delete(plane);
        assertEquals(true, result);
    }

    @Test
    public void whenDeleteStatementExecuteReturnOThenFalseShouldBeReturned() throws  AirPortDaoException {
        when(mockJdbcTemplate.update(DELETE_PLANE_BY_ID_SQL,1)).thenReturn(0);
        boolean result = planeDao.delete(plane);

        assertEquals(false, result);
    }

    @Test
    public void whenGetAllThenCorrectSQLShouldBeExecutedAndCorrectListReturned() throws AirPortDaoException {
        when(mockJdbcTemplate.query(eq(SELECT_ALL_PLANES_BY_ID_SQL), any(RowMapper.class))).thenReturn(new ArrayList<>());
        List<Plane> planes = planeDao.getAll();

        assertTrue(planes.isEmpty());
    }

    @Test
    public void whenGetByIdReturnEmptyResultSetThenEmptyOptionalShouldBeReturned() throws AirPortDaoException {
        when(mockJdbcTemplate.queryForObject(eq(SELECT_PLANE_BY_ID_SQL), any(Object[].class), any(RowMapper.class))).thenReturn(null);
        Optional<Plane> plane = planeDao.getById(Integer.valueOf(1));
        assertEquals(Optional.empty(), plane);
    }

    @Test
    public void whenGetByIdNullPassedThenDAOExceptionShouldBeThrown() {
        Throwable exception =  assertThrows(AirPortDaoException.class,() -> {
            planeDao.getById(null);
        });

        assertEquals(PLANE_ID_CANNOT_BE_NULL.get(), exception.getMessage());
    }

    @Test
    public void whenCreateThrownEmptyResultDataAccessExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
            when(mockJdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenThrow(EmptyResultDataAccessException.class);
            planeDao.create(plane);
        });

        assertEquals(exception.getMessage(), FAILED_TO_CREATE_NEW_PLANE.get());
    }

    @Test
    public void whenUpdateThrownEmptyResultDataAccessExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
            when(mockJdbcTemplate.update(any(PreparedStatementCreator.class))).thenThrow(EmptyResultDataAccessException.class);
            planeDao.update(plane);
        });

        assertEquals(exception.getMessage(), FAILED_TO_UPDATE_PLANE_WITH_ID.get()+1);
    }

    @Test
    public void whenDeleteThrownEmptyResultDataAccessExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
            when(mockJdbcTemplate.update(DELETE_PLANE_BY_ID_SQL,1))
                    .thenThrow(EmptyResultDataAccessException.class);
            planeDao.delete(plane);
        });

        assertEquals(exception.getMessage(), FAILED_TO_DELETE_PLANE_WITH_ID.get()+1);
    }

    @Test
    public void whenGetAllThrownAccessExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
        when(mockJdbcTemplate.query(eq(SELECT_ALL_PLANES_BY_ID_SQL), any(RowMapper.class)))
                .thenThrow(EmptyResultDataAccessException.class);
            planeDao.getAll();
        });
        assertEquals(exception.getMessage(), FAILED_TO_GET_ALL_PLANES.get());
    }

    @Test
    public void whenGetByIdThrownSQlExceptionThenDAOExceptionShouldBeThrownToo() {
        when(mockJdbcTemplate.queryForObject(eq(SELECT_PLANE_BY_ID_SQL), any(), any(RowMapper.class)))
                .thenThrow(EmptyResultDataAccessException.class);
        Throwable exception = assertThrows(AirPortDaoException.class, () -> {
            planeDao.getById(Integer.valueOf(1));
        });

        assertEquals(exception.getMessage(), FAILED_TO_GET_PLANE_WITH_ID.get()+1);
    }
}


