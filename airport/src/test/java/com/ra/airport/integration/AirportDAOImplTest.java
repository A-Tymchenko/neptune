package com.ra.airport.integration;

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
    Airport ap;
    AirportDAOImpl apim;

    @BeforeEach
    public void initH2() throws SQLException, IOException {
        conn = ConnectionFactory.getInstance();
        conn.getConnection().createStatement().executeUpdate("RUNSCRIPT FROM 'src/main/resources/dbschema.sql'");
        conn.getConnection().createStatement().executeUpdate("RUNSCRIPT FROM 'src/main/resources/test-data.sql'");
        ap = new Airport(1,"Kenedy", 12345, "international", "New York", 10);
        apim = new AirportDAOImpl(conn);
    }

    @Test
    public void WhenAddAirportThenReturnCreatedAirportWitsId() throws AirPortDaoException {
        Airport createdAirport = apim.create(ap);
        ap.setApid(createdAirport.getApid());
        assertEquals(createdAirport, ap);
    }

    @Test
    public void WhenUpdateAirportThenReturnCreatedAirportWitsId() throws AirPortDaoException {
        Airport createdAirport = apim.update(ap);
        ap.setApid(createdAirport.getApid());
        assertEquals(createdAirport, ap);
    }

    @Test
    public void WhenDeleteAirportThenReturnTrue() throws AirPortDaoException {
        assertEquals(apim.delete(ap), true);
    }

    @Test
    public void WhenDeleteAirportThenReturnFalse() throws AirPortDaoException {
        ap.setApid(10);
        assertEquals(apim.delete(ap), false);
    }

    @Test
    public void WhenGetAirportByIdThenReturnAirport() throws AirPortDaoException {
        Optional<Airport> ap = apim.getById(1);
        assertEquals(ap.get().getApname(), this.ap.getApname());
        assertEquals(ap.get().getAddresses(), this.ap.getAddresses());
        assertEquals(ap.get().getAptype(), this.ap.getAptype());
        assertEquals(ap.get().getApnum(), this.ap.getApnum());
        assertEquals(ap.get().getTerminalcount(), this.ap.getTerminalcount());
    }

    @Test
    public void WhenGetAirportByIdThenReturnEmptyAirport() throws AirPortDaoException {
        Optional<Airport> ap1 = apim.getById(8);
        assertEquals(ap1.isPresent(), false);
    }

    @Test
    public void getAirports() throws AirPortDaoException {
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        List<Airport> list = apim.getAll();
        assertEquals(list.size(), 7);
    }
}