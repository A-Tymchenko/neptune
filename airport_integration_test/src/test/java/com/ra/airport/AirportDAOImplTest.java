package com.ra.airport;

import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.entity.Airport;
import com.ra.airport.dao.impl.AirportDAOImpl;
import com.ra.airport.factory.ConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AirportDAOImplTest {

    ConnectionFactory connection;
    Airport airport;
    AirportDAOImpl airportImpl;

    @BeforeEach
    public void initH2() throws SQLException, IOException {
        connection = ConnectionFactory.getInstance();
        connection.getConnection().createStatement().executeUpdate("RUNSCRIPT FROM 'src/test/resources/sql/create_table_skripts.sql'");
        connection.getConnection().createStatement().executeUpdate("RUNSCRIPT FROM 'src/test/resources/sql/tables_backup(data).sql'");
        airport = new Airport(1,"Kenedy", 12345, "international", "New York", 10);
        airportImpl = new AirportDAOImpl(connection);
    }

    @AfterEach
    public void removeTable() throws SQLException {
        connection.getConnection().createStatement().executeUpdate("RUNSCRIPT FROM 'src/test/resources/sql/remove_table_skripts.sql'");
    }

    @Test
    public void whenAddAirportThenReturnCreatedAirportWitsId() throws AirPortDaoException {
        Airport createdAirport = airportImpl.create(airport);
        airport.setApId(createdAirport.getApId());
        assertEquals(createdAirport, airport);
    }

    @Test
    public void whenUpdateAirportThenReturnCreatedAirportWitsId() throws AirPortDaoException {
        Airport createdAirport = airportImpl.update(airport);
        airport.setApId(createdAirport.getApId());
        assertEquals(createdAirport, airport);
    }

    @Test
    public void whenDeleteAirportThenReturnTrue() throws AirPortDaoException {
        assertEquals(airportImpl.delete(airport), true);
    }

    @Test
    public void whenDeleteAirportThenReturnFalse() throws AirPortDaoException {
        airport.setApId(10);
        assertEquals(airportImpl.delete(airport), false);
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
    public void whenGetAirportByIdThenReturnEmptyAirport() throws AirPortDaoException {
        Optional<Airport> optionalAirport = airportImpl.getById(8);
        assertEquals(optionalAirport.isPresent(), false);
    }

    @Test
    public void getAirports() throws AirPortDaoException {
        AirportDAOImpl apim = new AirportDAOImpl(connection);
        List<Airport> list = apim.getAll();
        assertEquals(list.size(), 7);
    }
}