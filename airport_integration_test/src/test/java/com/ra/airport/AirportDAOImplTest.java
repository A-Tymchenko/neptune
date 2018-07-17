package com.ra.airport;

import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.entity.Airport;
import com.ra.airport.dao.impl.AirportDAOImpl;
import com.ra.airport.factory.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AirportDAOImplTest {

    ConnectionFactory conn;
    Airport airport;
    AirportDAOImpl airportImpl;

    @BeforeEach
    public void initH2() throws SQLException, IOException {
        conn = ConnectionFactory.getInstance();
        conn.getConnection().createStatement().executeUpdate("RUNSCRIPT FROM 'src/test/resources/sql/dbschema.sql'");
        conn.getConnection().createStatement().executeUpdate("RUNSCRIPT FROM 'src/test/resources/sql/test-data.sql'");
        airport = new Airport(1,"Kenedy", 12345, "international", "New York", 10);
        airportImpl = new AirportDAOImpl(conn);
    }

    @Test
    public void whenAddAirportThenReturnCreatedAirportWitsId() throws AirPortDaoException {
        Airport createdAirport = airportImpl.create(airport);
        airport.setApid(createdAirport.getApid());
        assertEquals(createdAirport, airport);
    }

    @Test
    public void whenUpdateAirportThenReturnCreatedAirportWitsId() throws AirPortDaoException {
        Airport createdAirport = airportImpl.update(airport);
        airport.setApid(createdAirport.getApid());
        assertEquals(createdAirport, airport);
    }

    @Test
    public void whenDeleteAirportThenReturnTrue() throws AirPortDaoException {
        assertEquals(airportImpl.delete(airport), true);
    }

    @Test
    public void whenDeleteAirportThenReturnFalse() throws AirPortDaoException {
        airport.setApid(10);
        assertEquals(airportImpl.delete(airport), false);
    }

    @Test
    public void whenGetAirportByIdThenReturnAirport() throws AirPortDaoException {
        Optional<Airport> ap = airportImpl.getById(1);
        System.out.println(ap);
        assertEquals(ap.get().getApname(), this.airport.getApname());
        assertEquals(ap.get().getAddresses(), this.airport.getAddresses());
        assertEquals(ap.get().getAptype(), this.airport.getAptype());
        assertEquals(ap.get().getApnum(), this.airport.getApnum());
        assertEquals(ap.get().getTerminalcount(), this.airport.getTerminalcount());
    }

    @Test
    public void whenGetAirportByIdThenReturnEmptyAirport() throws AirPortDaoException {
        Optional<Airport> optionalAirport = airportImpl.getById(8);
        assertEquals(optionalAirport.isPresent(), false);
    }

    @Test
    public void getAirports() throws AirPortDaoException {
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        List<Airport> list = apim.getAll();
        assertEquals(list.size(), 7);
    }
}