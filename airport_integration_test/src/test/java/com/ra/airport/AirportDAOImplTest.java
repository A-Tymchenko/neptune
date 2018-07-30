package com.ra.airport;

import com.ra.airport.config.AirPortConfiguration;
import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.dao.impl.AirportDAOImpl;
import com.ra.airport.entity.Airport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AirPortConfiguration.class})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/create_table_skripts.sql")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/tables_backup(data).sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/remove_table_skripts.sql")
class AirportDAOImplTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    AirportDAOImpl airportImpl;
    Airport airport;

    @BeforeEach
    public void init() {
        airport = new Airport(1,"Kenedy", 12345, "international", "New York", 10);
    }

    @Test
    void whenAddAirportThenReturnCreatedAirportWitsId() throws AirPortDaoException {
        Airport createdAirport = airportImpl.create(airport);
        airport.setApId(createdAirport.getApId());
        assertEquals(createdAirport, airport);
    }

    @Test
    void whenUpdateAirportThenReturnCreatedAirportWitsId() throws AirPortDaoException {
        Airport createdAirport = airportImpl.update(airport);
        airport.setApId(createdAirport.getApId());
        assertEquals(createdAirport, airport);
    }

    @Test
    public void whenDeleteAirportThenReturnTrue() throws AirPortDaoException {
        assertEquals(airportImpl.delete(airport), true);
    }

    @Test
    public void whenGetAirportByIdThenReturnAirport() throws AirPortDaoException {
        Optional<Airport> ap = airportImpl.getById(1);
        assertEquals(ap.get().getApName(), this.airport.getApName());
        assertEquals(ap.get().getAddress(), this.airport.getAddress());
        assertEquals(ap.get().getApType(), this.airport.getApType());
        assertEquals(ap.get().getApNum(), this.airport.getApNum());
        assertEquals(ap.get().getTerminalCount(), this.airport.getTerminalCount());
    }

    @Test
    public void whenGetAirportByIdThenThrowAirPortDaoException() throws AirPortDaoException {
        Throwable thrown = assertThrows(AirPortDaoException.class, () -> {
            airportImpl.getById(8);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void getAirports() throws AirPortDaoException {
        List<Airport> list = airportImpl.getAll();
        assertEquals(list.size(), 7);
    }
}