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

public class AirportDAOImplTest {

    ConnectionFactory conn;
    Airport ap;

    {
        try {
            conn = ConnectionFactory.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void initH2(){
        try {
            conn.getConnection().createStatement().executeUpdate("RUNSCRIPT FROM 'src/main/resources/dbschema.sql'");
            conn.getConnection().createStatement().executeUpdate("RUNSCRIPT FROM 'src/main/resources/test-data.sql'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ap = new Airport(1,"Kenedy", 4949034, "International", "USA New Yourk", 10);
    }

    @Test
    public void addAirport() {
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        try {
            apim.create(ap);
        } catch (AirPortDaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateAirport() {
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        try {
            apim.update(ap);
        } catch (AirPortDaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteAirport() {
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        try {
            apim.delete(ap);
        } catch (AirPortDaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAirport() {
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        try {
            Optional<Airport> ap = apim.getById(7);
            Optional<Airport> ap1 = apim.getById(8);
        } catch (AirPortDaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAirports() {
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        List<Airport> list = apim.getAll();

    }

    @Test
    public void testSetMethodsAirport (){
        Airport ap = new Airport(10293,"Kenedy", 4949034, "International", "USA New Yourk", 10);
        ap.setAddresses("test");
        ap.setApid(1);
        ap.setApname("dsfdsfdsf");
        ap.setApnum(332423);
        ap.setAptype("fsdfsdfsdf");
        ap.setTerminalcount(4);
    }


}