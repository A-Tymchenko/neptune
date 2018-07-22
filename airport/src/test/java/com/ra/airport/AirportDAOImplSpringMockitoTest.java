package com.ra.airport;

import com.ra.airport.configuration.AirPortConfiguration;
import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.dao.impl.AirportDAOImplSpring;
import com.ra.airport.entity.Airport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.ResultSet;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AirPortConfiguration.class})
class AirportDAOImplSpringMockitoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    @Autowired
    private AirportDAOImplSpring airportDAO;
    private Airport airport;
    private SqlRowSet sqlRowSet;
    private static final String INSERT_QUERY = "INSERT INTO Airport(apname, apnum, aptype, address, terminalcount) "
            + "VALUES(?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_QUERY = "Select * From Airport Where apid = ?";
    private static final String SELECT_ALL_QUERY = "Select * From Airport";
    private static final String UPDATE_QUERY = "UPDATE Airport SET apname = ?, apnum = ?, aptype = ?, address = ?, terminalcount = ?"
            + " WHERE apid = ?";
    private static final String DELETE_QUERY = "DELETE FROM Airport "
            + "WHERE apid = ?";
    private static final String LAST_INSERT_ID = "Select LAST_INSERT_ID() from Airport";

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        airport = new Airport(8,"Kenedy", 4949034, "International", "USA New Yourk", 10);
        sqlRowSet = Mockito.mock(SqlRowSet.class);
        createAirport();
    }

    private void createAirport() {
        Mockito.when(sqlRowSet.getInt("apid")).thenReturn(airport.getApId());
        Mockito.when(sqlRowSet.getString("apname")).thenReturn(airport.getApName());
        Mockito.when(sqlRowSet.getInt("apnum")).thenReturn(airport.getApNum());
        Mockito.when(sqlRowSet.getString("aptype")).thenReturn(airport.getApType());
        Mockito.when(sqlRowSet.getString("address")).thenReturn(airport.getAddress());
        Mockito.when(sqlRowSet.getInt("terminalcount")).thenReturn(airport.getTerminalCount());
    }

    @Test
    void whenCreateAirportThenReturnAirportWitsIdTrue() throws AirPortDaoException {
        Mockito.when(jdbcTemplate.queryForRowSet(LAST_INSERT_ID)).thenReturn(sqlRowSet);
        Mockito.when(sqlRowSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(sqlRowSet.getInt(1)).thenReturn(8);
        Mockito.when(jdbcTemplate.queryForObject(Mockito.eq(SELECT_BY_ID_QUERY), Mockito.any(BeanPropertyRowMapper.class), Mockito.eq(8))).thenReturn(airport);
        airportDAO.create(airport);
    }

    @Test
    void whenCreateAirportThenReturnAirportWitsIdFalse() throws AirPortDaoException {
        Mockito.when(jdbcTemplate.queryForRowSet(LAST_INSERT_ID)).thenReturn(sqlRowSet);
        Mockito.when(sqlRowSet.next()).thenReturn(false);
        airportDAO.create(airport);
    }

    @Test
    void whenCreateAirportThenThrowExseption() {
        Mockito.when(jdbcTemplate.queryForRowSet(LAST_INSERT_ID)).thenThrow(BadSqlGrammarException.class);
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            airportDAO.create(airport);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void whenUpdateAirportThenReturnAirport() throws AirPortDaoException {
        airportDAO.update(airport);
    }

    @Test
    void whenUpdateAirportThenThrowExseption() {
        Mockito.doThrow(BadSqlGrammarException.class).when(jdbcTemplate).update(Mockito.eq(UPDATE_QUERY), Mockito.any(PreparedStatementSetter.class));
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            airportDAO.update(airport);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void whenDeleteAirportThenReturnTrue() throws AirPortDaoException {
        Mockito.when(jdbcTemplate.update(Mockito.eq(DELETE_QUERY), Mockito.any(PreparedStatementSetter.class))).thenReturn(1);
        airportDAO.delete(airport);
    }

    @Test
    void whenDeleteAirportThenReturnFalse() throws AirPortDaoException {
        airportDAO.delete(airport);
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
        airportDAO.getById(1);
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
    }
    @Test
    void whenGetAllThenThrowExseption() {
        Mockito.when(jdbcTemplate.query(Mockito.eq(SELECT_ALL_QUERY), Mockito.any(BeanPropertyRowMapper.class))).thenThrow(BadSqlGrammarException.class);
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            airportDAO.getAll();
        });
        assertNotNull(thrown.getMessage());
    }
}