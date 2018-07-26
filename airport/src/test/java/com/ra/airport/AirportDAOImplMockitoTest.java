package com.ra.airport;

import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.dao.impl.AirportDAOImpl;
import com.ra.airport.entity.Airport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AirportDAOImplMockitoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private PreparedStatement statement;
    @InjectMocks
    private AirportDAOImpl airportDAO;
    private Airport airport;
    private static final String INSERT_QUERY = "INSERT INTO Airport(apname, apnum, aptype, address, terminalcount) "
            + "VALUES(?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_QUERY = "Select * From Airport Where apid = ?";
    private static final String SELECT_ALL_QUERY = "Select * From Airport";
    private static final String UPDATE_QUERY = "UPDATE Airport SET apname = ?, apnum = ?, aptype = ?, address = ?, terminalcount = ?"
            + " WHERE apid = ?";
    private static final String DELETE_QUERY = "DELETE FROM Airport "
            + "WHERE apid = ?";
    private static final String LAST_INSERT_ID = "SELECT SCOPE_IDENTITY()";

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        airport = new Airport(8,"Kenedy", 4949034, "International", "USA New Yourk", 10);
    }

    @Test
    void whenCreateAirportThenReturnAirportWitsIdTrue() throws AirPortDaoException {
        Mockito.doAnswer(invocation -> {
            ((PreparedStatementSetter)invocation.getArguments()[1]).setValues(statement);
            return null;
        }).when(jdbcTemplate).update(Mockito.eq(INSERT_QUERY), Mockito.any(PreparedStatementSetter.class));
        Mockito.when(jdbcTemplate.queryForObject(LAST_INSERT_ID, Integer.class)).thenReturn(8);
        Mockito.when(jdbcTemplate.queryForObject(Mockito.eq(SELECT_BY_ID_QUERY), Mockito.any(BeanPropertyRowMapper.class), Mockito.eq(8))).thenReturn(airport);
        Airport createdAirport = airportDAO.create(airport);
        assertEquals(createdAirport, airport);
    }

    @Test
    void whenCreateAirportThenThrowExseption() {
        Mockito.when(jdbcTemplate.queryForObject(LAST_INSERT_ID, Integer.class)).thenThrow(BadSqlGrammarException.class);
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            airportDAO.create(airport);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void whenUpdateAirportThenReturnAirport() throws AirPortDaoException {
        Mockito.doAnswer(invocation -> {
            ((PreparedStatementSetter)invocation.getArguments()[1]).setValues(statement);
            return null;
        }).when(jdbcTemplate).update(Mockito.eq(UPDATE_QUERY), Mockito.any(PreparedStatementSetter.class));
        Airport updatedAirport = airportDAO.update(airport);
        assertEquals(updatedAirport, airport);
    }

    @Test
    void whenUpdateAirportThenThrowExseption() {
        Mockito.doThrow(BadSqlGrammarException.class).when(jdbcTemplate).update(Mockito.eq(UPDATE_QUERY),
                Mockito.any(PreparedStatementSetter.class));
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            airportDAO.update(airport);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void whenDeleteAirportThenReturnTrue() throws AirPortDaoException {
        Mockito.when(jdbcTemplate.update(Mockito.eq(DELETE_QUERY), Mockito.any(PreparedStatementSetter.class))).thenReturn(1);
        boolean createdAirport = airportDAO.delete(airport);
        assertEquals(createdAirport, true);
    }

    @Test
    void whenDeleteAirportThenReturnFalse() throws AirPortDaoException {
        Mockito.doAnswer(invocation -> {
            ((PreparedStatementSetter)invocation.getArguments()[1]).setValues(statement);
            return null;
        }).when(jdbcTemplate).update(Mockito.eq(DELETE_QUERY), Mockito.any(PreparedStatementSetter.class));
        boolean delettedAirport = airportDAO.delete(airport);
        assertEquals(delettedAirport, false);
    }

    @Test
    void whenDeleteAirportThenThrowExseption() {
        Mockito.doThrow(BadSqlGrammarException.class).when(jdbcTemplate).update(Mockito.eq(DELETE_QUERY), Mockito.any(PreparedStatementSetter.class));
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            airportDAO.delete(airport);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void whenGetByIdAirportThenReturnAirport() throws AirPortDaoException {
        Mockito.when(jdbcTemplate.queryForObject(Mockito.eq(SELECT_BY_ID_QUERY), Mockito.any(BeanPropertyRowMapper.class), Mockito.eq(1))).thenReturn(airport);
        Airport findAirport = airportDAO.getById(1).get();
        assertEquals(findAirport, airport);
    }

    @Test
    void whenGetByIdAirportThenThrowExseption() {
        Mockito.doThrow(BadSqlGrammarException.class).when(jdbcTemplate).queryForObject(Mockito.eq(SELECT_BY_ID_QUERY),
                Mockito.any(BeanPropertyRowMapper.class), Mockito.eq(1));
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            airportDAO.getById(1);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void whenGetAllThenReturnList() throws AirPortDaoException {
        List<Airport> list = new ArrayList<>() {
            @Override
            public int size() {
                return 3;
            }
        };
        Mockito.when(jdbcTemplate.query(Mockito.eq(SELECT_ALL_QUERY), Mockito.any(BeanPropertyRowMapper.class))).thenReturn(list);
        airportDAO.getAll();
        assertEquals(list.size(), 3);
    }
    @Test
    void whenGetAllThenThrowExseption() {
        Mockito.when(jdbcTemplate.query(Mockito.eq(SELECT_ALL_QUERY), Mockito.any(BeanPropertyRowMapper.class))).thenThrow(BadSqlGrammarException.class);
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            airportDAO.getAll();
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void throwNewAirPortDaoException() {
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            throw new AirPortDaoException("test Exception");
        });
        assertNotNull(thrown.getMessage());
    }
}